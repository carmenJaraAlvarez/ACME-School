
package controllers.teacher;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParentsGroupService;
import services.TeacherService;
import controllers.AbstractController;
import domain.ParentsGroup;
import domain.Teacher;

@Controller
@RequestMapping("/inscription/teacher")
public class InscriptionTeachersController extends AbstractController {

	@Autowired
	ParentsGroupService	parentsGroupService;
	@Autowired
	TeacherService		teacherService;


	@RequestMapping("/createInscription")
	public ModelAndView create(@RequestParam final Integer parentsGroupId) {
		ModelAndView result = createModelAndView("inscription/teacher");
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("URL", "inscription/teacher/createInscription.do");

		return result;
	}

	@RequestMapping(value = "/createInscription", method = RequestMethod.GET, params = "save")
	public ModelAndView create(@RequestParam String code, @RequestParam int parentsGroupId) {
		ModelAndView result;
		try {
			ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
			Teacher teacher = this.teacherService.findByPrincipal();
			if (parentsGroup.getCode().equals(code)) {
				this.parentsGroupService.addTeacher(teacher, parentsGroup);
				result = new ModelAndView("redirect:/parentsGroup/general/list.do");
			} else {
				result = this.createEditModelAndView(parentsGroupId, "code.commit.error");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parentsGroupId, "member.commit.error");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final int parentsGroupId, final String message) {
		ModelAndView result;
		result = createModelAndView("inscription/teacher");
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("message", message);
		result.addObject("URL", "inscription/teacher/createInscription.do");
		return result;
	}

	@RequestMapping(value = "/deleteInscription", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int parentsGroupId) {
		ModelAndView result;
		try {
			ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
			Teacher teacher = this.teacherService.findByPrincipal();
			if (parentsGroup.getTeachers().contains(teacher)) {
				this.parentsGroupService.removeTeacherOfGroup(teacher, parentsGroup);
				result = new ModelAndView("redirect:/parentsGroup/teacher/mylist.do");
			} else {
				result = this.deleteModelAndView(parentsGroupId, "no.member.error");
			}

		} catch (final Throwable oops) {
			result = this.deleteModelAndView(parentsGroupId, "member.commit.error");
		}
		return result;
	}

	//Donde se vaya a poner el boton que se redirija ahi
	private ModelAndView deleteModelAndView(final int parentsGroupId, final String message) {
		Teacher teacher = this.teacherService.findByPrincipal();
		Collection<ParentsGroup> res = new ArrayList<>(teacher.getParentsGroups());
		ModelAndView result = createModelAndView("parentsGroup/teacher/mylist");
		result.addObject("parentsGroups", res);
		result.addObject("myList", true);
		result.addObject("message", message);

		return result;
	}

}
