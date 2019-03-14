
package services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EntryRepository;
import domain.Entry;
import domain.Message;
import domain.ParentsGroup;
import domain.Teacher;

@Service
@Transactional
public class EntryService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private EntryRepository	entryRepository;

	@Autowired
	private TeacherService	teacherService;

	@Autowired
	private MessageService	messageService;

	@Autowired
	private Validator		validator;


	// Constructors -----------------------------------------------------------

	public EntryService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Entry create() {
		final Entry res = new Entry();
		res.setTeacher(this.teacherService.findByPrincipal());
		return res;
	}

	public Entry save(final Entry entry) {
		this.checkConcurrency(entry);
		final Entry result = this.entryRepository.save(entry);
		if (entry.getId() == 0) {
			final Message message = new Message();
			message.setParent(null);
			final Calendar calendar = Calendar.getInstance();
			final Date fechaActual = calendar.getTime();
			message.setMoment(fechaActual);
			final String body = result.getTeacher().getId() + "," + result.getTeacher().getName() + "," + result.getId();
			message.setBody(body);
			message.setAttachment(null);
			for (final ParentsGroup pg : result.getTeacher().getParentsGroups())
				this.messageService.saveForTeacher(message, pg.getId());
		}
		return result;
	}
	public Entry findOne(final int entryId) {
		return this.entryRepository.findOne(entryId);
	}
	// Other business methods -------------------------------------------------

	private void checkConcurrency(final Entry entry) {
		if (entry.getId() != 0) {
			final Entry entryDB = this.findOne(entry.getId());
			Assert.isTrue(entry.getVersion() == entryDB.getVersion(), "concurrency error");
		}
	}
	public Entry findOneToEdit(final int entryId) {
		Entry entry;
		entry = this.entryRepository.findOne(entryId);
		this.checkTeacher(entry);
		return entry;
	}

	private void checkTeacher(final Entry entry) {
		final Teacher teacher = this.teacherService.findByPrincipal();
		Assert.isTrue(entry.getTeacher().equals(teacher), "Not authorized");
	}

	public Entry reconstruct(final Entry entry, final BindingResult binding) {
		Entry result = this.create();

		if (entry.getId() == 0) {
			final Calendar calendar = Calendar.getInstance();
			final Date fechaActual = calendar.getTime();
			result.setMoment(fechaActual);
			result.setTitle(entry.getTitle());
			result.setBody(entry.getBody());
			if (binding != null)
				this.validator.validate(result, binding);
		} else {
			final Entry entryBBDD = this.findOne(entry.getId());
			if (entryBBDD != null && entryBBDD.getTeacher().equals(this.teacherService.findByPrincipal())) {
				result.setId(entry.getId());
				result.setVersion(entry.getVersion());
				result.setMoment(entryBBDD.getMoment());
				result.setTitle(entry.getTitle());
				result.setBody(entry.getBody());

				if (binding != null)
					this.validator.validate(result, binding);
			} else
				result = null;
		}

		return result;
	}

	public void flush() {
		this.entryRepository.flush();
	}
}
