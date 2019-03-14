
package controllers.parent;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MarkService;
import services.ParentService;
import services.StudentService;
import controllers.AbstractController;
import domain.Mark;
import domain.Student;

@Controller
@RequestMapping("/mark/parent")
public class MarkParentsController extends AbstractController {

	@Autowired
	private MarkService		markService;

	@Autowired
	private ParentService	parentService;
	@Autowired
	private StudentService	studentService;


	// Panic handler ----------------------------------------------------------

	public MarkParentsController() {
		super();
	}

	//  ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int studentId) {
		ModelAndView result = super.createModelAndView("mark/parent/list");
		try {
			final Collection<Mark> marks = this.markService.findByStudentForParent(studentId);
			final Student student = this.studentService.findOne(studentId);
			if (student == null) {
				result.addObject("notAuthorized", true);
			}
			result.addObject("marks", marks);
			result.addObject("student", student);
			result.addObject("authorized", true);
			String uri = "mark/parent/list.do?studentId=" + studentId;
			result.addObject("uri", uri);
		} catch (Throwable oops) {
			result.addObject("notAuthorized", true);
		}
		return result;
	}

}
