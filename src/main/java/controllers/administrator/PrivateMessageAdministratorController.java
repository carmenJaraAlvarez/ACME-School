/*
 * MessageAdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PrivateMessageService;
import controllers.AbstractController;
import domain.PrivateMessage;
import forms.PrivateMessageForm;

@Controller
@RequestMapping("/privateMessage/administrator")
public class PrivateMessageAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private PrivateMessageService	privateMessageService;


	// Constructors -----------------------------------------------------------

	public PrivateMessageAdministratorController() {
		super();
	}

	// Notificacion create---------------------------------------------------------------

	@RequestMapping(value = "/notification", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(required = false, defaultValue = "0") final Integer folderId) {
		ModelAndView result;
		result = createModelAndView("privateMessage/createNotification");
		PrivateMessageForm privateMessageForm = new PrivateMessageForm();

		result.addObject("actionUri", "privateMessage/administrator/notification.do?folderId=" + folderId);
		result.addObject("cancelUri", "/folder/actor/list.do?folderId=" + folderId);
		result.addObject("esNotificacion", true);
		result.addObject("privateMessageForm", privateMessageForm);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/notification", method = RequestMethod.POST, params = "save")
	public ModelAndView notify(final PrivateMessageForm privateMessageForm, @RequestParam(required = false, defaultValue = "0") final Integer folderId, final BindingResult binding) {
		ModelAndView result;

		PrivateMessage reconstructed = this.privateMessageService.reconstructNotification(privateMessageForm, binding);

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(privateMessageForm, folderId);
		} else {
			try {
				this.privateMessageService.newNotification(reconstructed);

				result = new ModelAndView("redirect:/folder/actor/list.do?folderId=" + folderId + "&actionFolder=notification");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(privateMessageForm, folderId, "message.commit.error");
			}
		}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PrivateMessageForm privateMessageForm, Integer folderId) {
		ModelAndView result;

		result = this.createEditModelAndView(privateMessageForm, folderId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PrivateMessageForm privateMessageForm, Integer folderId, final String message) {
		final ModelAndView result;

		result = createModelAndView("privateMessage/createNotification");

		result.addObject("actionUri", "privateMessage/administrator/notification.do?folderId=" + folderId);
		result.addObject("cancelUri", "/folder/actor/list.do?folderId=" + folderId);
		result.addObject("esNotificacion", true);
		result.addObject("privateMessageForm", privateMessageForm);
		result.addObject("message", message);

		return result;
	}

}
