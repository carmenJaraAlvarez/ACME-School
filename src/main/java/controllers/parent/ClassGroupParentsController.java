package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SchoolService;

import controllers.AbstractController;
import domain.ClassGroup;
import domain.School;

@Controller
@RequestMapping("/classGroup/parent")
public class ClassGroupParentsController extends AbstractController {
	
	@Autowired
	SchoolService schoolService;
	
	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int schoolId) {
		ModelAndView result;
		School school = this.schoolService.findOne(schoolId);
		Collection<ClassGroup> classGroups = this.schoolService.classGroupOfSchool(school);
		result = new ModelAndView("classGroup/list");
		result.addObject("classGroups", classGroups);
		return result;
	}

}
