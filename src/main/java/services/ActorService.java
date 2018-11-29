
package services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import forms.CreateActorForm;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ActorRepository	actorRepository;


	// Constructors -----------------------------------------------------------

	public ActorService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Actor findOne(final int actorId) {
		return this.actorRepository.findOne(actorId);
	}

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public boolean checkAuthenticate() {
		final boolean res = true;
		try {
			this.findByPrincipal();
		} catch (final Throwable oops) {
			return false;
		}
		return res;
	}

	public boolean checkPassword(final CreateActorForm createActorForm) {
		boolean result = false;
		if (createActorForm.getPassword().equals(createActorForm.getPassword2()))
			result = true;
		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	public Actor findAdmin() {
		final List<Actor> actores = this.actorRepository.findAll();
		Actor res = null;
		final Authority compara = new Authority();
		compara.setAuthority(Authority.ADMIN);
		for (final Actor a : actores)
			if (a.getUserAccount().getAuthorities().contains(compara)) {
				res = a;
				break;
			}
		return res;
	}

	public Actor save(final Actor a) {
		final Actor actor = this.actorRepository.save(a);
		return actor;
	}

}
