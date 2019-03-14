
package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdvertisementRepository;
import utilities.CookiesUtilities;
import domain.Administrator;
import domain.Advertisement;
import domain.Agent;
import domain.CreditCard;

@Service
@Transactional
public class AdvertisementService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdvertisementRepository	advertisementRepository;

	// Services ---------------------------------------------------------------
	@Autowired
	private AgentService			agentService;
	@Autowired
	private Validator				validator;
	@Autowired
	private GlobalService			globalService;
	@Autowired
	private AdministratorService	adminService;


	// Constructors -----------------------------------------------------------

	public AdvertisementService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Advertisement findOne(final int advertisementId) {
		return this.advertisementRepository.findOne(advertisementId);
	}

	public Collection<Advertisement> findAll() {
		return this.advertisementRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public void flush() {
		this.advertisementRepository.flush();
	}

	public Advertisement create() {
		Advertisement res;
		final Boolean paid = false;
		res = new Advertisement();
		res.setPaid(paid);
		final Agent agent = this.agentService.findByPrincipal();
		res.setAgent(agent);
		return res;
	}

	public Advertisement findOneToEdit(final int advertisementId) {
		Advertisement res;
		res = this.findOne(advertisementId);
		this.agentService.checkPrincipal(res.getAgent());
		return res;
	}

	public Advertisement save(final Advertisement advertisement) {
		this.agentService.checkPrincipal(advertisement.getAgent());
		return this.advertisementRepository.save(advertisement);

	}
	public String checkConcurrence(final Advertisement advertisement) {
		String s = null;
		if (advertisement.getId() != 0) {
			final Advertisement advertisementBD = this.advertisementRepository.findOne(advertisement.getId());
			if (advertisement.getVersion() != advertisementBD.getVersion())
				s = "advertisement.concurrency.error";
		}
		return s;
	}

	public Collection<Advertisement> findByKeyWord(final String keyWord) {
		final Collection<Advertisement> advertisements = this.advertisementRepository.findByKeyWord(keyWord);
		return advertisements;
	}

	public Advertisement reconstruct(final Advertisement advertisement, final BindingResult binding) {
		Advertisement result;
		if (advertisement.getId() == 0) {
			result = this.create();
			result.setAgent(this.agentService.findByPrincipal());
			result.setTitle(advertisement.getTitle());
			result.setBanner(advertisement.getBanner());
			result.setWeb(advertisement.getWeb());
			result.setCreditCard(advertisement.getCreditCard());
		} else {
			final Advertisement origin = this.findOneToEdit(advertisement.getId());
			result = advertisement;
			advertisement.setAgent(origin.getAgent());
		}

		if (binding != null)
			this.validator.validate(result, binding);
		return result;
	}

	public Collection<Advertisement> getTabooAdvertisements() {
		Assert.notNull(this.adminService.findByPrincipal());
		final Collection<String> tabooWords = this.globalService.getGlobal().getSpamWords();
		final Collection<Advertisement> advertisementTaboo = new HashSet<Advertisement>();
		for (final String tabooWord : tabooWords)
			advertisementTaboo.addAll(this.advertisementRepository.tabooAdvertisement(tabooWord));

		return advertisementTaboo;
	}

	public void deleteAdmin(final Advertisement advertisement) {
		this.checkAdmin();
		this.advertisementRepository.delete(advertisement);

	}

	private void checkAdmin() {
		final Administrator admin = this.adminService.findByPrincipal();
		Assert.notNull(admin, "No admin");
	}

	//Query 2.5.3
	public Double ratioAdvertisementsTabooVsTotal() {
		Double res = 0.;
		final Double all = (double) this.findAll().size();
		final Double taboos = (double) this.getTabooAdvertisements().size();
		if (all > 0)
			res = taboos / all;

		return res * 100.;

	}

	public Advertisement getValidRandom() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -2);
		final Date fechaActual = calendar.getTime();
		final Collection<Advertisement> announcements = this.advertisementRepository.findAll();
		final List<Advertisement> validAnnouncements = new ArrayList<Advertisement>();
		for (final Advertisement a : announcements)
			if (a.getPaid() && a.getPaidMoment().after(fechaActual))
				validAnnouncements.add(a);
		final Random rand = new Random();
		Advertisement randomAnnouncement = null;
		if (validAnnouncements.size() > 0) {
			randomAnnouncement = validAnnouncements.get(rand.nextInt(validAnnouncements.size()));
		}
		return randomAnnouncement;
	}

	public boolean checkTarjeta(final CreditCard creditCard) {
		final Date hoy = new Date();
		final Integer mesHoy = this.obtenerMes(hoy);
		final Integer añoHoy = this.obtenerAnyo(hoy);
		Boolean res = true;
		if (creditCard.getExpirationYear() < añoHoy)
			res = false;
		else if (creditCard.getExpirationYear() == añoHoy && creditCard.getExpirationMonth() < mesHoy)
			res = false;
		return res;
	}
	public boolean validaTarjeta(final CreditCard creditCard) {
		Boolean res = false;
		if ((creditCard.getCvv() != null || !creditCard.getCvv().equals(" ")) || (creditCard.getExpirationYear() != null || !creditCard.getExpirationYear().equals(""))
			|| (creditCard.getExpirationMonth() != null || !creditCard.getExpirationMonth().equals("")))
			res = true;
		return res;
	}
	//-----------------------------------------------------------------
	public Integer obtenerAnyo(final Date date) {

		if (null == date)
			return 0;
		else {

			final String formato = "yy";
			final SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return Integer.parseInt(dateFormat.format(date));

		}

	}
	public int obtenerMes(final Date date) {

		if (null == date)
			return 0;
		else {

			final String formato = "MM";
			final SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return Integer.parseInt(dateFormat.format(date));

		}
	}

	//PayPal
	public void payAdvertisement(final Advertisement advertisement) {
		this.agentService.checkPrincipal(advertisement.getAgent());

		Boolean paid;

		paid = advertisement.getPaid();

		Assert.isTrue(!paid, "advertisement.error.paid");

		Date paymentMoment;

		paymentMoment = new Date(System.currentTimeMillis() - 1);

		advertisement.setPaidMoment(paymentMoment);
		advertisement.setPaid(true);

		this.advertisementRepository.save(advertisement);
	}

	public HttpServletResponse compruebaCookies(final HttpServletResponse response, final HttpServletRequest request) {

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
		if (ID.getValue() == null || !ID.getValue().equals(loginID)) {

			ID.setValue(loginID.toString());
			title.setValue("titlePrueba");
			banner.setValue("https://i.ytimg.com/vi/nWU1c3jYjYE/hqdefault.jpg");
			web.setValue("https://ev.us.es/");

			//System.out.print(ID.getValue());
			//mismo ID
		}
		response.addCookie(title);
		response.addCookie(banner);
		response.addCookie(web);
		return response;
	}

	public int getAmountAdsHiredLastMonth() {
		return this.advertisementRepository.getAdvertisementInLastMonth();
	}
}
