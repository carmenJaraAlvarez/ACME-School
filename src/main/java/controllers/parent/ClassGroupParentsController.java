
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClassGroupService;
import services.LevelService;
import services.SchoolService;
import controllers.AbstractController;
import domain.ClassGroup;
import domain.School;

@Controller
@RequestMapping("/classGroup/parent")
public class ClassGroupParentsController extends AbstractController {

	@Autowired
	SchoolService		schoolService;

	@Autowired
	LevelService		levelService;

	@Autowired
	ClassGroupService	classGroupService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int schoolId) {
		ModelAndView result;
		final School school = this.schoolService.findOne(schoolId);
		final Collection<ClassGroup> classGroups = this.schoolService.classGroupOfSchool(school);
		result = createModelAndView("classGroup/list");
		result.addObject("classGroups", classGroups);
		result.addObject("schoolId", schoolId);
		result.addObject("requestURI", "classGroup/parent/list.do");
		return result;
	}

	@RequestMapping("/create")
	public ModelAndView create(@RequestParam final int levelId) {
		final ModelAndView result;
		final ClassGroup classGroup = this.classGroupService.create(levelId);
		result = this.createEditModelAndView(classGroup);
		result.addObject("classGroup", classGroup);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int classGroupId) {
		ModelAndView result;
		final ClassGroup cg = this.classGroupService.findOneToEdit(classGroupId);
		result = this.createEditModelAndView(cg);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ClassGroup classGroup, final BindingResult binding) {
		ModelAndView result;
		final ClassGroup reconstructed = this.classGroupService.reconstruct(classGroup, binding);
		if (this.classGroupService.checkingConcurrency(classGroup)) {
			result = this.createEditModelAndView(reconstructed, "classGroup.concurrency.error");
		} else {
			if (binding.hasErrors())
				result = this.createEditModelAndView(classGroup);
			else {
				if (this.classGroupService.checkname(reconstructed)) {
					result = this.createEditModelAndView(reconstructed, "classGroup.name.error");

				} else {
					try {
						final ClassGroup save = this.classGroupService.saveForParent(classGroup);
						result = new ModelAndView("redirect:/school/display.do?schoolId=" + save.getLevel().getSchool().getId());
					} catch (final Throwable oops) {
						System.out.println(oops);
						result = this.createEditModelAndView(reconstructed, "classGroup.commit.error");
					}
				}
			}
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final ClassGroup classGroup) {
		ModelAndView result;
		result = this.createEditModelAndView(classGroup, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final ClassGroup classGroup, final String message) {
		ModelAndView result;
		result = super.createModelAndView("classGroup/edit");
		result.addObject("classGroup", classGroup);
		result.addObject("message", message);

		return result;
	}
}
