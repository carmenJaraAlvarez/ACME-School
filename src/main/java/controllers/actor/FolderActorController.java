/*
 * sponsorshipCustomerController.java
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
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.PrivateMessage;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public FolderActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) Integer folderId, @RequestParam(required = false) String actionFolder) {
		ModelAndView result;
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

		Collection<PrivateMessage> privateMessages = folder.getPrivateMessages();
		List<PrivateMessage> privateMessagesList = new ArrayList<PrivateMessage>(privateMessages);
		List<Folder> foldersList = new ArrayList<Folder>();

		Folder folderSelected = folder;
		while (folderSelected.getFatherFolder() != null) {
			foldersList.add(folderSelected);
			folderSelected = folderSelected.getFatherFolder();
		}
		Folder primaryFolder = folderSelected;
		Collections.reverse(foldersList);
		Collections.reverse(privateMessagesList);
		result = createModelAndView("folder/list");
		result.addObject("fathers", fathers);
		result.addObject("children", children);
		result.addObject("foldersList", foldersList);
		result.addObject("primaryFolder", primaryFolder);
		result.addObject("privateMessages", privateMessagesList);
		result.addObject("isTrashBox", isTrashBox);
		result.addObject("isNotificationBox", isNotificationBox);
		result.addObject("folder", folder);
		result.addObject("low", "LOW");
		result.addObject("high", "HIGH");
		result.addObject("neutral", "NEUTRAL");

		if (actionFolder != null) {
			if (actionFolder.equals("deleteComplete")) {
				result.addObject("delete", true);
			} else if (actionFolder.equals("toTrashBox")) {
				result.addObject("toTrashBox", true);
			} else if (actionFolder.equals("moved")) {
				result.addObject("moved", true);
			} else if (actionFolder.equals("stayed")) {
				result.addObject("stayed", true);
			} else if (actionFolder.equals("folderDeleted")) {
				result.addObject("folderDeleted", true);
			} else if (actionFolder.equals("notification")) {
				result.addObject("newNotification", true);
			} else if (actionFolder.equals("error")) {
				result.addObject("folderError", true);
			}
		}

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "fatherId")
	public ModelAndView create(@RequestParam final int fatherId) {
		ModelAndView result = createModelAndView("folder/create");
		Folder father = this.folderService.findOne(fatherId);
		if (father == null) {
			result = new ModelAndView("redirect:list.do");
		} else {
			Folder folder = this.folderService.create();

			result.addObject("uri", "folder/actor/edit.do?fatherId=" + fatherId);
			result.addObject("cancelUri", "folder/actor/list.do?folderId=" + fatherId);
			result.addObject("folder", folder);
		}
		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "folderId")
	public ModelAndView edit(@RequestParam final int folderId) {
		ModelAndView result = createModelAndView("folder/edit");
		final Folder folder = this.folderService.findOne(folderId);
		if (folder == null || folder.getOfTheSystem() || !folder.getActor().equals(this.actorService.findByPrincipal())) {
			result = new ModelAndView("redirect:list.do");
		} else {
			final List<Folder> all = this.folderService.findRest(folder.getId());
			result.addObject("all", all);
			result.addObject("folder", folder);
			result.addObject("uri", "folder/actor/edit.do");
			result.addObject("cancelUri", "folder/actor/list.do?folderId=" + folderId);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Folder folder, @RequestParam(required = false) Integer fatherId, final BindingResult binding) {
		ModelAndView result;
		Folder reconstructed = this.folderService.reconstruct(folder, fatherId, binding);
		if (reconstructed == null) {
			result = new ModelAndView("redirect:list.do");
		} else if (!this.folderService.validName(reconstructed))
			result = this.createEditModelAndView(folder, fatherId, "folder.errorname");
		else if (binding.hasErrors())
			result = this.createEditModelAndView(folder, fatherId);
		else
			try {
				Folder saved = this.folderService.save(reconstructed);
				result = new ModelAndView("redirect:list.do?folderId=" + saved.getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, fatherId, "folder.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Folder folder, final BindingResult binding) {
		ModelAndView result;
		try {
			Folder folderBBDD = this.folderService.findOne(folder.getId());
			boolean isFolderDeleted = false;
			String actionFolder = "";
			if (folderBBDD != null) {
				isFolderDeleted = this.folderService.delete(folderBBDD);
			}
			if (isFolderDeleted) {
				actionFolder = "folderDeleted";
			}
			result = new ModelAndView("redirect:list.do?actionFolder=" + actionFolder);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, folder.getFatherFolder().getId(), "folder.commit.error");
		}

		return result;
	}
	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Folder folder, Integer fatherId) {
		ModelAndView result;

		result = this.createEditModelAndView(folder, fatherId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, Integer fatherId, final String message) {
		final ModelAndView result;
		if (folder.getId() == 0) {
			result = createModelAndView("folder/create");
		} else {
			result = createModelAndView("folder/edit");
		}

		if (folder.getOfTheSystem()) {
			result.addObject("folderError", true);
		} else {
			if (folder.getId() == 0) {
				result.addObject("uri", "folder/actor/edit.do?fatherId=" + fatherId);
				result.addObject("cancelUri", "folder/actor/list.do?folderId=" + fatherId);
			} else {
				final List<Folder> all = this.folderService.findRest(folder.getId());
				result.addObject("all", all);
				result.addObject("uri", "folder/actor/edit.do");
				result.addObject("cancelUri", "folder/actor/list.do?folderId=" + folder.getId());
			}
		}

		result.addObject("folder", folder);
		result.addObject("message", message);

		return result;
	}

}
