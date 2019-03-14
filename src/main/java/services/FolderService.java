
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.PrivateMessage;

@Service
@Transactional
public class FolderService {

	//Managed respository -------------------------------------

	@Autowired
	private FolderRepository		folderRepository;

	//Supporting services ------------------------------------
	@Autowired
	private ActorService			actorService;
	@Autowired
	private PrivateMessageService	messageService;

	@Autowired
	private Validator				validator;


	//Constructors
	public FolderService() {
		super();
	}

	//Simple CRUD methods ------------------------------------

	public Folder create() {
		final Folder res = new Folder();
		return res;
	}

	public Folder create(final Folder father) {
		//father puede ser null
		final Collection<Folder> childFolders = new ArrayList<Folder>();
		final Collection<PrivateMessage> messages = new ArrayList<PrivateMessage>();
		final Actor propietario = this.actorService.findByPrincipal();
		final Folder res = new Folder();

		res.setActor(propietario);
		res.setChildFolders(childFolders);
		res.setPrivateMessages(messages);
		res.setFatherFolder(father);
		res.setOfTheSystem(false);
		return res;
	}

	public Folder createForNewUser() {
		//father puede ser null
		final Collection<Folder> childFolders = new ArrayList<Folder>();
		final Collection<PrivateMessage> messages = new ArrayList<PrivateMessage>();
		final Folder res = new Folder();
		res.setChildFolders(childFolders);
		res.setPrivateMessages(messages);
		res.setOfTheSystem(false);
		return res;
	}

	public Folder create(final Actor actor, final String name) {

		final Collection<Folder> childFolders = new ArrayList<Folder>();
		final Collection<PrivateMessage> messages = new ArrayList<PrivateMessage>();

		final Folder res = new Folder();
		res.setActor(actor);
		res.setName(name);
		res.setChildFolders(childFolders);
		res.setPrivateMessages(messages);

		return res;
	}

	public Folder findOne(final int folderId) {
		return this.folderRepository.findOne(folderId);
	}

	public Folder findOneToEdit(final int folderId) {
		final Folder folder = this.folderRepository.findOne(folderId);
		this.checkPrincipal(folder);
		return folder;
	}

	public Folder save(final Folder folder) {
		this.checkConcurrence(folder);
		Folder resFolder = this.folderRepository.save(folder);
		return resFolder;
	}
	public boolean delete(final Folder folder) {
		boolean result = false;
		Assert.isTrue(!folder.getOfTheSystem(), "carpeta del sistema");
		Assert.isTrue(folder.getActor() == this.actorService.findByPrincipal(), "No es propietario");
		//mensajes a papelera
		Folder papelera = null;
		papelera = this.folderRepository.trashBox(folder.getActor().getId());
		Assert.isTrue(papelera != null, "no localizada papelera");
		if (!folder.getPrivateMessages().isEmpty()) {
			result = true;
			Collection<PrivateMessage> privateMessages = new ArrayList<PrivateMessage>(folder.getPrivateMessages());
			for (final PrivateMessage msg : privateMessages) {
				this.messageService.changeFolder(msg, folder, papelera);
			}
		}
		final Collection<Folder> hijas = folder.getChildFolders();
		//comprobación carpetas hijas
		if (hijas.size() > 0)
			for (final Folder f : hijas)
				result = this.delete(f);

		this.folderRepository.delete(folder);

		return result;
	}

	public Collection<Folder> folderOfSystem() {
		Collection<Folder> folders = new ArrayList<Folder>();
		try {
			Folder inBox = this.createForNewUser();
			inBox.setOfTheSystem(true);
			inBox.setName("in box");
			Folder outBox = this.createForNewUser();
			outBox.setOfTheSystem(true);
			outBox.setName("out box");
			Folder notificationBox = this.createForNewUser();
			notificationBox.setOfTheSystem(true);
			notificationBox.setName("notification box");
			Folder trashBox = this.createForNewUser();
			trashBox.setOfTheSystem(true);
			trashBox.setName("trash box");
			Folder spamBox = this.createForNewUser();
			spamBox.setOfTheSystem(true);
			spamBox.setName("spam box");
			folders.add(inBox);
			folders.add(outBox);
			folders.add(notificationBox);
			folders.add(trashBox);
			folders.add(spamBox);
		} catch (final Throwable oops) {
		}
		return folders;
	}
	//Complex methods ----------------------------------------

	public Collection<Folder> findByActor(final Actor actor) {
		return this.folderRepository.findByActorId(actor.getId());
	}

	public Collection<Folder> findByFatherFolder(final int fatherId) {
		return this.folderRepository.findByFatherFolder(fatherId);
	}

	public Collection<Folder> findByPrincipal() {
		Collection<Folder> res;
		final Actor a = this.actorService.findByPrincipal();
		res = this.folderRepository.findByActorId(a.getId());
		return res;
	}
	public Collection<Folder> primerNivel(Actor actor) {
		Collection<Folder> res;
		res = this.folderRepository.primerNivel(actor.getId());
		return res;
	}
	public Folder findInboxByActor(final Actor actor) {
		Folder res;
		res = this.folderRepository.inbox(actor.getId());
		return res;
	}

	public Folder findNotificationBoxByActor(final Actor actor) {
		Folder res;
		res = this.folderRepository.notification(actor.getId());
		return res;
	}

	public Folder findOutBoxByActor(final Actor actor) {
		Folder res;
		res = this.folderRepository.outBox(actor.getId());
		return res;
	}

	public Folder findTrashBoxByActor(final Actor actor) {
		Folder res;
		res = this.folderRepository.trashBox(actor.getId());
		return res;
	}

	public Folder findSpamBoxByActor(final Actor actor) {
		Folder res;
		res = this.folderRepository.spamBox(actor.getId());
		return res;
	}

	public boolean isTrashFolder(Folder folder, Actor actor) {
		Folder res = this.folderRepository.trashBox(actor.getId());
		return res.equals(folder);
	}
	public boolean isNotificationFolder(Folder folder, Actor actor) {
		Folder res = this.folderRepository.notification(actor.getId());
		return res.equals(folder);
	}

	public List<Folder> findAll() {
		return this.folderRepository.findAll();
	}
	public String ubicacion(final Folder f) {
		String res = "/";
		final Folder aux = f;
		while (aux.getFatherFolder() != null)
			res = "/" + aux.getFatherFolder().getName() + res;
		return res;

	}
	public List<Folder> findRest(final Integer folderId) {
		Folder folder = this.folderRepository.findOne(folderId);
		final Collection<Folder> all = this.findByActor(folder.getActor());
		Collection<Folder> hijas = this.getChildsFolders(folder, new ArrayList<Folder>());
		all.removeAll(hijas);

		final List<Folder> res = new ArrayList<>(all);
		return res;

	}

	private Collection<Folder> getChildsFolders(Folder father, Collection<Folder> acumulador) {
		Collection<Folder> childs = father.getChildFolders();

		acumulador.add(father);
		//comprobación carpetas hijas
		if (childs.size() > 0)
			for (final Folder f : childs)
				this.getChildsFolders(f, acumulador);

		return acumulador;
	}
	public boolean validName(final Folder folder) {
		boolean res = true;
		final Actor actor = this.actorService.findByPrincipal();
		Collection<Folder> all;
		if (folder.getFatherFolder() == null) {
			all = this.primerNivel(actor);
			for (final Folder f : all)
				if (f.getName().equals(folder.getName())) {
					res = false;
					break;
				}

		} else {
			all = this.findByFatherFolder(folder.getFatherFolder().getId());
			for (final Folder f : all)
				if (f.getName().equals(folder.getName()) && f.getId() != folder.getId()) {
					res = false;
					break;
				}
		}
		return res;
	}

	public void checkConcurrence(final Folder folder) {
		if (folder.getId() != 0) {
			final Folder folderBD = this.folderRepository.findOne(folder.getId());
			Assert.isTrue(folder.getVersion() == folderBD.getVersion(), "concurrency error");
		}
	}

	protected void checkPrincipal(final Folder folder) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(folder.getActor().equals(actor));
	}

	public Folder reconstruct(final Folder folder, Integer fatherId, final BindingResult binding) {
		Actor actor = this.actorService.findByPrincipal();
		Folder result = this.create();
		Folder father;
		if (fatherId != null) {
			father = this.folderRepository.findOne(fatherId);
		} else {
			father = folder.getFatherFolder();
		}
		if ((father == null && folder.getId() == 0) || (father != null && !father.getActor().equals(actor))) {
			result = null;
		} else {
			if (folder.getId() == 0) {
				result = this.create(father);
				result.setName(folder.getName());

				if (binding != null) {
					this.validator.validate(result, binding);
				}
			} else {
				Folder folderBBDD = this.findOne(folder.getId());
				if (folderBBDD != null && folderBBDD.getActor().equals(actor)) {
					result.setActor(actor);
					result.setChildFolders(folderBBDD.getChildFolders());
					result.setFatherFolder(folder.getFatherFolder());
					result.setId(folder.getId());
					result.setVersion(folder.getVersion());
					result.setName(folder.getName());
					result.setPrivateMessages(folderBBDD.getPrivateMessages());
					result.setOfTheSystem(folderBBDD.getOfTheSystem());

					if (binding != null) {
						this.validator.validate(result, binding);
					}
				} else {
					result = null;
				}
			}
		}

		return result;
	}

	public Folder copy(Folder folder) {
		final Folder res = new Folder();
		res.setActor(folder.getActor());
		res.setChildFolders(folder.getChildFolders());
		res.setPrivateMessages(folder.getPrivateMessages());
		res.setFatherFolder(folder.getFatherFolder());
		res.setOfTheSystem(folder.getOfTheSystem());
		res.setName(folder.getName());
		res.setId(folder.getId());
		res.setVersion(folder.getVersion());
		return res;
	}
	public void flush() {
		this.folderRepository.flush();
	}
}
