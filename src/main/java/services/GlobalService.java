
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.GlobalRepository;
import domain.Global;

@Service
@Transactional
public class GlobalService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private GlobalRepository		globalRepository;

	@Autowired
	private AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public GlobalService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public Collection<Global> findAll() {
		Collection<Global> result;

		result = this.globalRepository.findAll();

		return result;
	}

	// Other business methods ---------------------------------------------------
	public Global getGlobal() {
		return (Global) this.findAll().toArray()[0];
	}

	public Global findOne(final int globalId) {
		this.checkAdmin();
		return this.globalRepository.findOne(globalId);
	}

	private void checkAdmin() {
		Assert.isTrue(this.administratorService.findByPrincipal() != null);
	}

	public Global save(final Global global) {
		Assert.notNull(global);
		this.checkAdmin();

		if (global.getId() != 0)
			Assert.isTrue(this.findOne(global.getId()).getVersion() == (global.getVersion()), "No se puede modificar porque la versión ha cambiado");

		final Global result = this.globalRepository.save(global);
		return result;
	}

	public void flush() {
		this.globalRepository.flush();
	}
}
