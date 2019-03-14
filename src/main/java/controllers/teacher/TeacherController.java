
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClassGroupService;
import services.SchoolService;
import services.TeacherService;
import controllers.AbstractController;
import domain.ClassGroup;
import domain.School;
import domain.Teacher;
import forms.ActorForm;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends AbstractController {

	@Autowired
	private TeacherService		teacherService;
	@Autowired
	private SchoolService		schoolService;
	@Autowired
	private ClassGroupService	classGroupService;


	//Edit-------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final Teacher teacher = this.teacherService.findByPrincipal();
		final ActorForm actorForm = new ActorForm(teacher);
		result = this.createEditModelAndView(actorForm);

		return result;
	}
	// Guarda al editar el teacher
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
		ModelAndView result;
		final Teacher teacher = this.teacherService.reconstructTeacher(actorForm, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(actorForm);
		else
			try {
				this.teacherService.saveEdit(teacher);
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
		result.addObject("direction", "teacher/edit.do");
		result.addObject("actorType", "teacher");
		result.addObject("schools", this.schoolService.findAll());
		return result;
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Teacher> teachers = this.teacherService.findAll();
		result = createModelAndView("teachers/list");
		result.addObject("teachers", teachers);
		return result;
	}

	@RequestMapping(value = "/addClassGroup")
	public ModelAndView addClassGroup(@RequestParam final int classGroupId, @RequestParam final int schoolId) {
		ModelAndView result;

		ClassGroup classGroup = this.classGroupService.findOne(classGroupId);
		Teacher teacher = this.teacherService.findByPrincipal();
		School school = this.schoolService.findOne(schoolId);
		Assert.isTrue(teacher.getSchool().equals(school));
		Assert.isTrue(!teacher.getClassGroups().contains(classGroup));
		teacher.getClassGroups().add(classGroup);
		classGroup.getTeachers().add(teacher);
		this.teacherService.saveEdit(teacher);
		this.classGroupService.save(classGroup);

		result = new ModelAndView("redirect:/school/display.do?schoolId=" + teacher.getSchool().getId());
		return result;
	}

}
