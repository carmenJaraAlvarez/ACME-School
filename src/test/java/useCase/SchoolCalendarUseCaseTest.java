
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.AdministratorService;
import services.HomeworkService;
import services.ParentService;
import services.ParentsGroupService;
import services.SchoolCalendarService;
import services.SubjectService;
import utilities.AbstractTest;
import domain.Administrator;
import domain.Parent;
import domain.ParentsGroup;
import domain.SchoolCalendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SchoolCalendarUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private HomeworkService			homeworkService;
	@Autowired
	private ParentService			parentService;
	@Autowired
	private ParentsGroupService		parentsGroupService;
	@Autowired
	private SubjectService			subjectService;
	@Autowired
	private SchoolCalendarService	schoolCalendarService;
	@Autowired
	private AdministratorService	administratorService;


	//Use case 21: Asociar un calendario a alguno de los grupos a los que pertenezca.
	protected void templateLinkCalendar(final String username, final String parentsGroup, final String schoolCalendarBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			final ParentsGroup pg = this.parentsGroupService.findOneToDisplay(this.getEntityId(parentsGroup));
			final SchoolCalendar sc = this.schoolCalendarService.findOne(this.getEntityId(schoolCalendarBean));

			this.schoolCalendarService.link(pg, sc);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverLinkCalendar() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "parentsGroup1", "schoolCalendar1", null
			},
			//Negative test
			{
				"teacher1", "parentsGroup1", "schoolCalendar1", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "parentsGroup1", "schoolCalendar99", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateLinkCalendar((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	//Use case 34: Crear calendarios escolares.
	protected void templateCreateSaveSchoolCalendar(final String username, final String course, final String country, final String image, final String region, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			final Administrator admin = this.administratorService.findByPrincipal();

			final SchoolCalendar calendar = this.schoolCalendarService.create();
			calendar.setCountry(country);
			calendar.setCourse(course);
			calendar.setImage(image);
			calendar.setRegion(region);

			this.schoolCalendarService.saveForAdmin(calendar);
			this.schoolCalendarService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveSchoolCalendar() {
		final Object testingData[][] = {
			//Positive test
			{
				"admin", "2018/2019", "Spain", "http://image.com", "Andalusia", null
			},
			//Negative test
			{
				"parent1", "2018/2019", "Spain", "http://image.com", "Andalusia", IllegalArgumentException.class
			},
			//Negative test
			{
				"admin", "", "", "", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveSchoolCalendar((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

}
