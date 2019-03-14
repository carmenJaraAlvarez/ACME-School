
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Event;
import domain.Parent;
import domain.ParentsGroup;

@Service
@Transactional
public class EventService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EventRepository		eventRepository;

	@Autowired
	private ParentsGroupService	parentsgroup;

	@Autowired
	private ParentService		parentService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public EventService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	public Event create(final int parentsGroupId) {
		Event res;
		ParentsGroup group;
		group = this.parentsgroup.findOne(parentsGroupId);
		res = new Event();
		res.setParentsGroup(group);
		return res;
	}
	public Event create() {
		Event res;
		res = new Event();
		return res;
	}
	public Event findOne(final int eventId) {
		return this.eventRepository.findOne(eventId);
	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}
	public Event save(final Event event) {
		this.checkParent(event);
		return this.eventRepository.save(event);

	}

	// Other business methods -------------------------------------------------
	public Event reconstruct(final Event event, final BindingResult binding) {

		Event result;
		result = this.create();
		result.setMoment(event.getMoment());
		result.setDescription(event.getDescription());
		result.setLocation(event.getLocation());
		result.setParentsGroup(event.getParentsGroup());
		if (event.getId() != 0) {

			final Event origin = this.eventRepository.findOne(event.getId());
			result.setId(origin.getId());
			result.setVersion(origin.getVersion());
		}
		this.validator.validate(result, binding);
		return result;

	}
	public Event findOneToEdit(final int eventId) {
		Event event;
		event = this.eventRepository.findOne(eventId);
		this.checkParent(event);
		return event;
	}
	public boolean checkFecha(final Event event) {
		final Date hoy = new Date();
		final Integer mesHoy = this.obtenerMes(hoy);
		final Integer añoHoy = this.obtenerAnyo(hoy);
		final Integer mesEvent = this.obtenerMes(event.getMoment());
		final Integer añoEvent = this.obtenerAnyo(event.getMoment());
		Boolean res = true;
		if (añoEvent < añoHoy)
			res = false;
		else if (añoEvent == añoHoy && mesEvent < mesHoy)
			res = false;
		return res;
	}
	public int obtenerMes(final Date date) {

		if (null == date)
			return 0;
		else {

			final String formato = "MM";
			final SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return Integer.parseInt(dateFormat.format(date));

		}
	}
	public Integer obtenerAnyo(final Date date) {

		if (null == date)
			return 0;
		else {

			final String formato = "yy";
			final SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
			return Integer.parseInt(dateFormat.format(date));

		}

	}
	private void checkParent(final Event event) {
		final Parent parent = this.parentService.findByPrincipal();
		Assert.isTrue(event.getParentsGroup().getMembers().contains(parent) || event.getParentsGroup().getGroupAdmins().contains(parent) || event.getParentsGroup().getCreator().equals(parent), "Not authorized");
	}

	public Double getAverageEventPerGroup() {
		return this.eventRepository.averageEventsPerGroup();
	}
	public Double getStandardDeviationEventPerGroup() {
		return this.eventRepository.standardDeviationEventsPerGroup();
	}
	public Double getMinEventPerGroup() {
		return this.eventRepository.minEventsPerGroup();
	}
	public Double getMaxEventPerGroup() {
		return this.eventRepository.maxEventsPerGroup();
	}
}
