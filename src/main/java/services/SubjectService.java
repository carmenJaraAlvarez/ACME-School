
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SubjectRepository;
import domain.ClassTime;
import domain.Homework;
import domain.Level;
import domain.Subject;

@Service
@Transactional
public class SubjectService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SubjectRepository	subjectRepository;

	//-----------------------------------------------
	@Autowired
	private LevelService		levelService;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public SubjectService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Collection<Subject> findAll() {
		return this.subjectRepository.findAll();
	}

	public Subject findOne(final int subjectId) {
		final Subject res = this.subjectRepository.findOne(subjectId);
		return res;
	}
	private Subject save(final Subject subject) {
		final Subject res = this.subjectRepository.save(subject);
		return res;
	}

	public Subject create() {
		final Subject res = new Subject();
		final Collection<ClassTime> classTimes = new ArrayList<>();
		final Collection<Homework> homeworks = new ArrayList<>();
		res.setClassTimes(classTimes);
		res.setHomeworks(homeworks);
		return res;
	}
	public Subject create(final int levelId) {
		final Subject res = this.create();
		final Level level = this.levelService.findOne(levelId);
		res.setLevel(level);
		return res;
	}

	// Other business methods -------------------------------------------------

	public Collection<Subject> findByLevel(final int levelId) {
		return this.subjectRepository.finByLevel(levelId);
	}

	public Subject findOneToEdit(final int subjectId) {
		final Subject subject = this.findOne(subjectId);
		Assert.isTrue(this.parentService.checkIsParent());
		return subject;
	}

	public Subject reconstruct(final Subject subject, final BindingResult binding) {
		Subject res;
		if (subject.getId() == 0)
			res = this.create(subject.getLevel().getId());
		else
			res = this.subjectRepository.findOne(subject.getId());
		res.setName(subject.getName());
		this.validator.validate(res, binding);

		return res;
	}

	public Subject saveForParent(final Subject subject) {
		Assert.isTrue(this.parentService.checkIsParent(), "No parent");
		this.checkConcurrency(subject);
		final Subject res = this.save(subject);
		return res;
	}
	private void checkConcurrency(final Subject subject) {
		if (subject.getId() != 0) {
			final Subject c = this.findOne(subject.getId());
			Assert.isTrue(subject.getVersion() == c.getVersion(), "concurrency error");
		}
	}

}
