
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

import repositories.StudentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.ChatRoom;
import domain.Folder;
import domain.Mark;
import domain.PrivateMessage;
import domain.Student;
import forms.ActorForm;
import forms.CreateActorForm;

@Service
@Transactional
public class StudentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private StudentRepository	studentRepository;
	@Autowired
	private ParentService		parentService;
	@Autowired
	private Validator			validator;


	// Constructors -----------------------------------------------------------

	public StudentService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Student save(final Student student) {
		this.checkPrincipal(student);
		this.checkConcurrency(student);
		this.checkParent();

		return this.studentRepository.save(student);
	}

	public Student findOne(final int studentId) {
		return this.studentRepository.findOne(studentId);
	}

	public Collection<Student> findAll() {
		return this.studentRepository.findAll();
	}

	public Student findByPrincipal() {
		Student result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Student findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Student result;

		result = this.studentRepository.findByUserAccount(userAccount.getId());

		return result;
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal(final Student student) {
		if (student.getId() != 0) {
			final Student s = this.findByPrincipal();
			Assert.isTrue(student.equals(s));
		}
	}
	private void checkConcurrency(final Student student) {
		if (student.getId() != 0) {
			final Student s = this.findOne(student.getId());
			Assert.isTrue(student.getVersion() == s.getVersion());
		}
	}

	private void checkParent() {
		Assert.notNull(this.parentService.findByPrincipal());
	}

	//Form de Agent (Se utiliza para la creacion de un nuevo agent)
	public Student reconstruct(final CreateActorForm createActorForm, final BindingResult binding) {

		final Student result = createActorForm.getStudent();
		final UserAccount userAccount = createActorForm.getUserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.STUDENT);
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
		result.setChatRooms(new ArrayList<ChatRoom>());
		result.setParent(this.parentService.findByPrincipal());

		this.validator.validate(result, binding);
		

		return result;

	}

	// ---- Reconstruct de ActorForm para editar agents -------
	public Student reconstructStudent(final ActorForm actorForm, final BindingResult binding) {
		final Student studentBBDD = this.findOne(actorForm.getId());
		final Student result = actorForm.getStudent();
		result.setUserAccount(studentBBDD.getUserAccount());
		result.setFolders(studentBBDD.getFolders());
		result.setMessagesReceived(studentBBDD.getMessagesReceived());
		result.setMessagesSent(studentBBDD.getMessagesSent());
		result.setMarks(studentBBDD.getMarks());
		result.setChatRooms(studentBBDD.getChatRooms());
		result.setParent(studentBBDD.getParent());
		this.validator.validate(result, binding);

		return result;
	}

}
