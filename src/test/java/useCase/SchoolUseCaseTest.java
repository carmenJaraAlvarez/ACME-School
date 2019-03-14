
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ParentService;
import services.SchoolService;
import utilities.AbstractTest;
import domain.School;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class SchoolUseCaseTest extends AbstractTest {

	//Services used
	@Autowired
	private SchoolService	schoolService;

	@Autowired
	private ParentService	parentService;


	//Use case 07: Dar de alta un colegio
	protected void templateCreateSaveSchool(final String username, final String nameSchool, final String address, final String phoneNumber, final String emailSchool, final String image, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			this.schoolService.findAll();
			final School sc = this.schoolService.create();
			sc.setAddress(address);
			sc.setEmailSchool(emailSchool);
			sc.setImage(image);
			sc.setNameSchool(nameSchool);
			sc.setPhoneNumber(phoneNumber);
			this.schoolService.save(sc);
			this.schoolService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverCreateSaveSchool() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "schoolName", "address", "123456789", "email@School.com", "http://image.com", null
			},
			//Negative test
			{
				"parent1", "", "address", "123456789", "email@School.com", "http://image.com", ConstraintViolationException.class
			},
			//Negative test
			{
				"parent1", "schoolName", "address", "123456789", "emailSchool.com", "http://image.com", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateCreateSaveSchool((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (Class<?>) testingData[i][6]);
	}

	//Use case 08: Editar un colegio
	protected void templateEditSchool(final String username, final String schoolBean, final String nameSchool, final String address, final String phoneNumber, final String emailSchool, final String image, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		try {
			this.authenticate(username);
			this.schoolService.findAll();
			final School sc = this.schoolService.findOneToEdit(super.getEntityId(schoolBean));
			sc.setAddress(address);
			sc.setEmailSchool(emailSchool);
			sc.setImage(image);
			sc.setNameSchool(nameSchool);
			sc.setPhoneNumber(phoneNumber);
			this.schoolService.save(sc);
			this.schoolService.findAll();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	@Test
	public void driverEditSchool() {
		final Object testingData[][] = {
			//Positive test
			{
				"parent1", "school1", "schoolName", "address", "123456789", "email@School.com", "http://image.com", null
			},
			//Negative test
			{
				"student1", "school1", "schoolName", "address", "123456789", "email@School.com", "http://image.com", IllegalArgumentException.class
			},
			//Negative test
			{
				"parent1", "school1", "schoolName", "address", "123456789", "emailSchool.com", "http://image.com", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateEditSchool((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(Class<?>) testingData[i][7]);
	}

}
