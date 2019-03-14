
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClassGroupService;
import services.ClassTimeService;
import services.ParentsGroupService;
import services.SubjectService;
import controllers.AbstractController;
import domain.ClassGroup;
import domain.ClassTime;
import domain.ParentsGroup;
import domain.Subject;
import forms.ClassTimeForm;

@Controller
@RequestMapping("/classTime/parent")
public class ClassTimeParentsController extends AbstractController {

	@Autowired
	private ClassTimeService	classTimeService;

	@Autowired
	private ParentsGroupService	parentsGroupService;
	@Autowired
	private SubjectService		subjectService;
	@Autowired
	private ClassGroupService	classGroupService;


	// Panic handler ----------------------------------------------------------

	public ClassTimeParentsController() {
		super();
	}

	//  ---------------------------------------------------------------	

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final Integer classGroupId, @RequestParam Integer parentsGroupId) {
		final ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		Collection<ClassTime> classTimes = this.classTimeService.findByClassGroup(classGroupId, parentsGroup);

		result = createModelAndView("classTime/parent/list");
		result.addObject("classTimes", classTimes);
		ClassGroup classGroup = this.classGroupService.findOne(classGroupId);
		result.addObject("classGroup", classGroup);
		result.addObject("parentsGroup", parentsGroup);
		result.addObject("classGroupId", classGroupId);
		result.addObject("parentsGroupId", parentsGroupId);
		Collection<ClassTime> mon = this.classTimeService.findMon(classGroupId);
		Collection<ClassTime> tue = this.classTimeService.findTue(classGroupId);
		Collection<ClassTime> wed = this.classTimeService.findWed(classGroupId);
		Collection<ClassTime> thu = this.classTimeService.findThu(classGroupId);
		Collection<ClassTime> fri = this.classTimeService.findFri(classGroupId);
		result.addObject("mon", mon);
		result.addObject("tue", tue);
		result.addObject("wed", wed);
		result.addObject("thu", thu);
		result.addObject("fri", fri);
		return result;

	}

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final Integer classGroupId, @RequestParam Integer day, @RequestParam Integer parentsGroupId) {
		ModelAndView result = null;
		if (day == null || (day != 1 && day != 2 && day != 3 && day != 4 && day != 5)) {
			result = new ModelAndView("redirect:/classTime/parent/list.do?classGroupId=" + classGroupId + "&&parentsGroupId=" + parentsGroupId);
		} else {
			try {
				ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
				Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
				ClassGroup classGroup = this.classGroupService.findOneToEdit(classGroupId);
				ClassTimeForm classTimeForm = new ClassTimeForm();
				classTimeForm.setClassGroup(classGroup);
				classTimeForm.setDay(day.toString());
				classTimeForm.setParentsGroupId(parentsGroupId);
				//				ClassTime classTime = this.classTimeService.create(classGroupId);
				//				classTime.setDay(day.toString());
				Integer levelId = classGroup.getLevel().getId();
				result = this.createEditModelAndView(classTimeForm, levelId);
				result.addObject("classGroupId", classGroupId);
				boolean b = true;
				result.addObject("newct", b);
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/classTime/parent/list.do?classGroupId=" + classGroupId + "&&parentsGroupId=" + parentsGroupId);

			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final Integer classTimeId, @RequestParam Integer parentsGroupId) {
		ModelAndView result;
		final ClassTime classTime;
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		classTime = this.classTimeService.findOneToEdit(classTimeId);
		Integer i = classTime.getClassGroup().getLevel().getId();
		ClassTimeForm classTimeForm = new ClassTimeForm(classTime);
		classTimeForm.setParentsGroupId(parentsGroupId);
		result = this.createEditModelAndView(classTimeForm, i);
		boolean b = false;
		result.addObject("newct", b);
		Integer classGroupId = classTime.getClassGroup().getId();
		result.addObject("classGroupId", classGroupId);
		result.addObject("parentsGroupId", parentsGroupId);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final ClassTimeForm classTimeForm, final BindingResult binding) {
		ModelAndView result = null;
		ClassTime classTime = this.classTimeService.reconstruct(classTimeForm, binding);

		ParentsGroup parentsGroup = this.parentsGroupService.findOne(classTimeForm.getParentsGroupId());
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup));
		if (binding.hasErrors())
			result = this.createEditModelAndView(classTimeForm, classTime.getClassGroup().getLevel().getId());

		else {
			if (!this.classTimeService.controlHours(classTime.getHour())) {
				result = this.createEditModelAndView(classTimeForm, "parentsGroup.hours.error", classTime.getClassGroup().getLevel().getId());
			} else if (this.classTimeService.IsOverlapped(classTime.getHour(), classTime.getClassGroup().getId(), classTime.getDay(), classTime.getSubject().getName())) {
				result = this.createEditModelAndView(classTimeForm, "classTime.error.hour", classTime.getClassGroup().getLevel().getId());
			} else {
				try {
					this.classTimeService.save(classTime);
					result = new ModelAndView("redirect:/classTime/parent/list.do?classGroupId=" + classTime.getClassGroup().getId() + "&&parentsGroupId=" + classTimeForm.getParentsGroupId());
				} catch (final Throwable oops) {
					System.out.println(oops);
					result = this.createEditModelAndView(classTimeForm, "parentsGroup.comit.error", classTime.getClassGroup().getLevel().getId());
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ClassTimeForm classTimeForm, final BindingResult binding) {
		ModelAndView result = null;
		try {
			ClassTime classTime = this.classTimeService.findOneToEdit(classTimeForm.getId());
			Integer id = classTime.getClassGroup().getId();
			this.classTimeService.delete(classTime);
			result = new ModelAndView("redirect:/classTime/parent/list.do?classGroupId=" + id + "&&parentsGroupId=" + classTimeForm.getParentsGroupId());
		} catch (final Throwable oops) {
			System.out.println(oops);
			result = this.createEditModelAndView(classTimeForm, classTimeForm.getClassGroup().getLevel().getId());
		}
		return result;
	}
	private ModelAndView createEditModelAndView(final ClassTimeForm classTimeForm, Integer levelId) {
		ModelAndView result;
		result = this.createEditModelAndView(classTimeForm, null, levelId);
		return result;
	}
	private ModelAndView createEditModelAndView(final ClassTimeForm classTimeForm, final String message, Integer levelId) {
		ModelAndView result;
		result = super.createModelAndView("classTime/parent/edit");
		result.addObject("classTimeForm", classTimeForm);
		Collection<Subject> subjects;
		String s = "selected";
		subjects = this.subjectService.findByLevel(levelId);
		result.addObject("subjects", subjects);
		result.addObject("message", message);
		result.addObject("s", s);

		return result;
	}
}
