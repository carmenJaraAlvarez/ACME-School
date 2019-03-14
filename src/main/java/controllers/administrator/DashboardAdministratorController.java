
package controllers.administrator;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import services.EventService;
import services.HomeworkService;
import services.MarkService;
import services.ParentService;
import services.ParentsGroupService;
import services.SchoolService;
import services.StudentService;
import services.TeacherService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	//Services

	@Autowired
	private EventService			eventService;
	@Autowired
	private HomeworkService			homeworkService;
	@Autowired
	private ParentService			parentService;
	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private TeacherService			teacherService;
	@Autowired
	private StudentService			studentService;
	@Autowired
	private SchoolService			schoolService;
	@Autowired
	private ParentsGroupService		parentsGroupService;
	@Autowired
	private MarkService				markService;


	//Constructors

	public DashboardAdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		final DecimalFormat df = new DecimalFormat("###.##");

		result = this.createModelAndView("administrator/dashboard");

		result.addObject("minTutorsGroup", df.format(this.parentService.getMinParentPerGroup()));
		result.addObject("maxTutorsGroup", df.format(this.parentService.getMaxParentPerGroup()));
		result.addObject("avgTutorsGroup", df.format(this.parentService.getAverageParentPerGroup()));
		result.addObject("stdevTutorsGroup", df.format(this.parentService.getStandardDeviationParentPerGroup()));

		result.addObject("minEventsGroup", df.format(this.eventService.getMinEventPerGroup()));
		result.addObject("maxEventsGroup", df.format(this.eventService.getMaxEventPerGroup()));
		result.addObject("avgEventsGroup", df.format(this.eventService.getAverageEventPerGroup()));
		result.addObject("stdevEventsGroup", df.format(this.eventService.getStandardDeviationEventPerGroup()));

		result.addObject("minHomeSubject", df.format(this.homeworkService.getMinHomeworkPerSubject()));
		result.addObject("maxHomeSubject", df.format(this.homeworkService.getMaxHomeworkPerSubject()));
		result.addObject("avgHomeSubject", df.format(this.homeworkService.getAverageHomeworkPerSubject()));
		result.addObject("stdevHomeSubject", df.format(this.homeworkService.getStandardDeviationHomeworkPerSubject()));

		result.addObject("groupMostTutors", this.parentsGroupService.getGroupMostParents());

		result.addObject("prntsMostMsgsLastWeek", this.parentService.getParentsMessageLastWeek());
		result.addObject("prntsMostMsgsLast2Week", this.parentService.getParentsMessageLast2Weeks());

		result.addObject("nAdsLastMonth", this.advertisementService.getAmountAdsHiredLastMonth());
		result.addObject("nMarksLastYear", this.markService.getPassedMarksLastYear());

		result.addObject("avgTeachersSchool", df.format(this.teacherService.getAverageTeachersPerSchool()));
		result.addObject("stdevTeachersSchool", df.format(this.teacherService.getStandardDeviationTeachersPerSchool()));

		result.addObject("avgStudentsClass", df.format(this.studentService.getAverageStudentsPerClass()));
		result.addObject("stdevStudentsClass", df.format(this.studentService.getStandardDeviationTeachersPerSchool()));

		result.addObject("schoolMostTeach", this.schoolService.getSchoolMostTeachers());

		result.addObject("requestURI", "/dashboard/administrator/display.do");

		return result;
	}
}
