
package controllers.agent;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.AgentService;
import services.GlobalService;
import utilities.CookiesUtilities;
import controllers.AbstractController;
import domain.Advertisement;
import domain.Agent;
import domain.Global;

@Controller
@RequestMapping("/advertisement/agent")
public class AdvertisementAgentController extends AbstractController {

	// Services

	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private GlobalService			globalService;
	@Autowired
	private AgentService			agentService;


	// ---------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		Global global = this.globalService.getGlobal();
		final Agent log = this.agentService.findByPrincipal();
		Double amount = global.getPrice();
		ModelAndView result;
		final Collection<Advertisement> advertisements = log.getAdvertisements();
		result = createModelAndView("advertisement/list");
		result.addObject("uri", "advertisement/agent/list.do");
		result.addObject("advertisements", advertisements);
		result.addObject("amount", amount);
		return result;
	}
	/* crear advertisement */
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result = createModelAndView("advertisement/create");
		final Advertisement advertisement = this.advertisementService.create();
		result.addObject("advertisement", advertisement);
		result.addObject("uri", "advertisement/agent/edit.do");

		return result;
	}
	/* carga la jsp con los distintos métodos de pago */
	@RequestMapping(value = "/paidMethod", method = RequestMethod.GET)
	public ModelAndView paidMethod(@RequestParam int advertisementId) {
		final ModelAndView result = createModelAndView("advertisement/paidMethod");
		final Advertisement advertisement;
		advertisement = this.advertisementService.findOne(advertisementId);
		result.addObject("advertisement", advertisement);
		result.addObject("advertisementId", advertisementId);
		result.addObject("uri", "advertisement/agent/paidMethod.do?advertisementId=" + advertisement.getId());

		return result;
	}
	/* carga la jsp pay donde se introduce la tarjeta de crédito para realizar el pago */
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public ModelAndView payCreditCard(@RequestParam int advertisementId) {
		final ModelAndView result = createModelAndView("advertisement/pay");
		final Advertisement advertisement;
		advertisement = this.advertisementService.findOne(advertisementId);
		result.addObject("advertisement", advertisement);
		result.addObject("uri", "advertisement/agent/pay.do?advertisementId=" + advertisement.getId());

		return result;
	}
	// Edition ----------------------------------------------------------------

	//	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	//	public ModelAndView edit(@RequestParam final int advertisementId) {
	//		ModelAndView result;
	//		Advertisement advertisement;
	//
	//		advertisement = this.advertisementService.findOneToEdit(advertisementId);
	//		result = this.createEditModelAndView(advertisement);
	//
	//		return result;
	//	}
	/* Guarda advetisement */
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Advertisement advertisement, final BindingResult binding) {
		ModelAndView result;
		final Advertisement toSave = this.advertisementService.reconstruct(advertisement, binding);
		String s = null;
		if (binding.hasErrors())
			result = this.createEditModelAndView(advertisement);
		else {
			s = this.advertisementService.checkConcurrence(advertisement);
			if (s != null)
				result = this.createEditModelAndView(advertisement, "advertisement.concurrency.error");
			else
				try {
					this.advertisementService.save(toSave);
					result = new ModelAndView("redirect:/advertisement/agent/list.do");

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(advertisement, "advertisement.commit.error");
				}
		}
		return result;

	}
	/* guarda la tarjeta de crédito */
	@RequestMapping(value = "/pay", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCrditCard(final Advertisement advertisement, final BindingResult binding) {
		ModelAndView result;
		Boolean paid;
		paid = new Boolean(true);
		Date paidMoment = new Date(System.currentTimeMillis() - 1);
		final Advertisement toSave = this.advertisementService.reconstruct(advertisement, binding);
		String s = null;
		result = createModelAndView("advertisement/pay");
		if (binding.hasErrors()) {
			result.addObject("advertisement", advertisement);
		} else {
			s = this.advertisementService.checkConcurrence(advertisement);
			if (s != null) {
				result.addObject("message", "advertisement.concurrency.error");
				result.addObject("advertisement", advertisement);
			} else
				try {
					if (advertisement.getCreditCard().getCvv() == null || advertisement.getCreditCard().getCvv().equals("")) {
						result.addObject("message", "advertisement.creditCard.cvv.error");
						result.addObject("advertisement", advertisement);
					} else if (advertisement.getCreditCard().getExpirationMonth() == null || advertisement.getCreditCard().getExpirationMonth().equals("")) {
						result.addObject("message", "advertisement.creditCard.expirationMonth.error");
						result.addObject("advertisement", advertisement);
					} else if (advertisement.getCreditCard().getExpirationYear() == null || advertisement.getCreditCard().getExpirationYear().equals("")) {
						result.addObject("message", "advertisement.creditCard.expirationYear.error");
						result.addObject("advertisement", advertisement);
					} else {
						if (this.advertisementService.checkTarjeta(advertisement.getCreditCard())) {
							toSave.setPaid(paid);
							toSave.setPaidMoment(paidMoment);
							this.advertisementService.save(toSave);
							result = new ModelAndView("redirect:/advertisement/agent/list.do");
						} else {
							result.addObject("message", "advertisement.date.error");
							result.addObject("advertisement", advertisement);
						}
					}

				} catch (final Throwable oops) {
					result = result.addObject("message", "advertisement.commit.error");
					result.addObject("advertisement", advertisement);
				}
		}
		return result;

	}
	/* Carga la jsp de pago Paypal */
	@RequestMapping(value = "/paypal", method = RequestMethod.GET)
	public ModelAndView PayPal(@RequestParam int advertisementId, final HttpServletResponse response, final HttpServletRequest request) {
		ModelAndView result;
		final Advertisement advertisement;
		advertisement = this.advertisementService.findOne(advertisementId);
		Boolean paid;
		paid = advertisement.getPaid();
		Global global = this.globalService.getGlobal();
		Double amount = global.getPrice();
		String payPalEmail = global.getPayPalEmail();

		Cookie ID = CookiesUtilities.getCookie(request, "ID");
		final String loginID = String.valueOf(this.agentService.findByPrincipal().getId());
		Cookie title = CookiesUtilities.getCookie(request, "title");
		Cookie banner = CookiesUtilities.getCookie(request, "banner");
		Cookie web = CookiesUtilities.getCookie(request, "web");
		//inicializacion cookies
		if (ID == null)
			ID = new Cookie("ID", loginID.toString());
		if (title == null)
			title = new Cookie("title", "titlePrueba");
		if (banner == null)
			banner = new Cookie("banner", "https://i.ytimg.com/vi/nWU1c3jYjYE/hqdefault.jpg");
		if (web == null)
			web = new Cookie("web", "https://ev.us.es/");

		//nuevo login o no dato->inicialización por defecto
		else if (ID.getValue() == null || !ID.getValue().equals(loginID)) {

			ID.setValue(loginID.toString());
			title.setValue("titlePrueba");
			banner.setValue("https://i.ytimg.com/vi/nWU1c3jYjYE/hqdefault.jpg");
			web.setValue("https://ev.us.es/");

			response.addCookie(title);
			response.addCookie(banner);
			response.addCookie(web);

			//System.out.print(ID.getValue());
			//mismo ID
		}
		response.addCookie(ID);

		result = createModelAndView("advertisement/paypal");
		result.addObject("advertisement", advertisement);
		result.addObject("amount", amount);
		result.addObject("payPalEmail", payPalEmail);
		result.addObject("paid", paid);
		result.addObject("actionURI", "advertisement/agent/paypal.do");

		return result;
	}
	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public ModelAndView success() {
		ModelAndView result;

		result = createModelAndView("advertisement/success");

		return result;
	}
	/* Método al que vuelve de paypal cuando el pago es satisfactorio */
	@RequestMapping(value = "/success", method = RequestMethod.POST)
	public ModelAndView success2(@RequestParam int advertisementId) {
		ModelAndView result;
		Advertisement advertisement;
		try {
			advertisement = advertisementService.findOne(advertisementId);

			advertisementService.payAdvertisement(advertisement);
			result = this.success();
		} catch (Throwable oops) {
			if (oops.getMessage().contains("advertisement.error.paid")) {
				result = createModelAndView("advertisement/advertisementError");
			} else {
				result = this.success();
			}
		}

		return result;
	}
	/* Método al que vuelve de paypal cuando el pago no se realiza */
	@RequestMapping(value = "/failure", method = RequestMethod.GET)
	public ModelAndView failure() {
		ModelAndView result;

		result = createModelAndView("advertisement/failure");

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Advertisement advertisement) {
		ModelAndView result;
		result = this.createEditModelAndView(advertisement, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Advertisement advertisement, final String message) {
		final ModelAndView result = createModelAndView("advertisement/edit");
		result.addObject("advertisement", advertisement);
		result.addObject("message", message);

		return result;
	}

}
