
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.SchoolCalendarRepository;
import domain.Actor;
import domain.Parent;
import domain.ParentsGroup;
import domain.SchoolCalendar;

@Service
@Transactional
public class SchoolCalendarService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SchoolCalendarRepository	schoolCalendarRepository;

	// Service  ------------
	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private ParentService				parentService;
	@Autowired
	private Validator					validator;
	@Autowired
	private ActorService				actorService;


	// Constructors -----------------------------------------------------------

	public SchoolCalendarService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Collection<SchoolCalendar> findAll() {
		return this.schoolCalendarRepository.findAll();
	}
	public SchoolCalendar findOne(final Integer schoolCalendarId) {
		return this.schoolCalendarRepository.findOne(schoolCalendarId);
	}

	// Other business methods -------------------------------------------------
	public SchoolCalendar create() {
		SchoolCalendar res;
		this.checkPrincipalIsAdmin();
		res = new SchoolCalendar();
		return res;
	}
	private void checkPrincipalIsAdmin() {
		Assert.isTrue(this.administratorService.findByPrincipal() != null, "Is not administrator");
	}
	public void link(final ParentsGroup pg, final SchoolCalendar calendar) {
		this.checkPrincipalParentsGroup(pg);
		pg.setSchoolCalendar(calendar);

	}
	private void checkPrincipalParentsGroup(final ParentsGroup pg) {
		Assert.isTrue(this.parentService.findByPrincipal() != null, "Is not parent");
		final Collection<Parent> all = new ArrayList<Parent>();
		all.addAll(pg.getMembers());
		all.addAll(pg.getGroupAdmins());
		all.add(pg.getCreator());
		Assert.isTrue(all.contains(this.parentService.findByPrincipal()));
	}

	public SchoolCalendar saveForParent(final SchoolCalendar schoolCalendar) {
		this.checkPrincipalParent();
		final SchoolCalendar res = this.save(schoolCalendar);
		return res;
	}
	private SchoolCalendar save(final SchoolCalendar schoolCalendar) {
		final SchoolCalendar save = this.schoolCalendarRepository.save(schoolCalendar);
		return save;
	}

	private void checkPrincipalParent() {
		Assert.isTrue(this.actorService.checkAuthenticate(), "Not authenticated");
		final Actor actor = this.actorService.findByPrincipal();
		System.out.println(actor.getUserAccount().getAuthorities());
		//TODO Assert.isTrue(actor.getUserAccount().getAuthorities().contains("PARENT"), "Not Authorized");
	}

	public SchoolCalendar saveForAdmin(final SchoolCalendar schoolCalendar) {
		Assert.isTrue(this.administratorService.checkIsAdmin());
		final SchoolCalendar res = this.save(schoolCalendar);
		return res;
	}

	private void checkConcurrency(final SchoolCalendar schoolCalendar) {
		if (schoolCalendar.getId() != 0) {
			final SchoolCalendar schoolCalendarDB = this.findOne(schoolCalendar.getId());
			Assert.isTrue(schoolCalendar.getVersion() == schoolCalendarDB.getVersion());
		}
	}

	public void flush() {
		this.schoolCalendarRepository.flush();
	}

}
