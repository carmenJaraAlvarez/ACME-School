
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

import repositories.AgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Advertisement;
import domain.Agent;
import domain.Folder;
import domain.PrivateMessage;
import forms.ActorForm;
import forms.CreateActorForm;

@Service
@Transactional
public class AgentService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AgentRepository	agentRepository;

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService	actorService;
	@Autowired
	private Validator		validator;


	// Constructors -----------------------------------------------------------

	public AgentService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Agent save(final Agent agent) {
		this.checkAuthenticate();
		this.checkPrincipal(agent);
		this.checkConcurrency(agent);
		final Agent result = this.agentRepository.save(agent);

		return this.agentRepository.save(result);
	}
	
	public Agent saveForFolders(final Agent agent) {
		//this.checkAuthenticate();
		//this.checkPrincipal(agent);
		this.checkConcurrency(agent);
		final Agent result = this.agentRepository.save(agent);

		return result;
	}

	public Agent saveEdit(final Agent agent) {
		this.checkPrincipal(agent);
		this.checkConcurrency(agent);
		final Agent result = this.agentRepository.save(agent);

		return result;
	}

	public Agent findOne(final int agentId) {
		return this.agentRepository.findOne(agentId);
	}

	public Collection<Agent> findAll() {
		return this.agentRepository.findAll();
	}

	public Agent findByPrincipal() {
		Agent result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	public Agent findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		return this.agentRepository.findByUserAccount(userAccount.getId());
	}

	// Other business methods -------------------------------------------------

	public void checkPrincipal(final Agent agent) {
		if (agent.getId() != 0) {
			final Agent c = this.findByPrincipal();
			Assert.isTrue(agent.equals(c));
		}
	}
	private void checkConcurrency(final Agent agent) {
		if (agent.getId() != 0) {
			final Agent c = this.findOne(agent.getId());
			Assert.isTrue(agent.getVersion() == c.getVersion());
		}
	}

	private void checkAuthenticate() {
		Assert.isTrue(!this.actorService.checkAuthenticate());
	}

	//Form de Agent (Se utiliza para la creacion de un nuevo agent)
	public Agent reconstruct(final CreateActorForm createActorForm, final BindingResult binding) {

		final Agent result = createActorForm.getAgent();
		final UserAccount userAccount = createActorForm.getUserAccount();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.AGENT);
		userAccount.getAuthorities().add(authority);
		this.validator.validate(userAccount, binding);

		final Md5PasswordEncoder encode = new Md5PasswordEncoder();
		final String pwdHash = encode.encodePassword(userAccount.getPassword(), null);
		userAccount.setPassword(pwdHash);
		result.setUserAccount(userAccount);
		result.setFolders(new ArrayList<Folder>());
		result.setMessagesReceived(new ArrayList<PrivateMessage>());
		result.setMessagesSent(new ArrayList<PrivateMessage>());
		result.setAdvertisements(new ArrayList<Advertisement>());

		if (binding != null)
			this.validator.validate(result, binding);

		return result;

	}

	// ---- Reconstruct de ActorForm para editar agents -------
	public Agent reconstructAgent(final ActorForm actorForm, final BindingResult binding) {
		final Agent agentBBDD = this.findOne(actorForm.getId());
		final Agent result = actorForm.getAgent();

		result.setUserAccount(agentBBDD.getUserAccount());
		result.setFolders(agentBBDD.getFolders());
		result.setMessagesReceived(agentBBDD.getMessagesReceived());
		result.setMessagesSent(agentBBDD.getMessagesSent());
		result.setAdvertisements(agentBBDD.getAdvertisements());

		this.validator.validate(result, binding);

		return result;
	}

	public void flush() {
		this.agentRepository.flush();
	}

}
