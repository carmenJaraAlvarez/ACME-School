package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ParentService;
import services.SchoolService;
import services.TeacherService;
import domain.Actor;
import domain.Parent;
import domain.School;
import domain.Teacher;

@Controller
@RequestMapping("/school")
public class SchoolController extends AbstractController {

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private ParentService parentService;

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private TeacherService teacherService;

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

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final Integer schoolId) {
		ModelAndView res;

		final School school = this.schoolService.findOne(schoolId);
		Assert.notNull(school);
		res = super.createModelAndView("school/display");
		res.addObject("school", school);
		res.addObject("levels", school.getLevels());
		if (this.actorService.checkAuthenticate()) {
			final Actor actor = this.actorService.findByPrincipal();
			final Authority authorityTeacher = new Authority();
			authorityTeacher.setAuthority(Authority.TEACHER);
			if (actor.getUserAccount().getAuthorities()
					.contains(authorityTeacher)) {
				Teacher teacher = this.teacherService.findOne(actor.getId());
				res.addObject("schoolLogueadoId", teacher.getSchool().getId());
				res.addObject("listClassGroupTeacher", teacher.getClassGroups());
			}
		}

		return res;
	}
}
