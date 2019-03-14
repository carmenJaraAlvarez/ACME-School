
package services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.LevelRepository;
import domain.ClassGroup;
import domain.Level;
import domain.School;
import domain.Subject;

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

	public Level create(final School school) {
		final Level res = new Level();
		res.setClassGroups(new ArrayList<ClassGroup>());
		res.setSubjects(new ArrayList<Subject>());
		res.setSchool(school);
		return res;
	}

	public Level save(final Level level) {
		return this.levelRepository.save(level);
	}

	public Level findOne(final int levelId) {
		return this.levelRepository.findOne(levelId);
	}

	// Other business methods -------------------------------------------------

}
