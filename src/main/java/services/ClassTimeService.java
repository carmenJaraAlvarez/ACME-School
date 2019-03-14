
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ClassTimeRepository;
import domain.ClassGroup;
import domain.ClassTime;
import domain.Parent;
import domain.ParentsGroup;
import forms.ClassTimeForm;

@Service
@Transactional
public class ClassTimeService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ClassTimeRepository	classTimeRepository;
	@Autowired
	private ClassGroupService	classGroupService;
	@Autowired
	private Validator			validator;
	@Autowired
	private ParentService		parentservice;


	// Constructors -----------------------------------------------------------

	public ClassTimeService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public Collection<ClassTime> findByClassGroup(Integer classGroupId, ParentsGroup parentsGroup) {
		Collection<ClassTime> res;
		checkIsInParentGroup(classGroupId, parentsGroup);
		ClassGroup classGroup = this.classGroupService.findOneToEdit(classGroupId);
		Integer classId = classGroup.getId();
		res = this.classTimeRepository.findByClass(classId);
		return res;
	}

	private void checkIsInParentGroup(Integer classGroupId, ParentsGroup parentsGroup) {
		Assert.isTrue(parentsGroup.getClassGroup().getId() == classGroupId);
	}
	public ClassTime create(Integer classGroupId) {
		ClassTime res;
		ClassGroup classGroup = this.classGroupService.findOneToEdit(classGroupId);
		res = new ClassTime();
		res.setClassGroup(classGroup);
		return res;
	}

	public ClassTime findOneToEdit(Integer classTimeId) {
		ClassTime res;
		res = this.findOne(classTimeId);
		Parent logged = this.parentservice.findByPrincipal();
		Collection<ParentsGroup> pgs = this.parentservice.getAllGroups(logged);
		Collection<ClassGroup> cgs = new ArrayList<>();
		for (ParentsGroup pg : pgs) {
			cgs.add(pg.getClassGroup());
		}
		Assert.isTrue(cgs.contains(res.getClassGroup()));
		return res;
	}

	private ClassTime findOne(Integer classTimeId) {
		return this.classTimeRepository.findOne(classTimeId);
	}

	public ClassTime save(ClassTime classTime) {

		return this.classTimeRepository.save(classTime);
	}

	public Collection<ClassTime> findMon(Integer classGroupId) {
		Collection<ClassTime> res;
		res = this.classTimeRepository.findMon(classGroupId);
		return res;
	}
	public Collection<ClassTime> findTue(Integer classGroupId) {
		Collection<ClassTime> res;
		res = this.classTimeRepository.findTue(classGroupId);
		return res;
	}
	public Collection<ClassTime> findWed(Integer classGroupId) {
		Collection<ClassTime> res;
		res = this.classTimeRepository.findWed(classGroupId);
		return res;
	}
	public Collection<ClassTime> findThu(Integer classGroupId) {
		Collection<ClassTime> res;
		res = this.classTimeRepository.findThu(classGroupId);
		return res;
	}
	public Collection<ClassTime> findFri(Integer classGroupId) {
		Collection<ClassTime> res;
		res = this.classTimeRepository.findFri(classGroupId);
		return res;
	}
	// Other business methods -------------------------------------------------

	public void delete(ClassTime classTime) {
		Parent logged = this.parentservice.findByPrincipal();
		Collection<ParentsGroup> pgs = this.parentservice.getAllGroups(logged);
		Collection<ClassGroup> cgs = new ArrayList<>();
		for (ParentsGroup pg : pgs) {
			cgs.add(pg.getClassGroup());
		}
		Assert.isTrue(cgs.contains(classTime.getClassGroup()));
		this.classTimeRepository.delete(classTime);

	}
	public boolean controlHours(String s) {
		//false if is not correct
		boolean res = true;
		String[] parts = s.split("-");
		String part1 = parts[0];
		String part2 = parts[1];

		String[] parts2 = part1.split(":");
		String part11 = parts2[0];
		String part12 = parts2[1];

		String[] parts3 = part2.split(":");
		String part21 = parts3[0];
		String part22 = parts3[1];

		Integer num1 = Integer.valueOf(part11);
		Integer num2 = Integer.valueOf(part12);
		Integer num3 = Integer.valueOf(part21);
		Integer num4 = Integer.valueOf(part22);

		if (num1 > num3 || (num1 == num3 && num2 > num4) || num1 >= 24 || num3 >= 24) {
			res = false;
		}
		return res;
	}

	public boolean IsOverlapped(String s, int classGroupId, String day, String subject) {
		boolean res = false;
		Collection<ClassTime> times = this.classTimeRepository.findBySubject(classGroupId, day, subject);
		if (times.size() > 0) {

			//new 		
			String[] parts = s.split("-");
			String part1 = parts[0];
			String part2 = parts[1];

			String[] parts2 = part1.split(":");
			String part11 = parts2[0];
			String part12 = parts2[1];

			String[] parts3 = part2.split(":");
			String part21 = parts3[0];
			String part22 = parts3[1];

			Integer num1 = Integer.valueOf(part11);
			Integer num2 = Integer.valueOf(part12);
			Integer num3 = Integer.valueOf(part21);
			Integer num4 = Integer.valueOf(part22);

			// to a single number for comfort
			Integer inicio = num1 * 10 + num2;
			Integer fin = num3 * 10 + num4;

			for (ClassTime c : times) {
				String BBDD = c.getHour();
				String[] partsBBDD = BBDD.split("-");
				String part1BBDD = partsBBDD[0];
				String part2BBDD = partsBBDD[1];

				String[] parts2BBDD = part1BBDD.split(":");
				String part11BBDD = parts2BBDD[0];
				String part12BBDD = parts2BBDD[1];

				String[] parts3BBDD = part2BBDD.split(":");
				String part21BBDD = parts3BBDD[0];
				String part22BBDD = parts3BBDD[1];

				Integer num1BBDD = Integer.valueOf(part11BBDD);
				Integer num2BBDD = Integer.valueOf(part12BBDD);
				Integer num3BBDD = Integer.valueOf(part21BBDD);
				Integer num4BBDD = Integer.valueOf(part22BBDD);
				// to a single number for comfort
				Integer inicioBBDD = num1BBDD * 10 + num2BBDD;
				Integer finBBDD = num3BBDD * 10 + num4BBDD;

				if (inicio == inicioBBDD || (inicio > inicioBBDD && inicio < finBBDD) || fin == finBBDD || (fin < finBBDD && fin > inicioBBDD)) {
					res = true;
					break;
				}
			}
		}
		return res;
	}
	public ClassTime reconstruct(ClassTimeForm classTimeForm, final BindingResult binding) {
		ClassTime res = null;
		res = this.create(classTimeForm.getClassGroup().getId());

		if (classTimeForm.getId() != 0) {
			ClassTime BBDD = this.findOne(classTimeForm.getId());
			res.setId(BBDD.getId());
			res.setVersion(BBDD.getVersion());
		}
		res.setDay(classTimeForm.getDay());
		res.setHour(classTimeForm.getHour());
		res.setSubject(classTimeForm.getSubject());
		this.validator.validate(res, binding);
		return res;
	}

}
