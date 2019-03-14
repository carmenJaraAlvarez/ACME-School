
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParentService;
import services.ParentsGroupService;
import services.SchoolCalendarService;
import controllers.AbstractController;
import domain.Parent;
import domain.ParentsGroup;
import domain.SchoolCalendar;
import forms.LinkForm;

@Controller
@RequestMapping("/schoolCalendar/parent")
public class SchoolCalendarParentsController extends AbstractController {

	@Autowired
	private ParentsGroupService		parentsGroupService;
	@Autowired
	private SchoolCalendarService	schoolCalendarService;
	@Autowired
	private ParentService			parentService;


	// Panic handler ----------------------------------------------------------

	public SchoolCalendarParentsController() {
		super();
	}

	//  ---------------------------------------------------------------		

	@RequestMapping(value = "/link", method = RequestMethod.GET)
	public ModelAndView link(@RequestParam final int schoolCalendarId) {
		ModelAndView result;
		LinkForm linkForm = new LinkForm();
		linkForm.setIdCalendar(schoolCalendarId);
		result = super.createModelAndView("schoolCalendar/parent/link");

		Parent logged = this.parentService.findByPrincipal();
		Collection<ParentsGroup> pg = this.parentService.getAllGroups(logged);

		result.addObject("parentsGroups", pg);
		result.addObject("linkForm", linkForm);

		return result;
	}

	@RequestMapping(value = "/link", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final LinkForm linkForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = super.createModelAndView("schoolCalendar/parent/link");
			result.addObject(linkForm);
			Parent logged = this.parentService.findByPrincipal();
			Collection<ParentsGroup> pg = this.parentService.getAllGroups(logged);
			result.addObject("parentsGroups", pg);
		} else
			try {
				int idCalendar = linkForm.getIdCalendar();
				int idGroup = linkForm.getIdGroup();
				ParentsGroup g = this.parentsGroupService.findOneToEdit(idGroup);
				SchoolCalendar schoolCalendar = this.schoolCalendarService.findOne(idCalendar);
				g.setSchoolCalendar(schoolCalendar);
				this.parentsGroupService.save(g);
				result = new ModelAndView("redirect:/schoolCalendar/list.do");
			} catch (final Throwable oops) {
				result = super.createModelAndView("schoolCalendar/parent/link");
				result.addObject(linkForm);
				Parent logged = this.parentService.findByPrincipal();
				Collection<ParentsGroup> pg = this.parentService.getAllGroups(logged);
				result.addObject("parentsGroups", pg);
				result.addObject("message", "schoolCalendar.commit.error");

			}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int parentsGroupId) {
		ModelAndView result;
		result = createModelAndView("schoolCalendar/display");
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		SchoolCalendar calendar = parentsGroup.getSchoolCalendar();
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		if (calendar == null) {
			result.addObject("noCalendar", true);
		} else {
			result.addObject("schoolCalendar", calendar);
		}
		result.addObject("parentsGroup", parentsGroup);
		result.addObject("displayGroup", true);
		return result;
	}

}
