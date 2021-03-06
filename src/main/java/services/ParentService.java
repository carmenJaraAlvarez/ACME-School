
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Folder;
import domain.Parent;
import domain.ParentsGroup;
import domain.PrivateMessage;
import domain.Student;
import forms.ActorForm;
import forms.CreateActorForm;

@Service
@Transactional
public class ParentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ParentRepository	parentRepository;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private Validator			validator;
	@Autowired
	private FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public ParentService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Parent save(final Parent parent) {
		this.checkAuthenticate();
		this.checkPrincipal(parent);
		this.checkConcurrency(parent);

		return this.parentRepository.save(parent);
	}

	public Parent saveForFolders(final Parent parent) {
		this.checkAuthenticate();
		//this.checkPrincipal(parent);
		this.checkConcurrency(parent);

		return this.parentRepository.save(parent);
	}

	public Parent saveEdit(final Parent parent) {
		this.checkPrincipal(parent);
		this.checkConcurrency(parent);

		return this.parentRepository.save(parent);
	}

	public Parent saveForParentsGroup(final Parent parent) {

		//this.checkAuthenticate();

		//this.checkPrincipal(parent);

		this.checkConcurrency(parent);

		return this.parentRepository.save(parent);

	}

	public Parent findOne(final int parentId) {
		return this.parentRepository.findOne(parentId);
	}

	public Collection<Parent> findAll() {
		return this.parentRepository.findAll();
	}

	public Parent findByPrincipal() {
		Parent result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Parent findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Parent result;

		result = this.parentRepository.findByUserAccount(userAccount.getId());

		return result;
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal(final Parent parent) {
		if (parent.getId() != 0) {
			final Parent p = this.findByPrincipal();
			Assert.isTrue(parent.equals(p));
		}
	}
	private void checkConcurrency(final Parent parent) {
		if (parent.getId() != 0) {
			final Parent p = this.findOne(parent.getId());
			Assert.isTrue(parent.getVersion() == p.getVersion(), "Concurrencia");
		}
	}

	private void checkAuthenticate() {
		Assert.isTrue(!this.actorService.checkAuthenticate());
	}

	//Form de Agent (Se utiliza para la creacion de un nuevo padre)
	public Parent reconstruct(final CreateActorForm createActorForm, final BindingResult binding) {

		final Parent result = createActorForm.getParent();
		final UserAccount userAccount = createActorForm.getUserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.PARENT);
		userAccount.getAuthorities().add(authority);
		this.validator.validate(userAccount, binding);

		final Md5PasswordEncoder encode = new Md5PasswordEncoder();
		final String pwdHash = encode.encodePassword(userAccount.getPassword(), null);
		userAccount.setPassword(pwdHash);
		result.setUserAccount(userAccount);
		result.setFolders(new ArrayList<Folder>());
		result.setMessagesReceived(new ArrayList<PrivateMessage>());
		result.setMessagesSent(new ArrayList<PrivateMessage>());
		result.setStudents(new ArrayList<Student>());
		result.setParentsGroupsCreated(new ArrayList<ParentsGroup>());
		result.setParentsGroupsManaged(new ArrayList<ParentsGroup>());
		result.setParentsGroupsMemberOf(new ArrayList<ParentsGroup>());

		if (binding != null)
			this.validator.validate(result, binding);

		return result;

	}

	// ---- Reconstruct de ActorForm para editar tutores -------
	public Parent reconstructParent(final ActorForm actorForm, final BindingResult binding) {
		final Parent parentBBDD = this.findOne(actorForm.getId());
		final Parent result = actorForm.getParent();

		result.setUserAccount(parentBBDD.getUserAccount());
		result.setFolders(parentBBDD.getFolders());
		result.setMessagesReceived(parentBBDD.getMessagesReceived());
		result.setMessagesSent(parentBBDD.getMessagesSent());
		result.setStudents(parentBBDD.getStudents());
		result.setParentsGroupsCreated(parentBBDD.getParentsGroupsCreated());
		result.setParentsGroupsManaged(parentBBDD.getParentsGroupsManaged());
		result.setParentsGroupsMemberOf(parentBBDD.getParentsGroupsMemberOf());

		if (binding != null)
			this.validator.validate(result, binding);

		return result;
	}

	public Collection<ParentsGroup> getAllGroups(final Parent parent) {
		final Collection<ParentsGroup> all = new HashSet<ParentsGroup>();
		all.addAll(parent.getParentsGroupsCreated());
		all.addAll(parent.getParentsGroupsManaged());
		all.addAll(parent.getParentsGroupsMemberOf());
		return all;
	}
	public boolean checkIsParent() {
		final boolean res = true;
		try {
			this.findByPrincipal();
		} catch (final Throwable oops) {
			return false;
		}
		return res;
	}

	public Double getAverageParentPerGroup() {
		return this.parentRepository.averageTutorsPerGroup();
	}
	public Double getStandardDeviationParentPerGroup() {
		return this.parentRepository.standardDeviationTutorsPerGroup();
	}
	public Double getMinParentPerGroup() {
		return this.parentRepository.minTutorsPerGroup();
	}
	public Double getMaxParentPerGroup() {
		return this.parentRepository.maxTutorsPerGroup();
	}
	public Collection<Parent> getParentsMessageLastWeek() {
		return this.parentRepository.getParentsWithMessagesLastWeek();
	}
	public Collection<Parent> getParentsMessageLast2Weeks() {
		return this.parentRepository.getParentsWithMessagesLast2Week();
	}

	public void flush() {
		this.parentRepository.flush();
	}
}
