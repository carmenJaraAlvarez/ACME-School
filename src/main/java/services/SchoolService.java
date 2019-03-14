
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.SchoolRepository;
import domain.ClassGroup;
import domain.Level;
import domain.Parent;
import domain.School;
import domain.Teacher;

@Service
@Transactional
public class SchoolService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private SchoolRepository	schoolRepository;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private LevelService		levelService;
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public SchoolService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public School create() {
		final School res = new School();
		res.setLevels(new ArrayList<Level>());

		res.setTeachers(new ArrayList<Teacher>());
		return res;
	}

	public School save(final School school) {

		final School save = this.schoolRepository.save(school);
		return save;
	}

	public School saveForParent(final School school) {
		this.checkPrincipalParent();
		final School res = this.save(school);
		return res;
	}

	public School findOne(final Integer schoolId) {
		return this.schoolRepository.findOne(schoolId);
	}

	public School findOneToEdit(final int schoolId) {
		this.checkPrincipalParent();
		return this.findOne(schoolId);
	}

	public Collection<School> findAll() {
		return this.schoolRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public School reconstruct(final School school, final BindingResult binding) {
		School res;
		if (school.getId() == 0)
			res = school;
		else {
			res = new School();
			final School aux = this.schoolRepository.findOne(school.getId());
			res.setNameSchool(school.getNameSchool());
			res.setAddress(school.getAddress());
			res.setEmailSchool(school.getEmailSchool());
			res.setImage(school.getImage());
			res.setPhoneNumber(school.getPhoneNumber());
			res.setLevels(aux.getLevels());
			res.setTeachers(aux.getTeachers());
			res.setId(school.getId());
			res.setVersion(school.getVersion());
		}

		this.validator.validate(res, binding);
		return res;
	}

	public void addLevels(final School school) {
		final Level level1 = this.levelService.create(school);
		final Level level2 = this.levelService.create(school);
		final Level level3 = this.levelService.create(school);
		final Level level4 = this.levelService.create(school);
		final Level level5 = this.levelService.create(school);
		final Level level6 = this.levelService.create(school);
		level1.setLevel(1);
		level2.setLevel(2);
		level3.setLevel(3);
		level4.setLevel(4);
		level5.setLevel(5);
		level6.setLevel(6);
		this.levelService.save(level1);
		this.levelService.save(level2);
		this.levelService.save(level3);
		this.levelService.save(level4);
		this.levelService.save(level5);
		this.levelService.save(level6);
	}

	private void checkPrincipalParent() {
		Parent logged = null;
		try {
			logged = this.parentService.findByPrincipal();
		} catch (final Exception e) {
			Assert.notNull(logged);
		}
	}
	public Collection<ClassGroup> classGroupOfSchool(final School school) {
		final Collection<ClassGroup> result = new ArrayList<ClassGroup>();
		for (final Level level : school.getLevels())
			result.addAll(level.getClassGroups());

		return result;
	}

	public Collection<School> findByKeyWord(final String keyWord) {
		final Collection<School> schools = this.schoolRepository.findByKeyWord(keyWord);
		return schools;
	}

	public boolean checkConcurrency(final School school) {
		boolean res = false;//true if there is concurrency
		if (school.getId() != 0) {
			final School c = this.findOne(school.getId());
			if (school.getVersion() != c.getVersion()) {
				res = true;
			}
		}
		return res;
	}

	public Collection<School> getSchoolMostTeachers() {
		return this.schoolRepository.getSchoolWithMostTeachers();
	}
}
