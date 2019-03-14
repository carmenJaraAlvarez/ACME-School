
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ParentService;
import services.SchoolCalendarService;
import domain.Actor;
import domain.SchoolCalendar;

@Controller
@RequestMapping("/schoolCalendar")
public class SchoolCalendarController extends AbstractController {

	@Autowired
	SchoolCalendarService	schoolCalendarService;

	@Autowired
	ParentService			parentService;

	@Autowired
	ActorService			actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = super.createModelAndView("schoolCalendar/list");
		Collection<SchoolCalendar> schoolCalendars = this.schoolCalendarService.findAll();
		result.addObject("schoolCalendars", schoolCalendars);
		boolean hasGroup = false;
		if (this.actorService.checkAuthenticate()) {
			Actor actor = this.actorService.findByPrincipal();
			if (actor.getUserAccount().getAuthorities().contains("PARENT") && this.parentService.getAllGroups(this.parentService.findByPrincipal()).size() > 0)
				;
			hasGroup = true;
		}
		result.addObject("hasGroup", hasGroup);
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int schoolCalendarId) {
		ModelAndView result;
		result = super.createModelAndView("schoolCalendar/display");
		SchoolCalendar calendar = this.schoolCalendarService.findOne(schoolCalendarId);
		result.addObject(calendar);
		return result;
	}

}
