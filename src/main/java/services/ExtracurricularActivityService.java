
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ExtracurricularActivityRepository;
import domain.ExtracurricularActivity;
import domain.Parent;
import domain.ParentsGroup;

@Service
@Transactional
public class ExtracurricularActivityService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ExtracurricularActivityRepository	extracurricularActivityRepository;

	@Autowired
	private ParentsGroupService					parentsGroupService;

	@Autowired
	private ParentService						parentService;

	@Autowired
	private Validator							validator;


	// Constructors -----------------------------------------------------------

	public ExtracurricularActivityService() {
		super();
	}
	// Simple CRUD methods ----------------------------------------------------

	public ExtracurricularActivity create(final Integer parentsGroupId) {
		final ExtracurricularActivity res = new ExtracurricularActivity();
		final ParentsGroup currentGroup = this.parentsGroupService.findOne(parentsGroupId);
		res.setParentsGroup(currentGroup);
		return res;
	}
	public ExtracurricularActivity save(final ExtracurricularActivity extracurricularActivity) {
		this.checkConcurrency(extracurricularActivity);
		return this.extracurricularActivityRepository.save(extracurricularActivity);
	}

	public ExtracurricularActivity saveForParent(final ExtracurricularActivity extracurricularActivity) {
		this.checkPrincipalParent();
		return this.save(extracurricularActivity);
	}

	public void delete(final ExtracurricularActivity extracurricularAct) {
		this.extracurricularActivityRepository.delete(extracurricularAct);
	}

	public void deleteForParents(final ExtracurricularActivity extracurricularAct) {
		this.checkPrincipalParent();
		Assert.isTrue(this.parentService.findByPrincipal().getParentsGroupsCreated().contains(extracurricularAct.getParentsGroup()) || this.parentService.findByPrincipal().getParentsGroupsManaged().contains(extracurricularAct.getParentsGroup())
			|| this.parentService.findByPrincipal().getParentsGroupsMemberOf().contains(extracurricularAct.getParentsGroup()));
		this.delete(extracurricularAct);
	}
	public ExtracurricularActivity findOne(final Integer extracurricularActivityId) {
		return this.extracurricularActivityRepository.findOne(extracurricularActivityId);
	}

	public ExtracurricularActivity findOneToEdit(final Integer extracurricularActivityId) {
		this.checkPrincipalParent();
		final ExtracurricularActivity extracurricularAct = this.extracurricularActivityRepository.findOne(extracurricularActivityId);
		Assert.isTrue(this.parentService.findByPrincipal().getParentsGroupsCreated().contains(extracurricularAct.getParentsGroup()) || this.parentService.findByPrincipal().getParentsGroupsManaged().contains(extracurricularAct.getParentsGroup())
			|| this.parentService.findByPrincipal().getParentsGroupsMemberOf().contains(extracurricularAct.getParentsGroup()));
		return this.findOne(extracurricularActivityId);
	}

	public Collection<ExtracurricularActivity> findAll() {
		return this.extracurricularActivityRepository.findAll();
	}

	// Other business methods -------------------------------------------------

	public ExtracurricularActivity reconstruct(final ExtracurricularActivity extracurricularActivity, final BindingResult binding) {
		ExtracurricularActivity res;
		if (extracurricularActivity.getId() == 0)
			res = extracurricularActivity;
		else {
			final ExtracurricularActivity aux = this.findOne(extracurricularActivity.getId());
			res = new ExtracurricularActivity();
			res.setId(extracurricularActivity.getId());
			res.setVersion(extracurricularActivity.getVersion());
			res.setTitle(extracurricularActivity.getTitle());
			res.setDescription(extracurricularActivity.getDescription());
			res.setParentsGroup(aux.getParentsGroup());
		}

		this.validator.validate(res, binding);

		return res;
	}

	private void checkConcurrency(final ExtracurricularActivity extracurricularActivity) {
		if (extracurricularActivity.getId() != 0) {
			final ExtracurricularActivity c = this.findOne(extracurricularActivity.getId());
			Assert.isTrue(extracurricularActivity.getVersion() == c.getVersion(), "Ha habido un cambio previo y no se puede editar esta actividad");
		}
	}

	private void checkPrincipalParent() {
		try {
			final Parent logged = this.parentService.findByPrincipal();
			Assert.notNull(logged);
		} catch (final Exception e) {
		}
	}

}
