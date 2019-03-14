
package controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClassGroupService;
import services.ParentService;
import services.StudentService;
import controllers.AbstractController;
import domain.Actor;
import domain.Student;
import forms.ActorForm;

@Controller
@RequestMapping("/student")
public class StudentController extends AbstractController {

	@Autowired
	private StudentService		studentService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private ClassGroupService	classGroupService;


	//Edit-------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int actorId) {
		ModelAndView result;
		final Student student = this.studentService.findOne(actorId);
		final ActorForm actorForm = new ActorForm(student);
		result = this.createEditModelAndView(actorForm);
		Actor logged = this.parentService.findByPrincipal();
		Assert.isTrue(logged.equals(student.getParent()));
		return result;
	}
	// Guarda al editar el student
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		final Student student = this.studentService.reconstructStudent(actorForm, binding);
		Actor logged = this.parentService.findByPrincipal();
		Assert.isTrue(logged.equals(student.getParent()));
		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {
				this.studentService.saveEdit(student);
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
		result.addObject("direction", "student/edit.do");
		result.addObject("actorType", "student");
		result.addObject("classGroups", this.classGroupService.findAll());
		return result;
	}

}
