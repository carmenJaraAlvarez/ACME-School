
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ParentsGroupRepository;
import domain.Event;
import domain.ExtracurricularActivity;
import domain.Homework;
import domain.Message;
import domain.Parent;
import domain.ParentsGroup;
import domain.Subject;
import domain.Teacher;
import forms.ParentsGroupEditRawForm;

@Service
@Transactional
public class ParentsGroupService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ParentsGroupRepository	parentsGroupRepository;
	// -----------------------------
	@Autowired
	private ParentService			parentService;

	@Autowired
	private Validator				validator;

	@Autowired
	private TeacherService			teacherService;


	// Constructors -----------------------------------------------------------

	public ParentsGroupService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public ParentsGroup findOne(final int parentsGroupId) {
		return this.parentsGroupRepository.findOne(parentsGroupId);
	}

	public Collection<ParentsGroup> findAll() {
		return this.parentsGroupRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public ParentsGroup create() {
		ParentsGroup res;
		res = new ParentsGroup();
		final Parent parent = this.parentService.findByPrincipal();
		final Collection<Event> events = new ArrayList<Event>();
		final Collection<ExtracurricularActivity> extracurricularActivities = new ArrayList<ExtracurricularActivity>();
		final Collection<Parent> groupAdmins = new ArrayList<Parent>();
		final Collection<Parent> members = new ArrayList<Parent>();
		members.add(parent);
		final Collection<Message> messages = new ArrayList<Message>();
		final Collection<Teacher> teachers = new ArrayList<>();
		final Collection<Homework> homeworks = new ArrayList<>();

		final String code = this.createCode();

		res.setCreator(parent);
		res.setEvents(events);
		res.setExtracurricularActivities(extracurricularActivities);
		res.setGroupAdmins(groupAdmins);
		res.setMembers(members);
		res.setMessages(messages);
		res.setCode(code);
		res.setHomeworks(homeworks);
		res.setTeachers(teachers);

		return res;
	}

	private String generator() {
		String code = "";
		final String minusculas = "abcdefghijklmnopqrstuvwxyz";
		final String mayusculas = "ABCDEFGHYJKLMNOPQRSTUVWXYZ";
		final String numeros = "0123456789";

		final String todas = minusculas + mayusculas;

		String cadenaAleatoria = RandomStringUtils.random(5, numeros);
		code = code + cadenaAleatoria;
		cadenaAleatoria = RandomStringUtils.random(2, todas);
		code = code + cadenaAleatoria;

		return code;
	}

	private String createCode() {
		final String code = this.generator();
		boolean unico = true;
		final Collection<ParentsGroup> all = this.parentsGroupRepository.findAll();
		for (final ParentsGroup pg : all)
			if (pg.getCode() == code) {
				unico = false;
				break;
			}
		if (unico)
			return code;
		else
			return this.createCode();
	}

	public ParentsGroup reconstruct(final ParentsGroup parentsgroup, final BindingResult binding) {

		ParentsGroup result;
		if (parentsgroup.getId() == 0)
			result = this.create();
		else {

			result = this.parentsGroupRepository.findOne(parentsgroup.getId());
			this.checkParent(result);
		}
		result.setDescription(parentsgroup.getDescription());
		result.setImage(parentsgroup.getImage());
		result.setName(parentsgroup.getName());
		result.setClassGroup(parentsgroup.getClassGroup());
		this.validator.validate(result, binding);
		return result;

	}
	public ParentsGroup reconstructEdit(final ParentsGroupEditRawForm parentsGroupEditRawForm, final BindingResult binding) {

		ParentsGroup result;

		result = this.parentsGroupRepository.findOne(parentsGroupEditRawForm.getId());
		this.checkParent(result);

		result.setDescription(parentsGroupEditRawForm.getDescription());
		if (parentsGroupEditRawForm.getUrl() != null)
			result.setImage(parentsGroupEditRawForm.getUrl());
		result.setName(parentsGroupEditRawForm.getName());
		//result.setClassGroup(parentsgroup.getClassGroup());

		return result;

	}
	private void checkParent(final ParentsGroup parentsgroup) {
		final Parent parent = this.parentService.findByPrincipal();
		Assert.isTrue(parentsgroup.getMembers().contains(parent) || parentsgroup.getGroupAdmins().contains(parent) || parentsgroup.getCreator().equals(parent), "Not authorized");
	}

	public void save(final ParentsGroup parentsgroup) {
		this.parentsGroupRepository.save(parentsgroup);

	}

	public ParentsGroup findOneToEdit(final int parentsGroupId) {
		ParentsGroup parentsgroup;
		parentsgroup = this.parentsGroupRepository.findOne(parentsGroupId);
		this.checkParent(parentsgroup);
		return parentsgroup;
	}

	public ParentsGroup findOneToDisplay(final int parentsGroupId) {
		ParentsGroup parentsgroup;
		parentsgroup = this.parentsGroupRepository.findOne(parentsGroupId);
		return parentsgroup;
	}

	public boolean checkParentInGroup(final ParentsGroup parentsGroup) {
		final Parent parent = this.parentService.findByPrincipal();
		final boolean result = parentsGroup.getMembers().contains(parent) || parentsGroup.getGroupAdmins().contains(parent) || parentsGroup.getCreator().equals(parent);
		return result;
	}

	public Collection<Message> findGroupMessages(final int parentsGroupId, final Integer messagesNumber) {
		Collection<Message> result;

		final ArrayList<Message> messages = new ArrayList<>(this.parentsGroupRepository.findGroupMessages(parentsGroupId));
		if (messagesNumber < messages.size()) {
			Collections.reverse(messages);
			final ArrayList<Message> selected = new ArrayList<Message>();
			Integer index = 0;
			for (final Message m : messages)
				if (messagesNumber > index) {
					selected.add(m);
					index++;
				} else
					break;
			Collections.reverse(selected);
			result = selected;
		} else
			result = messages;
		return result;
	}

	// Gestion de los miembros de un grupo
	public void addAdmin(final Parent parent, final ParentsGroup parentsGroup) {
		parentsGroup.getGroupAdmins().add(parent);
		parent.getParentsGroupsManaged().add(parentsGroup);
		this.parentsGroupRepository.save(parentsGroup);
		this.parentService.saveForParentsGroup(parent);
	}

	public void removeMemberOfGroup(final Parent parent, final ParentsGroup parentsGroup) {
		parentsGroup.getMembers().remove(parent);
		parent.getParentsGroupsMemberOf().remove(parentsGroup);
		this.parentsGroupRepository.save(parentsGroup);
		this.parentService.saveForParentsGroup(parent);
	}

	public void removeAdminOfGroup(final Parent parent, final ParentsGroup parentsGroup) {
		parentsGroup.getGroupAdmins().remove(parent);
		parent.getParentsGroupsManaged().remove(parentsGroup);
		this.parentsGroupRepository.save(parentsGroup);
		this.parentService.saveForParentsGroup(parent);
	}

	public void addMember(final Parent parent, final ParentsGroup parentsGroup) {
		Assert.isTrue(this.checkNoMember(parent, parentsGroup));
		parentsGroup.getMembers().add(parent);
		parent.getParentsGroupsMemberOf().add(parentsGroup);
		this.parentsGroupRepository.save(parentsGroup);
		this.parentService.saveForParentsGroup(parent);

	}

	public void addTeacher(final Teacher teacher, final ParentsGroup parentsGroup) {
		Assert.isTrue(this.checkNoMemberTeacher(teacher, parentsGroup));
		parentsGroup.getTeachers().add(teacher);
		teacher.getParentsGroups().add(parentsGroup);
		this.parentsGroupRepository.save(parentsGroup);
		this.teacherService.saveForParentsGroup(teacher);

	}

	public void removeTeacherOfGroup(final Teacher teacher, final ParentsGroup parentsGroup) {
		parentsGroup.getTeachers().remove(teacher);
		teacher.getParentsGroups().remove(parentsGroup);
		this.parentsGroupRepository.save(parentsGroup);
		this.teacherService.saveForParentsGroup(teacher);
	}

	public Collection<ParentsGroup> findByKeyWord(final String keyWord) {
		final Collection<ParentsGroup> parentsGroups = this.parentsGroupRepository.findByKeyWord(keyWord);
		return parentsGroups;
	}

	public Collection<Subject> getSubjects(final ParentsGroup pg) {
		final Collection<Subject> res = new ArrayList<Subject>(pg.getClassGroup().getLevel().getSubjects());
		return res;
	}

	public Boolean checkNoMember(final Parent parent, final ParentsGroup parentsGroup) {
		Boolean result = true;
		if (parentsGroup.getCreator().equals(parent) || parentsGroup.getGroupAdmins().contains(parent) || parentsGroup.getMembers().contains(parent))
			result = false;
		return result;
	}

	public Boolean checkNoMemberTeacher(final Teacher teacher, final ParentsGroup parentsGroup) {
		Boolean result = true;
		if (parentsGroup.getTeachers().contains(teacher))
			result = false;
		return result;
	}

	public void checkMember(final ParentsGroup parentsGroup) {
		final Collection<Parent> parentsAdmin = parentsGroup.getGroupAdmins();
		final Collection<Parent> parentsMember = parentsGroup.getMembers();
		final Parent parent = this.parentService.findByPrincipal();
		final Collection<Parent> allMembers = new ArrayList<>();
		allMembers.addAll(parentsMember);
		allMembers.addAll(parentsAdmin);
		allMembers.add(parentsGroup.getCreator());
		Assert.isTrue(allMembers.contains(parent));
	}

	public Collection<ParentsGroup> getGroupMostParents() {
		return this.parentsGroupRepository.getGroupWithMostTutors();
	}
	public void flush() {
		this.parentsGroupRepository.flush();
	}
	public boolean checkConcurrency(ParentsGroup parentsGroup) {
		boolean res = false;//true if there is concurrency
		if (parentsGroup.getId() != 0) {
			ParentsGroup BBDD = this.parentsGroupRepository.findOne(parentsGroup.getId());
			if (BBDD.getVersion() != parentsGroup.getVersion()) {
				res = true;
			}
		}
		return res;
	}

	public boolean checkConcurrency(ParentsGroupEditRawForm parentsGroupEditRawForm) {
		boolean res = false;//true if there is concurrency
		ParentsGroup BBDD = this.parentsGroupRepository.findOne(parentsGroupEditRawForm.getId());
		if (BBDD.getVersion() != parentsGroupEditRawForm.getVersion()) {
			res = true;
		}
		return res;
	}

}
