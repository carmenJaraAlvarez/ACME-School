
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
@RequestMapping("/parent/parent")
public class ParentParentsController extends AbstractController {

	@Autowired
	ParentService		parentService;
	@Autowired
	ParentsGroupService	parentsGroupService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int parentsGroupId) {
		ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		Collection<Parent> parentsAdmin = parentsGroup.getGroupAdmins();
		Collection<Parent> parentsMember = parentsGroup.getMembers();
		Parent parent = this.parentService.findByPrincipal();
		this.parentsGroupService.checkMember(parentsGroup);
		result = createModelAndView("parents/list");
		result.addObject("parentsAdmin", parentsAdmin);
		result.addObject("parentsMember", parentsMember);
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("parentsGroup", parentsGroup);
		if (parentsGroup.getCreator().equals(parent))
			result.addObject("isCreator", true);
		for (Parent admin : parentsGroup.getGroupAdmins()) {
			if (admin.equals(parent)) {
				result.addObject("isAdmin", true);
			}
		}
		result.addObject("logged", parent.getId());
		return result;
	}

	@RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
	public ModelAndView addAdmin(@RequestParam final int actorId, @RequestParam final int parentsGroupId) {

		ModelAndView result;

		try {

			ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);

			Parent parent = this.parentService.findOne(actorId);

			this.parentsGroupService.removeMemberOfGroup(parent, parentsGroup);

			this.parentsGroupService.addAdmin(parent, parentsGroup);

			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

		}

		return result;

	}

	@RequestMapping(value = "/deleteAdmin", method = RequestMethod.GET)
	public ModelAndView deleteAdmin(@RequestParam final int actorId, @RequestParam final int parentsGroupId) {

		ModelAndView result;

		try {

			ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);

			Parent parent = this.parentService.findOne(actorId);

			this.parentsGroupService.removeAdminOfGroup(parent, parentsGroup);

			this.parentsGroupService.addMember(parent, parentsGroup);

			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

		}

		return result;

	}

	@RequestMapping(value = "/removeAdmin", method = RequestMethod.GET)
	public ModelAndView removeAdmin(@RequestParam final int actorId, @RequestParam final int parentsGroupId) {

		ModelAndView result;

		try {

			ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);

			Parent parent = this.parentService.findOne(actorId);

			this.parentsGroupService.removeAdminOfGroup(parent, parentsGroup);

			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

		}

		return result;

	}

	@RequestMapping(value = "/removeMember", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam final int actorId, @RequestParam final int parentsGroupId) {

		ModelAndView result;
		if (actorId == this.parentService.findByPrincipal().getId()) {
			result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);
			result.addObject("message", "parentsGroup.remove.error");
		} else {
			try {

				ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);

				Parent parent = this.parentService.findOne(actorId);

				this.parentsGroupService.removeMemberOfGroup(parent, parentsGroup);

				result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

			} catch (final Throwable oops) {

				result = new ModelAndView("redirect:list.do?parentsGroupId=" + parentsGroupId);

			}
		}

		return result;

	}

}
