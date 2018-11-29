
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ParentsGroupRepository;
import domain.Event;
import domain.ExtracurricularActivity;
import domain.Message;
import domain.Parent;
import domain.ParentsGroup;

@Service
@Transactional
public class ParentsGroupService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ParentsGroupRepository	parentsGroupRepository;
	//-----------------------------
	@Autowired
	private ParentService			parentService;


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

	public ParentsGroup findOneToEdit(final int parentsGroupId) {

		ParentsGroup result = this.parentsGroupRepository.findOne(parentsGroupId);

		Assert.isTrue(this.parentService.findByPrincipal().equals(result.getCreator()), "Para que solo pueda editarla el creador");

		return result;

	}

	public ParentsGroup create() {
		ParentsGroup res;
		res = new ParentsGroup();
		Parent parent = this.parentService.findByPrincipal();
		Collection<Event> events = new ArrayList<Event>();
		Collection<ExtracurricularActivity> extracurricularActivities = new ArrayList<ExtracurricularActivity>();
		Collection<Parent> groupAdmins = new ArrayList<Parent>();
		Collection<Parent> members = new ArrayList<Parent>();
		members.add(parent);
		Collection<Message> messages = new ArrayList<Message>();

		String code = this.createCode();

		res.setCreator(parent);
		res.setEvents(events);
		res.setExtracurricularActivities(extracurricularActivities);
		res.setGroupAdmins(groupAdmins);
		res.setMembers(members);
		res.setMessages(messages);
		res.setCode(code);

		return res;
	}

	private String generator() {
		String code = "";
		String minusculas = "abcdefghijklmnopqrstuvwxyz";
		String mayusculas = "ABCDEFGHYJKLMNOPQRSTUVWXYZ";
		String numeros = "0123456789";

		String todas = minusculas + mayusculas;

		String cadenaAleatoria = RandomStringUtils.random(5, numeros);
		code = code + cadenaAleatoria;
		cadenaAleatoria = RandomStringUtils.random(2, todas);
		code = code + cadenaAleatoria;

		return code;
	}

	private String createCode() {
		String code = this.generator();
		boolean unico = true;
		Collection<ParentsGroup> all = this.parentsGroupRepository.findAll();
		for (ParentsGroup pg : all) {
			if (pg.getCode() == code) {
				unico = false;
				break;
			}
		}
		if (unico) {
			return code;
		} else {
			return createCode();
		}
	}

	//Gestion de los miembros de un grupo
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

		parentsGroup.getMembers().add(parent);

		parent.getParentsGroupsMemberOf().add(parentsGroup);

		this.parentsGroupRepository.save(parentsGroup);

		this.parentService.saveForParentsGroup(parent);

	}

	public Collection<ParentsGroup> findByKeyWord(final String keyWord) {
		final Collection<ParentsGroup> parentsGroups = this.parentsGroupRepository.findByKeyWord(keyWord);
		return parentsGroups;
	}

}
