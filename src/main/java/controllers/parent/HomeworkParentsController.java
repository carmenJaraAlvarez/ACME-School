
package controllers.parent;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HomeworkService;
import services.ParentService;
import services.ParentsGroupService;
import controllers.AbstractController;
import domain.Homework;
import domain.ParentsGroup;
import domain.Subject;

@Controller
@RequestMapping("/homework/parent")
public class HomeworkParentsController extends AbstractController {

	@Autowired
	private HomeworkService		homeworkService;

	@Autowired
	private ParentsGroupService	parentsGroupService;

	@Autowired
	private ParentService		parentService;


	// Panic handler ----------------------------------------------------------

	public HomeworkParentsController() {
		super();
	}

	//  ---------------------------------------------------------------	

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final Integer parentsGroupId) {
		final ModelAndView result;
		final ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		final Collection<Subject> subjects = this.parentsGroupService.getSubjects(parentsGroup);
		this.parentsGroupService.checkMember(parentsGroup);
		for (final Subject s : subjects) {
			final Collection<Homework> homeworksCopy = new ArrayList<Homework>(s.getHomeworks());
			for (final Homework hw : homeworksCopy)
				if (hw.getParentsGroup().getId() != parentsGroupId)
					s.getHomeworks().remove(hw);
		}

		result = createModelAndView("homework/list");
		result.addObject("subjects", subjects);
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("parentsGroup", parentsGroup);
		return result;
	}

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final Integer parentsGroupId) {
		ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		final Homework homework = this.homeworkService.create(parentsGroupId);
		result = this.createEditModelAndView(homework, parentsGroupId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int homeworkId) {
		ModelAndView result;
		final Homework homework;
		homework = this.homeworkService.findOneToEdit(homeworkId);
		ParentsGroup parentsGroup = homework.getParentsGroup();
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		result = this.createEditModelAndView(homework, homework.getParentsGroup().getId());

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Homework homework, final BindingResult binding) {
		ModelAndView result;
		final Homework reconstructed = this.homeworkService.reconstruct(homework, binding);
		ParentsGroup parentsGroup = reconstructed.getParentsGroup();
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		if (binding.hasErrors())
			result = this.createEditModelAndView(homework, homework.getParentsGroup().getId());
		else
			try {
				final Homework save = this.homeworkService.saveForParent(homework);
				result = new ModelAndView("redirect:/homework/parent/list.do?parentsGroupId=" + save.getParentsGroup().getId());
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndView(reconstructed, homework.getParentsGroup().getId(), "homework.commit.error");
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Homework homework, final int parentsGroupId) {
		ModelAndView result;
		result = this.createEditModelAndView(homework, parentsGroupId, null);
		return result;
	}
	private ModelAndView createEditModelAndView(final Homework homework, final int parentsGroupId, final String message) {
		ModelAndView result;
		result = super.createModelAndView("homework/edit");
		result.addObject("homework", homework);
		result.addObject("parentsGroupId", parentsGroupId);
		result.addObject("subjects", this.parentsGroupService.getSubjects(homework.getParentsGroup()));

		return result;
	}
}
