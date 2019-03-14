
package controllers.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ParentService;
import controllers.AbstractController;
import domain.Parent;
import forms.ActorForm;

@Controller
@RequestMapping("/parent")
public class ParentController extends AbstractController {

	@Autowired
	private ParentService	parentService;


	//Edit-------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Parent parent = this.parentService.findByPrincipal();
		final ActorForm actorForm = new ActorForm(parent);
		result = this.createEditModelAndView(actorForm);

		return result;
	}
	// Guarda al editar el parent
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		final Parent parent = this.parentService.reconstructParent(actorForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {
				this.parentService.saveEdit(parent);
				result = new ModelAndView("redirect:/actor/myDisplay.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actorForm, "actor.commit.error");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final ActorForm actorForm) {
		ModelAndView result;

		result = this.createEditModelAndView(actorForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm actorForm, final String message) {
		final ModelAndView result = createModelAndView("actor/edit");
		result.addObject("actorForm", actorForm);
		result.addObject("message", message);
		result.addObject("direction", "parent/edit.do");
		result.addObject("actorType", "parent");
		return result;
	}

}
