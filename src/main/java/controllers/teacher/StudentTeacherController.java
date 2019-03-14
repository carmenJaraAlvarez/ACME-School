
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StudentService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Student;
import domain.Teacher;

@Controller
@RequestMapping("/student/teacher")
public class StudentTeacherController extends AbstractController {

	@Autowired
	TeacherService	teacherService;

	@Autowired
	StudentService	studentService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int classGroupId) {
		ModelAndView result;
		final Collection<Student> students = this.studentService.findByClass(classGroupId);
		result = createModelAndView("student/teacher/list");
		result.addObject("students", students);
		String uri = "student/teacher/list.do?classGroupId=" + classGroupId;
		result.addObject("uri", uri);
		String uriCancel = "classGroup/teacher/list.do";
		result.addObject("uriCancel", uriCancel);
		return result;
	}
	@RequestMapping("/riskList")
	public ModelAndView list() {
		ModelAndView result;
		Teacher logged = this.teacherService.findByPrincipal();
		final Collection<Student> students = this.studentService.findIsolated(logged.getId());
		result = createModelAndView("student/teacher/list");
		result.addObject("students", students);
		String s = "student/teacher/riskList.do";
		result.addObject("uri", s);
		String uriCancel = "classGroup/teacher/list.do";
		result.addObject("uriCancel", uriCancel);

		result.addObject("riskList", true);

		return result;
	}

}
