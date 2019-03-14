
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SchoolCalendarService;
import controllers.AbstractController;
import domain.SchoolCalendar;

@Controller
@RequestMapping("/schoolCalendar/administrator")
public class SchoolCalendarAdministratorController extends AbstractController {

	@Autowired
	private SchoolCalendarService	schoolCalendarService;


	public SchoolCalendarAdministratorController() {
		super();
	}
	@RequestMapping(value = "/create")
	public ModelAndView create() {
		ModelAndView result;
		final SchoolCalendar schoolCalendar = this.schoolCalendarService.create();
		result = super.createModelAndView("schoolCalendar/administrator/create");
		result.addObject(schoolCalendar);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int schoolCalendarId) {
		ModelAndView result;
		final SchoolCalendar schoolCalendar;
		schoolCalendar = this.schoolCalendarService.findOne(schoolCalendarId);
		result = this.createEditModelAndView(schoolCalendar, "schoolCalendar/administrator/edit");

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SchoolCalendar schoolCalendar, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(schoolCalendar, "schoolCalendar/administrator/edit");
		else
			try {
				SchoolCalendar save = this.schoolCalendarService.saveForAdmin(schoolCalendar);
				result = new ModelAndView("redirect:/schoolCalendar/list.do");
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(schoolCalendar, "schoolCalendar.commit.error", "schoolCalendar/administrator/edit");
			}
		return result;
	}
	private ModelAndView createEditModelAndView(final SchoolCalendar schoolCalendar, String uri) {
		ModelAndView result;
		result = this.createEditModelAndView(schoolCalendar, null, uri);
		return result;
	}

	private ModelAndView createEditModelAndView(final SchoolCalendar schoolCalendar, final String message, String uri) {
		ModelAndView result;
		result = super.createModelAndView(uri);
		result.addObject("schoolCalendar", schoolCalendar);
		result.addObject("message", message);

		return result;
	}

}
