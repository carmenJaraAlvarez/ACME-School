
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import domain.ClassGroup;

import repositories.ClassGroupRepository;

@Service
@Transactional
public class ClassGroupService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClassGroupRepository	classGroupRepository;


	// Constructors -----------------------------------------------------------

	public ClassGroupService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------
	
	public Collection<ClassGroup> findAll(){
		return this.classGroupRepository.findAll();
	}
	
	public ClassGroup findOne(int classGroupId){
		return this.classGroupRepository.findOne(classGroupId);
	}

	// Other business methods -------------------------------------------------

}
