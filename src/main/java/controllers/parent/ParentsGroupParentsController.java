
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ParentService;
import services.ParentsGroupService;
import controllers.AbstractController;
import domain.ParentsGroup;

@Controller
@RequestMapping("/parentsGroup/parent")
public class ParentsGroupParentsController extends AbstractController {

	@Autowired
	ParentsGroupService	parentsGroupService;
	@Autowired
	ParentService		parentService;


	// Panic handler ----------------------------------------------------------

	public ParentsGroupParentsController() {
		super();
	}

	//  ---------------------------------------------------------------	
	@RequestMapping("/mylist")
	public ModelAndView list() {
		Collection<ParentsGroup> parentsGroups = this.parentService.findByPrincipal().getParentsGroupsMemberOf();
		ModelAndView result = new ModelAndView("parentsGroup/parent/mylist");
		result.addObject(parentsGroups);
		return result;
	}

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.create();
		result = new ModelAndView("parentsGroup/parent/edit");
		result.addObject(parentsGroup);
		return result;
	}
	@RequestMapping("/edit")
	public ModelAndView edit() {
		ModelAndView result = null;

		return result;
	}
}
