
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParentService;
import services.ParentsGroupService;
import controllers.AbstractController;
import domain.Parent;
import domain.ParentsGroup;

@Controller
@RequestMapping("/inscription/parent")
public class InscriptionParentsController extends AbstractController {

	@Autowired
	ParentsGroupService	parentsGroupService;
	@Autowired
	ParentService		parentService;


	@RequestMapping("/createInscription")
	public ModelAndView create(@RequestParam final Integer parentsGroupId) {
		ModelAndView result = createModelAndView("inscription/parent");
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("URL", "inscription/parent/createInscription.do");

		return result;
	}

	@RequestMapping(value = "/createInscription", method = RequestMethod.GET, params = "save")
	public ModelAndView create(@RequestParam String code, @RequestParam int parentsGroupId) {
		ModelAndView result;
		try {
			ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
			Parent parent = this.parentService.findByPrincipal();
			if (parentsGroup.getCode().equals(code)) {
				this.parentsGroupService.addMember(parent, parentsGroup);
				result = new ModelAndView("redirect:/parentsGroup/general/list.do");
			} else {
				result = this.createEditModelAndView(parentsGroupId, "code.commit.error");
			}

		} catch (final Throwable oops) {
			result = this.createEditModelAndView(parentsGroupId, "member.commit.error");
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final int parentsGroupId, final String message) {
		ModelAndView result;
		result = createModelAndView("inscription/parent");
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("message", message);
		result.addObject("URL", "inscription/parent/createInscription.do");
		return result;
	}

	@RequestMapping(value = "/deleteInscription", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int parentsGroupId) {
		ModelAndView result;
		try {
			ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
			Parent parent = this.parentService.findByPrincipal();
			if (parentsGroup.getCreator().equals(parent)) {
				result = this.deleteModelAndView(parentsGroupId, "creator.error");
			} else if (parentsGroup.getGroupAdmins().contains(parent)) {
				this.parentsGroupService.removeAdminOfGroup(parent, parentsGroup);
				result = new ModelAndView("redirect:/parentsGroup/parent/mylist.do");
			} else if (parentsGroup.getMembers().contains(parent)) {
				this.parentsGroupService.removeMemberOfGroup(parent, parentsGroup);
				result = new ModelAndView("redirect:/parentsGroup/parent/mylist.do");
			} else {
				result = this.deleteModelAndView(parentsGroupId, "no.member.error");
			}

		} catch (final Throwable oops) {
			result = this.deleteModelAndView(parentsGroupId, "member.commit.error");
		}
		return result;
	}

	private ModelAndView deleteModelAndView(final int parentsGroupId, final String message) {
		ModelAndView result = createModelAndView("parentsGroup/parent/mylist");
		Parent parent = this.parentService.findByPrincipal();
		final Collection<ParentsGroup> res = this.parentService.getAllGroups(parent);
		result.addObject("parentsGroups", res);
		result.addObject("logueadoId", parent.getId());
		result.addObject("message", message);
		result.addObject("myList", true);
		return result;
	}

}
