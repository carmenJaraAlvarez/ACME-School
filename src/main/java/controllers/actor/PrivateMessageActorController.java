/*
 * MessageActorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.actor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.PrivateMessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.PrivateMessage;
import forms.MovePrivateMessageForm;
import forms.PrivateMessageForm;

@Controller
@RequestMapping("/privateMessage/actor")
public class PrivateMessageActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService			folderService;
	@Autowired
	private PrivateMessageService	privateMessageService;
	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public PrivateMessageActorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final Integer folderId) {
		ModelAndView result;
		result = createModelAndView("privateMessage/create");
		PrivateMessageForm privateMessageForm = new PrivateMessageForm();

		result.addObject("actionUri", "privateMessage/actor/create.do?folderId=" + folderId);
		result.addObject("cancelUri", "/folder/actor/list.do?folderId=" + folderId);
		result.addObject("esNotificacion", false);
		result.addObject("privateMessageForm", privateMessageForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PrivateMessageForm privateMessageForm, @RequestParam(required = false, defaultValue = "0") final Integer folderId, final BindingResult binding) {
		ModelAndView result;

		String nombresIncorrectos = this.privateMessageService.checkSendTo(privateMessageForm.getSendTo());
		if (nombresIncorrectos.isEmpty()) {
			PrivateMessage reconstructed = this.privateMessageService.reconstruct(privateMessageForm, binding);

			if (binding.hasErrors()) {
				result = this.createEditModelAndView(privateMessageForm, null, folderId);
			} else {
				try {
					Collection<Actor> receptores = this.privateMessageService.getActorsFromSendTo(privateMessageForm.getSendTo());
					this.privateMessageService.saveNewMessage(reconstructed, receptores);

					result = new ModelAndView("redirect:/folder/actor/list.do?folderId" + folderId);
				} catch (final Throwable oops) {
					result = this.createEditModelAndView(privateMessageForm, null, folderId, "message.commit.error");
				}
			}
		} else {
			result = this.createEditModelAndView(privateMessageForm, nombresIncorrectos, folderId);
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final PrivateMessageForm privateMessageForm, String nombresIncorrectos, Integer folderId) {
		ModelAndView result;

		result = this.createEditModelAndView(privateMessageForm, nombresIncorrectos, folderId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PrivateMessageForm privateMessageForm, String nombresIncorrectos, Integer folderId, final String message) {
		final ModelAndView result;

		result = createModelAndView("privateMessage/create");

		result.addObject("actionUri", "privateMessage/actor/create.do?folderId=" + folderId);
		result.addObject("cancelUri", "/folder/actor/list.do?folderId=" + folderId);
		result.addObject("esNotificacion", false);
		if (nombresIncorrectos != null && !nombresIncorrectos.isEmpty()) {
			result.addObject("badNames", true);
			result.addObject("nombresIncorrectos", nombresIncorrectos);
		}
		result.addObject("privateMessageForm", privateMessageForm);
		result.addObject("message", message);

		return result;
	}

	// FIN Creation ---------------------------------------------------------------

	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView move(@RequestParam final int privateMessageId, @RequestParam final int folderId, @RequestParam final int newFolderId) {

		ModelAndView result;
		final PrivateMessage privateMessageToEdit = this.privateMessageService.findOne(privateMessageId);
		final Folder currentFolder = this.folderService.findOne(folderId);
		final Folder newFolder = this.folderService.findOne(newFolderId);
		final Actor actor = this.actorService.findByPrincipal();

		boolean canMoveMessage = this.privateMessageService.checkMoveMessage(privateMessageToEdit, currentFolder, newFolder);
		boolean isNotificationCurrentBox = this.folderService.isNotificationFolder(currentFolder, actor);
		boolean isNotificationNewBox = this.folderService.isNotificationFolder(newFolder, actor);

		if (isNotificationCurrentBox) {
			result = new ModelAndView("redirect:/folder/actor/list.do?actionFolder=error");
		} else if (!canMoveMessage) {
			result = new ModelAndView("redirect:/folder/actor/list.do");
		} else {

			List<Folder> foldersList = new ArrayList<Folder>();

			Folder folderSelected = newFolder;
			while (folderSelected.getFatherFolder() != null) {
				foldersList.add(folderSelected);
				folderSelected = folderSelected.getFatherFolder();
			}

			final Collection<Folder> fathers = this.folderService.primerNivel(actor);
			final Collection<Folder> childFolders = newFolder.getChildFolders();

			MovePrivateMessageForm movePrivateMessageForm = new MovePrivateMessageForm(privateMessageToEdit, currentFolder, newFolder);

			Collections.reverse(foldersList);
			result = createModelAndView("privateMessage/move");
			result.addObject("privateMessageToEdit", privateMessageToEdit);
			result.addObject("currentFolder", currentFolder);
			result.addObject("newFolder", newFolder);
			result.addObject("primaryFolder", folderSelected);
			result.addObject("fathers", fathers);
			result.addObject("childFolders", childFolders);
			result.addObject("movePrivateMessageForm", movePrivateMessageForm);
			result.addObject("foldersList", foldersList);
			result.addObject("isNotificationNewBox", isNotificationNewBox);
		}

		return result;
	}

	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "move")
	public ModelAndView change(final MovePrivateMessageForm movePrivateMessageForm) {
		ModelAndView result;
		try {
			PrivateMessage privateMessageToEdit = movePrivateMessageForm.getPrivateMessage();
			Folder currentFolder = movePrivateMessageForm.getCurrentFolder();
			Folder newFolder = movePrivateMessageForm.getNewFolder();

			boolean canMoveMessage = this.privateMessageService.checkMoveMessage(privateMessageToEdit, currentFolder, newFolder);
			Actor actor = this.actorService.findByPrincipal();
			boolean isNotificationCurrentBox = this.folderService.isNotificationFolder(currentFolder, actor);
			boolean isNotificationNewBox = this.folderService.isNotificationFolder(newFolder, actor);

			if (isNotificationCurrentBox || isNotificationNewBox) {
				result = new ModelAndView("redirect:/folder/actor/list.do?actionFolder=error");
			} else if (!canMoveMessage) {
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} else {

				if (currentFolder.getId() == newFolder.getId()) {
					result = new ModelAndView("redirect:/folder/actor/list.do?folderId=" + currentFolder.getId() + "&actionFolder=stayed");
				} else {
					this.privateMessageService.changeFolder(privateMessageToEdit, currentFolder, newFolder);

					result = new ModelAndView("redirect:/folder/actor/list.do?folderId=" + currentFolder.getId() + "&actionFolder=moved");
				}
			}

		} catch (final Throwable oops) {

			result = new ModelAndView("redirect:/folder/actor/list.do?actionFolder=error");
		}

		return result;
	}

	// Borrar mensaje permanentemente
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int privateMessageId, @RequestParam final int folderId) {
		ModelAndView result;
		final PrivateMessage privateMessageToEdit = this.privateMessageService.findOne(privateMessageId);
		final Folder currentFolder = this.folderService.findOne(folderId);
		Actor actor = this.actorService.findByPrincipal();
		Folder trashBox = this.folderService.findTrashBoxByActor(actor);
		Folder notificationBox = this.folderService.findNotificationBoxByActor(actor);
		String actionFolder = "";
		boolean canMove = this.privateMessageService.checkMoveMessage(privateMessageToEdit, currentFolder, trashBox);
		if (canMove) {
			if (currentFolder.equals(trashBox) || currentFolder.equals(notificationBox)) {
				this.privateMessageService.delete(privateMessageToEdit, currentFolder);
				actionFolder = "deleteComplete";
			} else {
				this.privateMessageService.changeFolder(privateMessageToEdit, currentFolder, trashBox);
				actionFolder = "toTrashBox";
			}
			result = new ModelAndView("redirect:/folder/actor/list.do?folderId=" + currentFolder.getId() + "&actionFolder=" + actionFolder);
		} else {
			result = new ModelAndView("redirect:/folder/actor/list.do");
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam Integer privateMessageId, @RequestParam(required = false) Integer folderId) {
		ModelAndView result = createModelAndView("folder/list");
		final Actor actor = this.actorService.findByPrincipal();
		Collection<Folder> fathers = this.folderService.primerNivel(actor);
		Folder folder;
		if (folderId == null) {
			folder = this.folderService.findInboxByActor(actor);
		} else {
			folder = this.folderService.findOne(folderId);
			if (folder == null || !folder.getActor().equals(actor)) {
				folder = this.folderService.findInboxByActor(actor);
			}
		}
		boolean isTrashBox = this.folderService.isTrashFolder(folder, actor);
		boolean isNotificationBox = this.folderService.isNotificationFolder(folder, actor);

		Collection<Folder> children = folder.getChildFolders();

		PrivateMessage privateMessage = this.privateMessageService.findOne(privateMessageId);
		if (this.privateMessageService.checkActorHasMessage(privateMessage, actor)) {
			result.addObject("displayMessage", true);
			result.addObject("privateMessageId", privateMessageId);
			result.addObject("privateMessage", privateMessage);
		} else {
			Collection<PrivateMessage> privateMessages = folder.getPrivateMessages();
			result.addObject("privateMessages", privateMessages);
		}

		List<Folder> foldersList = new ArrayList<Folder>();

		Folder folderSelected = folder;
		while (folderSelected.getFatherFolder() != null) {
			foldersList.add(folderSelected);
			folderSelected = folderSelected.getFatherFolder();
		}
		Folder primaryFolder = folderSelected;
		Collections.reverse(foldersList);
		result.addObject("fathers", fathers);
		result.addObject("children", children);
		result.addObject("foldersList", foldersList);
		result.addObject("primaryFolder", primaryFolder);
		result.addObject("isTrashBox", isTrashBox);
		result.addObject("isNotificationBox", isNotificationBox);
		result.addObject("folder", folder);

		return result;
	}

}
