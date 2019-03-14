
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ClassGroupService;
import services.LevelService;
import services.SchoolService;
import services.TeacherService;
import controllers.AbstractController;
import domain.ClassGroup;
import domain.Teacher;

@Controller
@RequestMapping("/classGroup/teacher")
public class ClassGroupTeacherController extends AbstractController {

	@Autowired
	TeacherService		teacherService;

	@Autowired
	SchoolService		schoolService;

	@Autowired
	LevelService		levelService;

	@Autowired
	ClassGroupService	classGroupService;


	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Teacher logged = this.teacherService.findByPrincipal();
		final Collection<ClassGroup> classGroups = logged.getClassGroups();
		result = createModelAndView("classGroup/teacher/list");
		result.addObject("classGroups", classGroups);
		result.addObject("requestURI", "classGroup/teacher/list.do");
		return result;
	}

}
