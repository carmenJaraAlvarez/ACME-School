
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import forms.ActorForm;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;
	@Autowired
	private Validator				validator;


	// Services ---------------------------------------------------------------

	// Constructors -----------------------------------------------------------

	public AdministratorService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Administrator findOne(final int adminId) {
		return this.administratorRepository.findOne(adminId);
	}

	public Administrator saveEdit(final Administrator admin) {
		this.checkPrincipal(admin);
		this.checkConcurrency(admin);
		final Administrator result = this.administratorRepository.save(admin);

		return result;
	}

	// Other business methods -------------------------------------------------

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator result;
		result = this.administratorRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	private void checkPrincipal(final Administrator admin) {
		if (admin.getId() != 0) {
			final Administrator a = this.findByPrincipal();
			Assert.isTrue(admin.equals(a));
		}
	}
	private void checkConcurrency(final Administrator admin) {
		if (admin.getId() != 0) {
			final Administrator a = this.findOne(admin.getId());
			Assert.isTrue(admin.getVersion() == a.getVersion());
		}
	}

	// ---- Reconstruct de ActorForm para editar customers -------
	public Administrator reconstructAdmin(final ActorForm actorForm, final BindingResult binding) {
		final Administrator adminBBDD = this.findOne(actorForm.getId());
		final Administrator result = actorForm.getAdministrator();
		result.setUserAccount(adminBBDD.getUserAccount());
		result.setFolders(adminBBDD.getFolders());
		result.setMessagesReceived(adminBBDD.getMessagesReceived());
		result.setMessagesSent(adminBBDD.getMessagesSent());
		this.validator.validate(result, binding);

		return result;

	}
	public boolean checkIsAdmin() {

		final boolean res = true;
		try {
			this.findByPrincipal();
		} catch (final Throwable oops) {
			return false;
		}
		return res;
	}

}
