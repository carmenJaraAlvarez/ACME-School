
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.LevelRepository;
import domain.Level;

@Service
@Transactional
public class LevelService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private LevelRepository	levelRepository;


	// Constructors -----------------------------------------------------------

	public LevelService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Level findOne(final int levelId) {
		return this.levelRepository.findOne(levelId);
	}

	// Other business methods -------------------------------------------------

}
