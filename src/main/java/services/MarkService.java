
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MarkRepository;
import domain.Mark;
import domain.Student;

@Service
@Transactional
public class MarkService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private MarkRepository	markRepository;
	//----------------------------------------
	@Autowired
	private ParentService	parentService;
	@Autowired
	private StudentService	studentService;
	@Autowired
	private TeacherService	teacherService;
	@Autowired
	private Validator		validator;


	// Constructors -----------------------------------------------------------

	public MarkService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Mark create() {
		Mark res;
		res = new Mark();
		res.setTeacher(this.teacherService.findByPrincipal());
		return res;
	}

	public Mark findOneToEdit(final int markId) {
		Mark res;
		res = this.markRepository.findOne(markId);
		this.checkTeacher(res);
		return res;
	}

	public Mark reconstruct(final Mark mark, final BindingResult binding) {
		final Mark res = this.create();
		Mark markBD = null;
		res.setComment(mark.getComment());
		res.setSubject(mark.getSubject());
		res.setValue(mark.getValue());
		res.setStudent(mark.getStudent());
		res.setDate(new Date());
		if (mark.getId() != 0) {
			markBD = this.findOneToEdit(mark.getId());
			res.setId(markBD.getId());
			res.setVersion(markBD.getVersion());
		}

		if (binding != null)
			this.validator.validate(res, binding);
		return res;
	}

	public Mark saveForTeacher(final Mark mark) {
		Mark res = null;
		this.checkTeacher(mark);
		this.checkConcurrency(mark);
		res = this.markRepository.save(mark);
		return res;
	}

	private void checkTeacher(final Mark mark) {
		Assert.isTrue(mark.getStudent().getClassGroup().getTeachers().contains(this.teacherService.findByPrincipal()));

	}
	public Collection<Mark> findByStudentForParent(final int studentId) {
		final Student student = this.studentService.findOne(studentId);
		Assert.isTrue(this.parentService.findByPrincipal().equals(student.getParent()), "Not Authorized");
		Collection<Mark> res;
		res = this.markRepository.findByStudent(studentId);
		return res;
	}
	// Other business methods -------------------------------------------------

	public Collection<Mark> findByStudentForTeacher(final int studentId) {
		final Student student = this.studentService.findOne(studentId);
		Assert.isTrue(this.teacherService.findByPrincipal().getClassGroups().contains(student.getClassGroup()), "Not Authorized");
		Collection<Mark> res;
		res = this.markRepository.findByStudent(studentId);
		return res;
	}

	public Mark create(final int studentId) {
		final Mark res = this.create();
		final Student student = this.studentService.findOne(studentId);
		Assert.isTrue(this.teacherService.findByPrincipal().getClassGroups().contains(student.getClassGroup()), "He/she is not in your class");
		res.setStudent(student);
		return res;
	}
	private void checkConcurrency(final Mark mark) {
		if (mark.getId() != 0) {
			final Mark markDB = this.findOne(mark.getId());
			Assert.isTrue(mark.getVersion() == markDB.getVersion(), "concurrency error");
		}
	}
	private Mark findOne(final int id) {
		return this.markRepository.findOne(id);
	}
	public int getPassedMarksLastYear() {
		return this.markRepository.getPassedMarks();
	}

	public void flush() {
		this.markRepository.flush();
	}
}
