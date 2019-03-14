
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PrivateMessageRepository;
import domain.Actor;
import domain.Folder;
import domain.PrivateMessage;
import domain.Student;
import domain.Teacher;
import forms.PrivateMessageForm;

@Service
@Transactional
public class PrivateMessageService {

	@Autowired
	private PrivateMessageRepository	privateMessageRepository;

	//´-----------------------------------
	@Autowired
	private ActorService				actorService;
	@Autowired
	private TeacherService				teacherService;
	@Autowired
	private GlobalService				globalService;
	@Autowired
	private FolderService				folderService;
	@Autowired
	private Validator					validator;
	@Autowired
	private MessageSource				messageSource;


	public PrivateMessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public PrivateMessage create() {
		final PrivateMessage res = new PrivateMessage();
		final Date ahora = new Date();
		res.setSendMoment(ahora);
		final Actor sender = this.actorService.findByPrincipal();
		res.setActorSender(sender);
		return res;
	}
	public PrivateMessage markNotification(Student student, Locale locale) {
		final PrivateMessage res = create();
		Actor logged = this.actorService.findByPrincipal();
		String subject = this.messageSource.getMessage("mark.newmark", null, locale);
		String body;
		String teacher = this.messageSource.getMessage("mark.notification.teacher", null, locale);
		String evaluate = this.messageSource.getMessage("mark.notification.evaluate", null, locale);
		String seeMark = this.messageSource.getMessage("mark.notification.see.mark", null, locale);
		String linkMark = this.messageSource.getMessage("mark.link", null, locale);
		if (locale.getLanguage().equals(new Locale("en").getLanguage())) {
			body = teacher + " " + logged.getName() + " " + evaluate + " " + student.getName() + ". <br/><br/>" + " <a href='mark/parent/list.do?studentId=" + student.getId() + "'>" + linkMark + "</a> " + seeMark;
		} else {
			body = teacher + " " + logged.getName() + " " + evaluate + " " + student.getName() + ". <br/><br/>" + seeMark + " <a href='mark/parent/list.do?studentId=" + student.getId() + "'>" + linkMark + "</a> ";
		}

		res.setPriority("LOW");
		res.setBody(body);
		res.setSubject(subject);
		return res;
	}
	public Collection<PrivateMessage> findAll() {
		return this.privateMessageRepository.findAll();
	}

	public PrivateMessage findOne(final int id) {
		return this.privateMessageRepository.findOne(id);
	}

	//Método para comprobar si el mensaje privado está en la carpeta de origen, y si ambas carpetas son del actor logueado.
	public boolean checkMoveMessage(final PrivateMessage privateMessageToEdit, final Folder currentFolder, final Folder newFolder) {
		boolean result = false;
		Actor logged = this.actorService.findByPrincipal();
		if (currentFolder != null && newFolder != null && currentFolder.getActor().equals(logged) && newFolder.getActor().equals(logged) && currentFolder.getPrivateMessages().contains(privateMessageToEdit)) {
			result = true;
		}
		return result;
	}

	//Método para comprobar si el mensaje privado que se muestra es del actor.
	public boolean checkActorHasMessage(final PrivateMessage privateMessage, final Actor actor) {
		boolean result = false;
		if (privateMessage != null) {
			for (Folder folder : actor.getFolders()) {
				if (folder.getPrivateMessages().contains(privateMessage)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	public void changeFolder(final PrivateMessage privateMessageToEdit, final Folder currentFolder, final Folder newFolder) {

		currentFolder.getPrivateMessages().remove(privateMessageToEdit);
		newFolder.getPrivateMessages().add(privateMessageToEdit);
		this.folderService.save(currentFolder);
		this.folderService.save(newFolder);
	}

	public PrivateMessage saveNewMessage(final PrivateMessage privateMessage, Collection<Actor> actorReceivers) {
		PrivateMessage savedMessage = this.privateMessageRepository.save(privateMessage);
		for (Actor a : actorReceivers) {
			a.getMessagesReceived().add(savedMessage);
			this.actorService.save(a);
		}
		Actor logged = this.actorService.findByPrincipal();
		logged.getMessagesSent().add(savedMessage);
		this.actorService.save(logged);

		//Seleccionamos todas las carpetas en las que va a estar ese mensaje.
		final List<Folder> folders = new ArrayList<>();
		//OutBox del usuario que lo envía:
		if (!actorReceivers.contains(logged)) {
			Folder outBox = this.folderService.findOutBoxByActor(logged);
			folders.add(outBox);
		}
		//Comprobamos si el mensaje se considera Spam:
		boolean isSpam = isSpamMessage(privateMessage.getBody());
		if (actorReceivers.size() > 0) {
			if (isSpam) {
				for (Actor receptor : actorReceivers) {
					folders.add(this.folderService.findSpamBoxByActor(receptor));
				}
			} else {
				for (Actor receptor : actorReceivers) {
					folders.add(this.folderService.findInboxByActor(receptor));
				}
			}
		}

		// Guardamos todas las carpetas seleccionadas
		for (Folder f : folders) {
			f.getPrivateMessages().add(savedMessage);
			this.folderService.save(f);
		}
		return savedMessage;
	}

	public PrivateMessage save(final PrivateMessage e) {
		return this.privateMessageRepository.save(e);
	}
	public PrivateMessage newNotification(final PrivateMessage privateMessage) {
		PrivateMessage savedMessage = this.privateMessageRepository.save(privateMessage);
		Collection<Actor> actorReceivers = this.actorService.findAll();
		Folder folderActor;
		for (Actor a : actorReceivers) {
			//Añadimos el mensaje al actor
			a.getMessagesReceived().add(savedMessage);
			folderActor = this.folderService.findNotificationBoxByActor(a);
			this.actorService.save(a);
			// Guardamos el mensaje en la carpeta del actor
			folderActor.getPrivateMessages().add(savedMessage);
			this.folderService.save(folderActor);
		}

		return savedMessage;
	}

	//Método para guardar los mensajes de invitación al profesor
	public PrivateMessage saveNotificationToTeacher(final PrivateMessage privateMessage, Collection<Actor> teachers) {
		PrivateMessage savedMessage = this.privateMessageRepository.save(privateMessage);
		Folder folderActor;
		for (Actor a : teachers) {
			//Añadimos el mensaje al actor
			a.getMessagesReceived().add(savedMessage);
			folderActor = this.folderService.findNotificationBoxByActor(a);
			this.actorService.save(a);
			// Guardamos el mensaje en la carpeta del actor
			folderActor.getPrivateMessages().add(savedMessage);
			this.folderService.save(folderActor);
		}

		return savedMessage;
	}

	//Método para guardar las notificaciones de aviso por palabras malsonantes de los estudiantes
	public PrivateMessage saveNotificationToParents(final PrivateMessage privateMessage, Collection<Actor> parents) {
		PrivateMessage savedMessage = this.privateMessageRepository.save(privateMessage);
		Folder folderActor;
		for (Actor a : parents) {
			//Añadimos el mensaje al actor
			a.getMessagesReceived().add(savedMessage);
			folderActor = this.folderService.findNotificationBoxByActor(a);
			this.actorService.save(a);
			// Guardamos el mensaje en la carpeta del actor
			folderActor.getPrivateMessages().add(savedMessage);
			this.folderService.save(folderActor);
		}

		return savedMessage;
	}

	public void delete(final PrivateMessage privateMessage, final Folder currentFolder) {
		currentFolder.getPrivateMessages().remove(privateMessage);
	}

	//Comprueba que todos los nombres escritos sean actores del sistema, y en caso negativo devuelve un texto con los nombres que no ha reconocido.
	public String checkSendTo(final String sendTo) {
		String res = "";

		Actor actor;
		final String parts[];
		parts = sendTo.split(",");
		if (sendTo.length() > 0 && parts.length == 0) {
			res = sendTo;
		} else {
			for (String s : parts) {
				if (!s.trim().isEmpty()) {
					actor = this.actorService.findByUserName(s.trim());
					if (actor == null) {
						if (res.isEmpty()) {
							res = s;
						} else {
							res += ", " + s.trim();
						}
					}
				}
			}
		}

		return res;
	}

	//Comprueba que todos los nombres escritos sean profesores del sistema, y en caso negativo devuelve un texto con los nombres que no ha reconocido.
	public String checkSendToTeacher(final String sendTo) {
		String res = "";

		Teacher teacher;
		final String parts[];
		parts = sendTo.split(",");
		if (sendTo.length() > 0 && parts.length == 0) {
			res = sendTo;
		} else {
			for (String s : parts) {
				if (!s.trim().isEmpty()) {
					teacher = this.teacherService.findByUserName(s.trim());
					if (teacher == null) {
						if (res.isEmpty()) {
							res = s;
						} else {
							res += ", " + s.trim();
						}
					}
				}
			}
		}

		return res;
	}

	public PrivateMessage reconstruct(final PrivateMessageForm privateMessageForm, final BindingResult binding) {
		//Inicializamos el mensaje con actor y fecha.
		PrivateMessage result = this.create();

		//Añadimos asunto, body y priority del formulario:
		result.setSubject(privateMessageForm.getSubject());
		result.setBody(privateMessageForm.getBody());
		result.setPriority(privateMessageForm.getPriority());

		//Validamos (comprobación del binding para los test JUnit)
		if (binding != null) {
			this.validator.validate(result, binding);
			this.validator.validate(privateMessageForm, binding);
		}

		return result;
	}

	//Reconstruct para notificaciones
	public PrivateMessage reconstructNotification(final PrivateMessageForm privateMessageForm, final BindingResult binding) {
		//Inicializamos el mensaje con actor y fecha.
		PrivateMessage result = this.create();

		//Añadimos asunto, body y priority del formulario:
		result.setSubject(privateMessageForm.getSubject());
		result.setBody(privateMessageForm.getBody());
		result.setPriority(privateMessageForm.getPriority());

		//Validamos (comprobación del binding para los test JUnit)
		if (binding != null) {
			this.validator.validate(result, binding);
		}

		return result;
	}

	//Método auxiliar para reconstruct
	public Collection<Actor> getActorsFromSendTo(final String sendTo) {
		Collection<Actor> actores = new ArrayList<Actor>();
		Actor actor;
		final String parts[];
		parts = sendTo.split(",");

		for (String s : parts) {
			if (!s.trim().isEmpty()) {
				actor = this.actorService.findByUserName(s.trim());
				actores.add(actor);
			}
		}

		return actores;
	}

	//Método auxiliar para guardar mensaje
	private boolean isSpamMessage(final String body) {
		List<String> spamwords = new ArrayList<String>(this.globalService.getGlobal().getDangerousWords());
		boolean isSpam = false;

		for (final String s : spamwords) {
			if (body.contains(s)) {
				isSpam = true;
				break;
			}
		}
		return isSpam;
	}
	public void flush() {
		this.privateMessageRepository.flush();

	}
}
