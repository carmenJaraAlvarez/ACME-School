
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
		final School res = this.schoolRepository.save(school);
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
			res = this.schoolRepository.findOne(school.getId());
			res.setNameSchool(school.getNameSchool());
			res.setAddress(school.getAddress());
			res.setEmailSchool(school.getEmailSchool());
			res.setImage(school.getImage());
			res.setPhoneNumber(school.getPhoneNumber());
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

}
