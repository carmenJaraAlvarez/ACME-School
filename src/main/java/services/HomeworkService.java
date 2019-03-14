
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.HomeworkRepository;
import domain.Homework;
import domain.Parent;
import domain.ParentsGroup;

@Service
@Transactional
public class HomeworkService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private HomeworkRepository	homeworkRepository;

	@Autowired
	private ParentsGroupService	parentsGroupService;

	@Autowired
	private ParentService		parentService;

	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public HomeworkService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Homework create(final Integer parentsGroupId) {
		final ParentsGroup pg = this.parentsGroupService.findOne(parentsGroupId);
		final Homework res = new Homework();
		res.setParentsGroup(pg);
		return res;
	}

	public Homework save(final Homework hw) {
		this.checkConcurrency(hw);
		return this.homeworkRepository.save(hw);
	}

	public Homework saveForParent(final Homework homework) {
		this.checkPrincipalParent();
		return this.save(homework);
	}

	public Homework findOne(final Integer homeworkId) {
		return this.homeworkRepository.findOne(homeworkId);
	}

	public Homework findOneToEdit(final Integer homeworkId) {
		this.checkPrincipalParent();
		return this.findOne(homeworkId);
	}

	// Other business methods -------------------------------------------------

	public Homework reconstruct(final Homework homework, final BindingResult binding) {
		Homework res;
		if (homework.getId() == 0)
			res = homework;
		else {
			res = this.homeworkRepository.findOne(homework.getId());
			res.setTitle(homework.getTitle());
			res.setDescription(homework.getDescription());
			res.setSubject(homework.getSubject());
			res.setMoment(homework.getMoment());
		}

		this.validator.validate(res, binding);

		return res;
	}

	private void checkPrincipalParent() {
		try {
			final Parent logged = this.parentService.findByPrincipal();
			Assert.notNull(logged);
		} catch (final Exception e) {
		}
	}

	private void checkConcurrency(final Homework homework) {
		if (homework.getId() != 0) {
			final Homework c = this.findOne(homework.getId());
			Assert.isTrue(homework.getVersion() == c.getVersion());
		}
	}

	public Double getAverageHomeworkPerSubject() {
		return this.homeworkRepository.averageHomeworkPerSubject();
	}
	public Double getStandardDeviationHomeworkPerSubject() {
		return this.homeworkRepository.standardDeviationHomeworkPerSubject();
	}
	public Double getMinHomeworkPerSubject() {
		return this.homeworkRepository.minHomeworkPerSubject();
	}
	public Double getMaxHomeworkPerSubject() {
		return this.homeworkRepository.maxHomeworkPerSubject();
	}
}
