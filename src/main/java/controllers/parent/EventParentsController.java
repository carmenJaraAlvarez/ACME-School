
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.EventService;
import services.ParentsGroupService;
import controllers.AbstractController;
import domain.Event;
import domain.ParentsGroup;

@Controller
@RequestMapping("/event/parent")
public class EventParentsController extends AbstractController {

	@Autowired
	private EventService		eventService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ParentsGroupService	parentsGroupService;


	// Panic handler ----------------------------------------------------------

	public EventParentsController() {
		super();
	}

	// ---------------------------------------------------------------		

	@RequestMapping("/display")
	public ModelAndView display(@RequestParam final int eventId) {
		ModelAndView result = createModelAndView("event/display");
		Event event = this.eventService.findOne(eventId);
		result.addObject("event", event);
		if (event.getLocation() == null || event.getLocation().getEastCoordinate().isEmpty() || event.getLocation().getNorthCoordinate().isEmpty()) {
			result.addObject("location", false);
		} else {
			result.addObject("location", true);
		}
		//Para comprobar si el grupo del evento es del que esta logueado o no
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PARENT);
		if (this.actorService.checkAuthenticate() && this.parentsGroupService.checkParentInGroup(event.getParentsGroup())) {
			result.addObject("isPrincipal", true);
		}
		//---------------------------------------------------------
		return result;
	}

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int parentsGroupId) {
		ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);
		Collection<Event> all = parentsGroup.getEvents();
		this.parentsGroupService.checkMember(parentsGroup);
		result = createModelAndView("event/list");
		result.addObject("uri", "event/parent/list.do?parentsGroupId=" + parentsGroupId);
		result.addObject("event", all);
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("parentsGroup", parentsGroup);
		//Para comprobar si el grupo del evento es del que esta logueado o no
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PARENT);
		if (this.actorService.checkAuthenticate() && this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authority)) {
			result.addObject("logueadoId", this.actorService.findByPrincipal().getId());
		}
		//---------------------------------------------------------
		return result;
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int parentsGroupId) {
		final ModelAndView result = createModelAndView("event/edit");
		final Event event = this.eventService.create(parentsGroupId);

		result.addObject("event", event);

		result.addObject("uri", "event/parent/list.do?parentsGroupId=" + parentsGroupId);

		return result;
	}
	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int eventId) {
		Event event;
		event = this.eventService.findOneToEdit(eventId);
		int parentsGroupId = event.getParentsGroup().getId();
		final ModelAndView result = createModelAndView("event/edit");
		result.addObject("uri", "event/parent/list.do?parentsGroupId=" + parentsGroupId);
		result.addObject("event", event);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Event event, final BindingResult binding) {
		ModelAndView result = null;

		Event reconstructed = this.eventService.reconstruct(event, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndView(event);
		else {
			try {
				if (this.eventService.checkFecha(event)) {
					this.eventService.save(event);
					result = new ModelAndView("redirect:/event/parent/list.do?parentsGroupId=" + event.getParentsGroup().getId());
				} else {
					result = this.createEditModelAndView(event, "event.date.error");
				}
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(reconstructed, "event.commit.error");
			}

		}
		return result;

	}

	private ModelAndView createEditModelAndView(final Event event) {
		ModelAndView result;
		result = this.createEditModelAndView(event, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Event event, final String message) {
		ModelAndView result;
		result = createModelAndView("event/edit");
		result.addObject("event", event);
		result.addObject("message", message);
		result.addObject("uri", "event/parent/list.do?parentsGroupId=" + event.getParentsGroup().getId());

		return result;
	}

}
