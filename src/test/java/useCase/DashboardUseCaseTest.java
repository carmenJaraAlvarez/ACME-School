
package useCase;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.AdministratorService;
import services.AdvertisementService;
import services.EventService;
import services.HomeworkService;
import services.MarkService;
import services.ParentService;
import services.ParentsGroupService;
import services.SchoolService;
import services.StudentService;
import services.TeacherService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class DashboardUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private MarkService				markService;
	@Autowired
	private TeacherService			teacherService;
	@Autowired
	private ParentService			parentService;
	@Autowired
	private EventService			eventService;
	@Autowired
	private HomeworkService			homeworkService;
	@Autowired
	private ParentsGroupService		parentsGroupService;
	@Autowired
	private AdvertisementService	advertisementService;
	@Autowired
	private SchoolService			schoolService;
	@Autowired
	private StudentService			studentService;
	@Autowired
	private AdministratorService	administratorService;


	//Use case 26: Dashboard.
	protected void templateDashboard(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			Assert.notNull(username);
			this.authenticate(username);
			Assert.notNull(this.administratorService.findByPrincipal());
			this.parentService.getMinParentPerGroup();
			this.parentService.getMaxParentPerGroup();
			this.parentService.getAverageParentPerGroup();
			this.parentService.getStandardDeviationParentPerGroup();

			this.eventService.getMinEventPerGroup();
			this.eventService.getMaxEventPerGroup();
			this.eventService.getAverageEventPerGroup();
			this.eventService.getStandardDeviationEventPerGroup();

			this.homeworkService.getMinHomeworkPerSubject();
			this.homeworkService.getMaxHomeworkPerSubject();
			this.homeworkService.getAverageHomeworkPerSubject();
			this.homeworkService.getStandardDeviationHomeworkPerSubject();

			this.parentsGroupService.getGroupMostParents();

			this.parentService.getParentsMessageLastWeek();
			this.parentService.getParentsMessageLast2Weeks();

			this.advertisementService.getAmountAdsHiredLastMonth();
			this.markService.getPassedMarksLastYear();

			this.teacherService.getAverageTeachersPerSchool();
			this.teacherService.getStandardDeviationTeachersPerSchool();

			this.studentService.getAverageStudentsPerClass();
			this.studentService.getStandardDeviationTeachersPerSchool();

			this.schoolService.getSchoolMostTeachers();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	@Test
	public void driverDashboard() {
		final Object testingData[][] = {
			//Positive test
			{
				"admin", null
			},
			//Negative test
			{
				"parent1", IllegalArgumentException.class
			},
			//Negative test
			{
				null, IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDashboard((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

}
