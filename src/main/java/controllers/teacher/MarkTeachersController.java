
package controllers.teacher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MarkService;
import services.ParentService;
import services.PrivateMessageService;
import services.StudentService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Actor;
import domain.Mark;
import domain.PrivateMessage;
import domain.Student;

@Controller
@RequestMapping("/mark/teacher")
public class MarkTeachersController extends AbstractController {

	@Autowired
	private TeacherService			teacherService;

	@Autowired
	private MessageSource			messageSource;

	@Autowired
	private MarkService				markService;

	@Autowired
	private ParentService			parentService;

	@Autowired
	private StudentService			studentService;

	@Autowired
	private PrivateMessageService	privateMessageService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ActorService			actorService;


	// Panic handler ----------------------------------------------------------

	public MarkTeachersController() {
		super();
	}

	//  ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int studentId) {
		ModelAndView result;
		result = super.createModelAndView("mark/teacher/list");
		try {
			final Collection<Mark> marks = this.markService.findByStudentForTeacher(studentId);
			final Student student = this.studentService.findOne(studentId);
			if (student == null) {
				result.addObject("notAuthorized", true);
			}
			result.addObject("marks", marks);
			result.addObject("studentId", studentId);
			result.addObject("student", student);
			result.addObject("authorized", true);
			String uri = "mark/teacher/list.do?studentId=" + studentId;
			result.addObject("uri", uri);
		} catch (Throwable oops) {
			result.addObject("notAuthorized", true);
		}
		return result;
	}

	@RequestMapping(value = "/create")
	public ModelAndView create(@RequestParam final int studentId) {
		ModelAndView result;
		final Mark mark = this.markService.create(studentId);
		result = this.createEditModelAndView(mark);
		result.addObject("mark", mark);
		result.addObject("subjects", mark.getStudent().getClassGroup().getLevel().getSubjects());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int markId) {
		ModelAndView result;
		final Mark mark;
		mark = this.markService.findOneToEdit(markId);
		result = this.createEditModelAndView(mark);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Mark mark, final BindingResult binding) {
		ModelAndView result;

		final Mark reconstructed = this.markService.reconstruct(mark, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView(mark);
		else
			try {
				final Mark save = this.markService.saveForTeacher(reconstructed);
				Student student = save.getStudent();
				Locale locale = LocaleContextHolder.getLocale();
				PrivateMessage msg = this.privateMessageService.markNotification(student, locale);
				Actor parent = student.getParent();
				Collection<Actor> actorReceivers = new ArrayList<>();
				actorReceivers.add(parent);
				this.privateMessageService.saveNewMessage(msg, actorReceivers);
				result = new ModelAndView("redirect:/mark/teacher/list.do?studentId=" + student.getId());
			} catch (final Throwable oops) {
				System.out.println(oops.getMessage());
				String msg = "mark.commit.error";
				if (oops.getMessage().contains("concurrency")) {
					msg = "mark.concurrency.error";
				}
				result = this.createEditModelAndView(reconstructed, msg);
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Mark mark) {
		ModelAndView result;
		result = this.createEditModelAndView(mark, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Mark mark, final String message) {
		ModelAndView result;
		result = super.createModelAndView("mark/teacher/edit");
		result.addObject("mark", mark);
		result.addObject("subjects", mark.getStudent().getClassGroup().getLevel().getSubjects());
		return result;
	}

}
