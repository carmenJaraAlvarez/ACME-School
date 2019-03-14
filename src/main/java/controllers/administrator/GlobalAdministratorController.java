
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GlobalService;
import controllers.AbstractController;
import domain.Global;

@Controller
@RequestMapping("/global/administrator")
public class GlobalAdministratorController extends AbstractController {

	//Services

	@Autowired
	private GlobalService	globalService;


	//Constructors

	public GlobalAdministratorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) final String actionSave) {
		final ModelAndView result = createModelAndView("global/edit");
		final Global global = this.globalService.getGlobal();
		if (actionSave != null) {
			if (actionSave.equals("saved")) {
				result.addObject("saved", true);
			}
		}
		Assert.notNull(global);
		result.addObject("global", global);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Global global, final BindingResult binding) {
		ModelAndView result = null;
		if (binding.hasErrors())
			result = this.createEditModelAndView(global);
		else
			try {
				this.globalService.save(global);
				result = new ModelAndView("redirect:edit.do?actionSave=saved");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(global, "global.commit.error");
			}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Global global) {
		ModelAndView result;

		result = this.createEditModelAndView(global, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Global global, final String message) {
		final ModelAndView result;

		result = createModelAndView("global/edit");

		result.addObject("global", global);
		result.addObject("message", message);
		return result;
	}
}
