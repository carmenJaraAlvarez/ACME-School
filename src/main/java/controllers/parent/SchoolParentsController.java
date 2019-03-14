
package controllers.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SchoolService;
import controllers.AbstractController;
import domain.School;

@Controller
@RequestMapping("/school/parent")
public class SchoolParentsController extends AbstractController {

	@Autowired
	private SchoolService	schoolService;


	// Panic handler ----------------------------------------------------------

	public SchoolParentsController() {
		super();
	}

	//  ---------------------------------------------------------------		

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam(required = false) final Integer createGroup) {

		ModelAndView result;
		final School school = this.schoolService.create();

		if (createGroup == 1)
			result = this.createEditModelAndViewForCreateGroup(school);
		else
			result = this.createEditModelAndView(school);
		return result;
	}

	private ModelAndView createEditModelAndViewForCreateGroup(final School school) {
		ModelAndView result;
		result = this.createEditModelAndViewForCreateGroup(school, null);
		return result;
	}

	private ModelAndView createEditModelAndViewForCreateGroup(final School school, final Object object) {
		ModelAndView result;
		result = super.createModelAndView("school/editForCreateGroup");
		result.addObject("school", school);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int schoolId) {
		ModelAndView result;
		final School school;
		school = this.schoolService.findOneToEdit(schoolId);
		Assert.notNull(school);
		result = this.createEditModelAndView(school);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final School school, final BindingResult binding) {
		ModelAndView result;
		final School reconstructed = this.schoolService.reconstruct(school, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(school);
		else {
			if (this.schoolService.checkConcurrency(reconstructed)) {
				result = this.createEditModelAndView(reconstructed, "school.concurrency.error");
			} else {
				try {

					final School save = this.schoolService.saveForParent(reconstructed);
					if (save.getVersion() == 0)
						this.schoolService.addLevels(save);
					result = new ModelAndView("redirect:/school/list.do");

				} catch (final Throwable oops) {
					System.out.println(oops);
					result = this.createEditModelAndView(reconstructed, "school.commit.error");
				}
			}
		}
		return result;
	}

	private ModelAndView createEditModelAndView(final School school) {
		ModelAndView result;
		result = this.createEditModelAndView(school, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final School school, final String message) {
		ModelAndView result;
		result = super.createModelAndView("school/edit");
		result.addObject("school", school);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/editForCreateGroup", method = RequestMethod.POST, params = "save")
	public ModelAndView saveForCreateGroup(final School school, final BindingResult binding) {
		ModelAndView result;
		final School reconstructed = this.schoolService.reconstruct(school, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndViewForCreateGroup(school);
		else
			try {
				final School save = this.schoolService.saveForParent(school);
				this.schoolService.addLevels(save);
				result = new ModelAndView("redirect:/parentsGroup/parent/create.do");
			} catch (final Throwable oops) {
				System.out.println(oops);
				result = this.createEditModelAndViewForCreateGroup(reconstructed, "school.commit.error");
			}
		return result;
	}

}
