
package useCase;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.AdministratorService;
import services.FolderService;
import services.ParentService;
import services.PrivateMessageService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;
import domain.Folder;
import domain.Parent;
import domain.PrivateMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class MessageUseCaseTest extends AbstractTest {

	@Autowired
	private FolderService			folderService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private PrivateMessageService	privateMessageService;
	@Autowired
	private ParentService			parentService;
	@Autowired
	private AdministratorService	administratorService;


	//--------------------------------------------------------------------------

	//Use case 04.1: Mandar mensajes
	protected void templateSendMessage(final String usernameFrom, final String usernameTo, final String subject, final String body, final String priority, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(usernameFrom);
			final Parent p = this.parentService.findByPrincipal();
			Assert.notNull(this.actorService.findByUserName(usernameTo));
			final PrivateMessage send = this.privateMessageService.create();
			send.setBody(body);
			send.setPriority(priority);
			send.setSubject(subject);

			final Collection<Actor> actorReceivers = new ArrayList<Actor>();
			actorReceivers.add(this.actorService.findByUserName(usernameTo));

			this.privateMessageService.saveNewMessage(send, actorReceivers);
			this.privateMessageService.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		this.authenticate(null);
	}

	@Test
	public void driverSendMessage() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "parent2", "subject", "body", "LOW", null
			},
			//Negative
			{
				null, "paren2", "subject", "body", "LOW", IllegalArgumentException.class
			},
			//Negative
			{
				"parent1", null, "subject", "body", "LOW", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSendMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (Class<?>) testingData[i][5]);
	}

	//Use case 04.2: Eliminar mensajes
	protected void templateDeleteMessage(final String usernameFrom, final String messageBean, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			Folder res = null;
			this.authenticate(usernameFrom);
			final Parent p = this.parentService.findByPrincipal();

			final PrivateMessage pm = this.privateMessageService.findOne(this.getEntityId(messageBean));
			final Collection<Folder> folders = p.getFolders();
			for (final Folder f : folders)
				if (f.getPrivateMessages().contains(pm))
					res = f;
			this.privateMessageService.delete(pm, res);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		this.authenticate(null);
	}

	@Test
	public void driverDeleteMessage() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "privateMessage2", null
			},
			//Negative
			{
				"student1", "privateMessage1", IllegalArgumentException.class
			},
			//Negative
			{
				"parent1", "privateMessage12345", AssertionError.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateDeleteMessage((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	//Use case 04.3: Mover mensajes
	protected void templateMoveMessage(final String usernameFrom, final String messageBean, final String currFolder, final String finalFolder, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(usernameFrom);
			final Parent p = this.parentService.findByPrincipal();

			final PrivateMessage pm = this.privateMessageService.findOne(this.getEntityId(messageBean));
			final Folder actFolder = this.folderService.findOne(this.getEntityId(currFolder));
			final Folder finFolder = this.folderService.findOne(this.getEntityId(finalFolder));

			this.privateMessageService.changeFolder(pm, actFolder, finFolder);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		this.authenticate(null);
	}
	@Test
	public void driverMoveMessage() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "privateMessage2", "inBoxParent1", "trashBoxParent1", null
			},
			//Negative
			{
				null, "privateMessage2", "inBoxParent1", "trashBoxParent1", IllegalArgumentException.class
			},
			//Negative
			{
				"teacher1", "privateMessage2", "inBoxParent1", "trashBoxParent1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateMoveMessage((String) testingData[i][0], (String) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (Class<?>) testingData[i][4]);
	}

	//Use case 25: Mandar una notificacion
	protected void templateSendNotification(final String usernameFrom, final String message, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(usernameFrom);
			Assert.notNull(this.administratorService.findByPrincipal());
			final Administrator p = this.administratorService.findByPrincipal();

			final PrivateMessage pm = this.privateMessageService.create();
			pm.setBody(message);
			pm.setSubject("Notificación importante");
			pm.setPriority("LOW");
			this.privateMessageService.newNotification(pm);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		this.authenticate(null);
	}

	@Test
	public void driverSendNotification() {
		final Object testingData[][] = {
			//Positive
			{
				"admin", "notification test", null
			},
			//Negative
			{
				null, "notification test", IllegalArgumentException.class
			},
			//Negative
			{
				"teacher1", "notification test", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateSendNotification((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
