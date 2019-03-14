
package controllers.student;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.ChatRoomService;
import services.StudentService;
import controllers.AbstractController;
import domain.ChatRoom;
import domain.Student;

@Controller
@RequestMapping("/student/student")
public class StudentStudentController extends AbstractController {

	@Autowired
	private ChatRoomService	chatRoomService;

	@Autowired
	private StudentService	studentService;


	// Panic handler ----------------------------------------------------------

	public StudentStudentController() {
		super();
	}

	//  ---------------------------------------------------------------	

	@RequestMapping("/memberList")
	public ModelAndView list(final Integer chatRoomId) {
		ModelAndView result;
		final ChatRoom chat = this.chatRoomService.findOne(chatRoomId);
		final Collection<Student> students = chat.getStudents();
		result = super.createModelAndView("student/memberList");
		result.addObject("students", students);
		result.addObject("uri", "student/student/memberList.do?chatRoomId=" + chatRoomId);
		result.addObject("uriCancel", "chatRoom/student/myList.do");
		return result;
	}
}
