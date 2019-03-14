
package useCase;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.FolderService;
import services.ParentService;
import utilities.AbstractTest;
import domain.Folder;
import domain.Parent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@Transactional
public class FolderUseCaseTest extends AbstractTest {

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ParentService	parentService;


	//--------------------------------------------------------------------------

	//Use case 05: Crear carpeta, moverla y eliminarla
	protected void templateNewFolder(final String username, final String folderName, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Parent p = this.parentService.findByPrincipal();
			p.getFolders();
			final Folder fold = this.folderService.create();

			fold.setName(folderName);
			final Folder recon = this.folderService.reconstruct(fold, this.getEntityId("inBoxParent1"), null);
			final Folder saved = this.folderService.save(recon);
			this.folderService.flush();
			final Folder recon2 = this.folderService.reconstruct(fold, this.getEntityId("trashBoxParent1"), null);
			final Folder saved2 = this.folderService.save(recon2);
			this.folderService.flush();
			this.folderService.delete(saved2);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
		this.authenticate(null);
	}

	@Test
	public void driverNewFolder() {
		final Object testingData[][] = {
			//Positive
			{
				"parent1", "name folder test", null
			},
			//Negative
			{
				null, "name folder test", IllegalArgumentException.class
			},
			//Negative
			{
				"parent1", "", ConstraintViolationException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.templateNewFolder((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}
}
