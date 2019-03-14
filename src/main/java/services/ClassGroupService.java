
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ClassGroupRepository;
import domain.ClassGroup;
import domain.ClassTime;
import domain.Parent;
import domain.ParentsGroup;
import domain.Student;
import domain.Teacher;

@Service
@Transactional
public class ClassGroupService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClassGroupRepository	classGroupRepository;

	@Autowired
	private ParentService			parentService;

	@Autowired
	private LevelService			levelService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------

	public ClassGroupService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public ClassGroup create(final Integer levelId) {
		final ClassGroup res = new ClassGroup();
		res.setClassTimes(new ArrayList<ClassTime>());
		res.setParentsGroups(new ArrayList<ParentsGroup>());
		res.setStudents(new ArrayList<Student>());
		res.setTeachers(new ArrayList<Teacher>());
		res.setLevel(this.levelService.findOne(levelId));
		return res;
	}

	public ClassGroup save(final ClassGroup cg) {
		this.checkConcurrency(cg);
		return this.classGroupRepository.save(cg);
	}

	public ClassGroup saveForParent(final ClassGroup cg) {
		this.checkPrincipalParent();
		return this.save(cg);
	}

	public ClassGroup findOne(final int classGroupId) {
		return this.classGroupRepository.findOne(classGroupId);
	}

	public ClassGroup findOneToEdit(final int classGroupId) {
		this.checkPrincipalParent();
		return this.findOne(classGroupId);
	}

	public Collection<ClassGroup> findAll() {
		return this.classGroupRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public ClassGroup reconstruct(final ClassGroup classGroup, final BindingResult binding) {
		ClassGroup res;
		if (classGroup.getId() == 0)
			res = classGroup;
		else {
			res = new ClassGroup();
			final ClassGroup aux = this.classGroupRepository.findOne(classGroup.getId());
			res.setName(classGroup.getName());
			res.setId(aux.getId());
			res.setVersion(aux.getVersion());
			res.setClassTimes(aux.getClassTimes());
			res.setLevel(aux.getLevel());
			res.setParentsGroups(aux.getParentsGroups());
			res.setStudents(aux.getStudents());
			res.setTeachers(aux.getTeachers());
		}

		this.validator.validate(res, binding);

		return res;
	}

	private void checkPrincipalParent() {
		Parent logged = null;
		try {
			logged = this.parentService.findByPrincipal();
		} catch (final Exception e) {
			Assert.notNull(logged);
		}
	}

	private void checkConcurrency(final ClassGroup classGroup) {
		if (classGroup.getId() != 0) {
			final ClassGroup c = this.findOne(classGroup.getId());
			Assert.isTrue(classGroup.getVersion() == c.getVersion());
		}
	}

	public boolean checkname(ClassGroup reconstructed) {
		boolean res = false;
		Collection<ClassGroup> sameLevel = reconstructed.getLevel().getClassGroups();
		for (ClassGroup cg : sameLevel) {
			if (cg.getName().toLowerCase().equals(reconstructed.getName().toLowerCase())) {
				res = true;
				break;
			}
		}
		return res;
	}
	public boolean checkingConcurrency(ClassGroup classGroup) {
		boolean res = false;
		if (classGroup.getId() != 0) {
			ClassGroup BBDD = this.classGroupRepository.findOne(classGroup.getId());
			if (BBDD.getVersion() != classGroup.getVersion()) {
				res = true;
			}
		}
		return res;
	}
}
