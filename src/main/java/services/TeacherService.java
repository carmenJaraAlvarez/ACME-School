
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.TeacherRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ClassGroup;
import domain.Entry;
import domain.Folder;
import domain.Mark;
import domain.ParentsGroup;
import domain.PrivateMessage;
import domain.Teacher;
import forms.ActorForm;
import forms.CreateActorForm;

@Service
@Transactional
public class TeacherService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private TeacherRepository	teacherRepository;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;

	// Constructors -----------------------------------------------------------

	public TeacherService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Teacher save(final Teacher teacher) {
		this.checkPrincipal(teacher);
		this.checkConcurrency(teacher);
		this.checkAuthenticate();
		return this.teacherRepository.save(teacher);
	}
	
	public Teacher saveForFolders(final Teacher teacher) {
		//this.checkPrincipal(teacher);
		this.checkConcurrency(teacher);
		this.checkAuthenticate();
		return this.teacherRepository.save(teacher);
	}

	public Teacher saveEdit(final Teacher teacher) {
		this.checkPrincipal(teacher);
		this.checkConcurrency(teacher);
		//this.checkAuthenticate();
		return this.teacherRepository.save(teacher);
	}

	public Teacher saveForParentsGroup(final Teacher teacher) {
		//this.checkPrincipal(teacher);
		this.checkConcurrency(teacher);
		//this.checkAuthenticate();
		return this.teacherRepository.save(teacher);
	}

	public Teacher findOne(final int teacherId) {
		return this.teacherRepository.findOne(teacherId);
	}

	public Collection<Teacher> findAll() {
		return this.teacherRepository.findAll();
	}

	private void checkAuthenticate() {
		Assert.isTrue(!this.actorService.checkAuthenticate());
	}

	public Teacher findByPrincipal() {
		Teacher result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Teacher findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Teacher result;

		result = this.teacherRepository.findByUserAccount(userAccount.getId());

		return result;
	}

	public Teacher findByUserName(final String userName) {
		return this.teacherRepository.findByUserName(userName);
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal(final Teacher teacher) {
		if (teacher.getId() != 0) {
			final Teacher t = this.findByPrincipal();
			Assert.isTrue(teacher.equals(t));
		}
	}
	private void checkConcurrency(final Teacher teacher) {
		if (teacher.getId() != 0) {
			final Teacher t = this.findOne(teacher.getId());
			Assert.isTrue(teacher.getVersion() == t.getVersion());
		}
	}

	//Form de Agent (Se utiliza para la creacion de un nuevo profesor)
	public Teacher reconstruct(final CreateActorForm createActorForm, final BindingResult binding) {

		final Teacher result = createActorForm.getTeacher();
		final UserAccount userAccount = createActorForm.getUserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.TEACHER);
		userAccount.getAuthorities().add(authority);
		this.validator.validate(userAccount, binding);

		final Md5PasswordEncoder encode = new Md5PasswordEncoder();
		final String pwdHash = encode.encodePassword(userAccount.getPassword(), null);
		userAccount.setPassword(pwdHash);
		result.setUserAccount(userAccount);
		result.setFolders(new ArrayList<Folder>());
		result.setMessagesReceived(new ArrayList<PrivateMessage>());
		result.setMessagesSent(new ArrayList<PrivateMessage>());
		result.setMarks(new ArrayList<Mark>());
		result.setEntries(new ArrayList<Entry>());
		result.setParentsGroups(new ArrayList<ParentsGroup>());
		result.setClassGroups(new ArrayList<ClassGroup>());

		if (binding != null)
			this.validator.validate(result, binding);

		return result;

	}

	// ---- Reconstruct de ActorForm para editar profesores -------
	public Teacher reconstructTeacher(final ActorForm actorForm, final BindingResult binding) {
		final Teacher teacherBBDD = this.findOne(actorForm.getId());
		final Teacher result = actorForm.getTeacher();

		result.setUserAccount(teacherBBDD.getUserAccount());
		result.setFolders(teacherBBDD.getFolders());
		result.setMessagesReceived(teacherBBDD.getMessagesReceived());
		result.setMessagesSent(teacherBBDD.getMessagesSent());
		result.setMarks(teacherBBDD.getMarks());
		result.setEntries(teacherBBDD.getEntries());
		result.setParentsGroups(teacherBBDD.getParentsGroups());
		result.setClassGroups(teacherBBDD.getClassGroups());

		this.validator.validate(result, binding);

		return result;
	}

	public Double getAverageTeachersPerSchool() {
		return this.teacherRepository.averageTeachersPerSchool();
	}
	public Double getStandardDeviationTeachersPerSchool() {
		return this.teacherRepository.standardDeviationTeachersPerSchool();
	}

	public void flush() {
		this.teacherRepository.flush();
	}
}
