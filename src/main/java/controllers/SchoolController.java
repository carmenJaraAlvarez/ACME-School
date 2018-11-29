
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ParentService;
import services.SchoolService;
import domain.Parent;
import domain.School;

@Controller
@RequestMapping("/school")
public class SchoolController extends AbstractController {

	@Autowired
	SchoolService	schoolService;

	@Autowired
	ParentService	parentService;


	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		final Collection<School> schools = this.schoolService.findAll();

		result = super.createModelAndView("school/general/list");
		result.addObject("schools", schools);

		try {
			final Parent logged = this.parentService.findByPrincipal();
			if (!logged.equals(null))
				result.addObject("showParentButtons", true);
		} catch (final Throwable oops) {
		}

		return result;
	}
}
