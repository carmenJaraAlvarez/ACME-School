
package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.ParentsGroupService;
import domain.ParentsGroup;

@Controller
@RequestMapping("/parentsGroup/general")
public class ParentsGroupController extends AbstractController {

	@Autowired
	ParentsGroupService	parentsGroupService;
	@Autowired
	ActorService		actorService;


	// Panic handler ----------------------------------------------------------

	public ParentsGroupController() {
		super();
	}

	// ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<ParentsGroup> all = new HashSet<>(this.parentsGroupService.findAll());
		result = createModelAndView("parentsGroup/general/list");
		result.addObject("uri", "parentsGroup/general/list.do");
		result.addObject("parentsGroups", all);
		//Para comprobar si el grupo es del que esta logueado o no
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PARENT);
		final Authority authorityTeacher = new Authority();
		authorityTeacher.setAuthority(Authority.TEACHER);
		if (this.actorService.checkAuthenticate() && (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authority) || this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityTeacher))) {
			result.addObject("logueadoId", this.actorService.findByPrincipal().getId());
		}

		return result;
	}

}
