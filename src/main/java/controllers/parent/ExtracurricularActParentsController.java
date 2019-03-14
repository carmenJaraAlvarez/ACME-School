
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

import services.ExtracurricularActivityService;
import services.ParentsGroupService;
import controllers.AbstractController;
import domain.ExtracurricularActivity;
import domain.ParentsGroup;

@Controller
@RequestMapping("/extracurricularActivity/parent")
public class ExtracurricularActParentsController extends AbstractController {

	@Autowired
	private ExtracurricularActivityService	extracurricularActService;

	@Autowired
	private ParentsGroupService				parentsGroupService;


	// Constructors ----------------------------------------------------------

	public ExtracurricularActParentsController() {
		super();
	}

	//  ---------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int parentsGroupId) {
		ModelAndView result;
		final Collection<ExtracurricularActivity> activities = this.parentsGroupService.findOne(parentsGroupId).getExtracurricularActivities();
		result = super.createModelAndView("extracurrAct/list");
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		this.parentsGroupService.checkMember(parentsGroup);
		result.addObject("activities", activities);
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("parentsGroup", parentsGroup);
		return result;
	}

	@RequestMapping(value = "/create")
	public ModelAndView create(final Integer parentsGroupId) {
		ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		final ExtracurricularActivity extracurricularActivity = this.extracurricularActService.create(parentsGroupId);
		result = this.createEditModelAndView(extracurricularActivity);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int extracurricularActId) {
		ModelAndView result;
		final ExtracurricularActivity extracurricularAct;
		extracurricularAct = this.extracurricularActService.findOneToEdit(extracurricularActId);
		result = this.createEditModelAndView(extracurricularAct);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ExtracurricularActivity extracurricularAct, final BindingResult binding) {
		ModelAndView result;
		final ExtracurricularActivity reconstructed = this.extracurricularActService.reconstruct(extracurricularAct, binding);
		ParentsGroup parentsGroup = reconstructed.getParentsGroup();
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		if (binding.hasErrors())
			result = this.createEditModelAndView(extracurricularAct);
		else
			try {
				final ExtracurricularActivity save = this.extracurricularActService.saveForParent(extracurricularAct);
				result = new ModelAndView("redirect:/extracurricularActivity/parent/list.do?parentsGroupId=" + save.getParentsGroup().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(reconstructed, "extracurrAct.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ExtracurricularActivity extracurricularAct, final BindingResult binding) {
		ModelAndView result;
		ParentsGroup parentsGroup = extracurricularAct.getParentsGroup();
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		try {
			this.extracurricularActService.deleteForParents(extracurricularAct);
			result = new ModelAndView("redirect:/extracurricularActivity/parent/list.do?parentsGroupId=" + extracurricularAct.getParentsGroup().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(extracurricularAct, "folder.commit.error");
		}

		return result;
	}

	private ModelAndView createEditModelAndView(final ExtracurricularActivity extracurricularActivity) {
		ModelAndView result;
		result = this.createEditModelAndView(extracurricularActivity, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final ExtracurricularActivity extracurricularActivity, final String message) {
		ModelAndView result;
		result = super.createModelAndView("extracurrAct/edit");
		result.addObject("extracurricularActivity", extracurricularActivity);
		result.addObject("parentsGroupId", extracurricularActivity.getParentsGroup().getId());
		result.addObject("message", message);

		return result;
	}

}
