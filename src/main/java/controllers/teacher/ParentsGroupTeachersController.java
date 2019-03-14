
package controllers.teacher;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.TeacherService;
import controllers.AbstractController;
import domain.ParentsGroup;
import domain.Teacher;

@Controller
@RequestMapping("/parentsGroup/teacher")
public class ParentsGroupTeachersController extends AbstractController {

	@Autowired
	private TeacherService	teacherService;


	@RequestMapping("/mylist")
	public ModelAndView list() {
		Teacher teacher = this.teacherService.findByPrincipal();
		Collection<ParentsGroup> res = new ArrayList<>(teacher.getParentsGroups());
		ModelAndView result = createModelAndView("parentsGroup/teacher/mylist");
		result.addObject("parentsGroups", res);
		result.addObject("myList", true);
		result.addObject("logueadoId", teacher.getId());
		return result;
	}

}
