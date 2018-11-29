
package controllers;

import java.util.Collection;

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
	ActorService actorService;


	// Panic handler ----------------------------------------------------------

	public ParentsGroupController() {
		super();
	}

	// ---------------------------------------------------------------		

	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<ParentsGroup> all = this.parentsGroupService.findAll();
		result = new ModelAndView("parentsGroup/general/list");
		result.addObject("uri", "parentsGroup/general/list.do");
		result.addObject("parentsGroup",all);
		//Para comprobar si el grupo es del que esta logueado o no
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PARENT);
		if(this.actorService.checkAuthenticate() && this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authority)){
			result.addObject("logueadoId", this.actorService.findByPrincipal().getId());
		}
		//---------------------------------------------------------
		return result;
	}

}
