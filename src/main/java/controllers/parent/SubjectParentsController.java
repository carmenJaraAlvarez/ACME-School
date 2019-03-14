
package controllers.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SubjectService;
import controllers.AbstractController;
import domain.Subject;

@Controller
@RequestMapping("/subject/parent")
public class SubjectParentsController extends AbstractController {

	@Autowired
	private SubjectService	subjectService;


	// Panic handler ----------------------------------------------------------

	public SubjectParentsController() {
		super();
	}

	//  ---------------------------------------------------------------		

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final int levelId) {
		ModelAndView result;
		final Subject subject = this.subjectService.create(levelId);
		result = this.createEditModelAndView(subject);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int subjectId) {
		ModelAndView result;
		final Subject subject;
		subject = this.subjectService.findOneToEdit(subjectId);
		result = this.createEditModelAndView(subject);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Subject subject, final BindingResult binding) {
		ModelAndView result;
		final Subject reconstructed = this.subjectService.reconstruct(subject, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(subject);
		else
			try {
				for (Subject subjectNevel : subject.getLevel().getSubjects()) {
					Assert.isTrue(!subjectNevel.getName().equals(subject.getName()), "No pueden tener un nombre repetido dentro del nivel");
				}
				final Subject save = this.subjectService.saveForParent(reconstructed);
				String uri = "redirect:/subject/list.do?levelId=" + String.valueOf(subject.getLevel().getId());
				result = createModelAndView(uri);
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(reconstructed, "subject.commit.error");
				if (oops.getMessage().equals("No pueden tener un nombre repetido dentro del nivel")) {
					result = this.createEditModelAndView(reconstructed, "subject.commit.duplicate");
				}
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Subject subject) {
		ModelAndView result;
		result = this.createEditModelAndView(subject, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Subject subject, final String message) {
		ModelAndView result;
		result = super.createModelAndView("subject/edit");
		result.addObject("subject", subject);
		result.addObject("message", message);

		return result;
	}

}
