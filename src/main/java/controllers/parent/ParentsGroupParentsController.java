
package controllers.parent;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import services.ClassGroupService;
import services.LevelService;
import services.MessageService;
import services.ParentService;
import services.ParentsGroupService;
import services.PrivateMessageService;
import services.SchoolService;
import controllers.AbstractController;
import domain.Actor;
import domain.ClassGroup;
import domain.FileUpload;
import domain.Level;
import domain.Message;
import domain.Parent;
import domain.ParentsGroup;
import domain.PrivateMessage;
import domain.School;
import forms.CreateGroupForm;
import forms.CreateGroupFormClass;
import forms.MessageForm;
import forms.ParentsGroupEditRawForm;
import forms.PrivateMessageForm;

@Controller
@RequestMapping("/parentsGroup/parent")
public class ParentsGroupParentsController extends AbstractController {

	@Autowired
	private ParentsGroupService		parentsGroupService;
	@Autowired
	private ParentService			parentService;
	@Autowired
	private ClassGroupService		classGroupService;
	@Autowired
	private MessageService			messageService;
	@Autowired
	private SchoolService			schoolService;
	@Autowired
	private LevelService			levelService;
	@Autowired
	private PrivateMessageService	privateMessageService;


	// Panic handler ----------------------------------------------------------

	public ParentsGroupParentsController() {
		super();
	}

	//  ---------------------------------------------------------------	
	@RequestMapping("/mylist")
	public ModelAndView list() {
		final Parent parent = this.parentService.findByPrincipal();
		final Collection<ParentsGroup> res = this.parentService.getAllGroups(parent);
		final ModelAndView result = createModelAndView("parentsGroup/parent/mylist");
		result.addObject("parentsGroups", res);
		result.addObject("logueadoId", parent.getId());
		result.addObject("myList", true);
		result.addObject("uri", "parentsGroup/parent/mylist.do");
		return result;
	}

	//M�todos para realizar el display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int parentsGroupId, @RequestParam(required = false, defaultValue = "10") final Integer messagesNumber, @RequestParam(required = false) final String actionParentsGroup) {
		ModelAndView result = this.createModelAndView("parentsGroup/parent/display");
		try {
			final MessageForm messageForm = new MessageForm();
			final Integer incremento = 4;
			result = this.createDisplayModelAndView(messageForm, parentsGroupId, messagesNumber, actionParentsGroup, incremento);
		} catch (final Throwable oops) {
			result.addObject("errorOccurred", true);
		}

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.POST, params = "save")
	public ModelAndView addMessage(final MessageForm messageForm, @RequestParam("file") final MultipartFile file, @RequestParam final int parentsGroupId, @RequestParam(required = false, defaultValue = "10") final Integer messagesNumber, @RequestParam(
		required = false) final String actionParentsGroup, final BindingResult binding) {
		ModelAndView result;
		final Integer incremento = 0;
		if (file.getSize() > 10000000)
			result = this.createDisplayModelAndView(messageForm, parentsGroupId, messagesNumber, actionParentsGroup, incremento, "parentsGroup.message.tooBigFile");
		else {
			final Message reconstructedMessage = this.messageService.reconstruct(messageForm, file, binding);
			if (reconstructedMessage == null)
				result = this.createDisplayModelAndView(messageForm, parentsGroupId, messagesNumber, actionParentsGroup, incremento, "parentsGroup.commit.error");
			else if (binding.hasErrors())
				result = this.createDisplayModelAndView(messageForm, parentsGroupId, messagesNumber, actionParentsGroup, incremento);
			else
				try {
					this.messageService.save(reconstructedMessage, parentsGroupId);
					result = new ModelAndView("redirect:display.do?parentsGroupId=" + parentsGroupId + "&messagesNumber=" + messagesNumber + "#FormularioChat");

				} catch (final Throwable oops) {
					result = this.createDisplayModelAndView(messageForm, parentsGroupId, messagesNumber, actionParentsGroup, incremento, "parentsGroup.commit.error");
				}
		}
		return result;
	}

	protected ModelAndView createDisplayModelAndView(final MessageForm messageForm, final int parentsGroupId, final Integer messagesNumber, final String actionParentsGroup, final Integer incremento) {
		ModelAndView result;
		result = this.createDisplayModelAndView(messageForm, parentsGroupId, messagesNumber, actionParentsGroup, incremento, null);
		return result;

	}

	protected ModelAndView createDisplayModelAndView(final MessageForm messageForm, final int parentsGroupId, final Integer messagesNumber, final String actionParentsGroup, final Integer incremento, final String message) {
		ModelAndView result = this.createModelAndView("parentsGroup/parent/display");
		final ParentsGroup parentsGroup = this.parentsGroupService.findOneToDisplay(parentsGroupId);
		if (this.parentsGroupService.checkParentInGroup(parentsGroup)) {
			final Collection<Message> messages = this.parentsGroupService.findGroupMessages(parentsGroupId, messagesNumber);
			final boolean moreMessages = parentsGroup.getMessages().size() > messagesNumber;

			if (actionParentsGroup != null) {
				if (actionParentsGroup.equals("invited")) {
					result.addObject("invited", true);
				}
			}
			result.addObject("requestURI", "parentsGroup/parent/display.do?parentsGroupId=" + parentsGroupId);
			result.addObject("parentsGroup", parentsGroup);
			result.addObject("messages", messages);
			result.addObject("message", message);
			result.addObject("moreMessages", moreMessages);
			result.addObject("messageForm", messageForm);
			result.addObject("messagesNumber", messagesNumber + incremento);
			result.addObject("incremento", incremento);
			result.addObject("parentLogged", this.parentService.findByPrincipal());
		} else
			result = new ModelAndView("redirect:/inscription/parent/createInscription.do?parentsGroupId=" + parentsGroupId);
		return result;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void downloadDocument(@RequestParam final int messageId, @RequestParam final int parentsGroupId, final HttpServletResponse response) throws IOException {
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		Assert.isTrue(this.parentsGroupService.checkParentInGroup(parentsGroup), "");
		final Message message = this.messageService.findOne(messageId);
		final FileUpload document = message.getAttachment();
		response.setContentType(document.getType());
		response.setContentLength(document.getContent().length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"");

		FileCopyUtils.copy(document.getContent(), response.getOutputStream());

	}

	//Fin del display

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int parentsGroupId) {
		ModelAndView result;
		final ParentsGroup parentsGroup = this.parentsGroupService.findOneToEdit(parentsGroupId);
		final ParentsGroupEditRawForm parentsGroupEditRawForm = new ParentsGroupEditRawForm(parentsGroup);
		result = this.createEditModelAndViewRaw(parentsGroupEditRawForm);
		final String requestURI = "parentsGroup/parent/edit";
		result.addObject(requestURI);
		result.addObject(parentsGroupEditRawForm);
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "done")
	public ModelAndView step1(final ParentsGroup parentsgroup, final BindingResult binding) {
		ModelAndView result = null;
		if (parentsgroup.getClassGroup() == null)
			result = this.createModelAndView(parentsgroup, "parentsGroup.select.error");
		else
			try {
				final ParentsGroup parentsgroupWithClass = this.parentsGroupService.create();
				parentsgroupWithClass.setClassGroup(parentsgroup.getClassGroup());
				result = this.createEditModelAndView(parentsgroupWithClass);
			} catch (final Throwable oops) {
				result = this.createModelAndView(parentsgroup, "parentsGroup.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ParentsGroupEditRawForm parentsGroupEditRawForm, final BindingResult binding) {
		ModelAndView result;
		if (this.parentsGroupService.checkConcurrency(parentsGroupEditRawForm)) {
			result = this.createEditModelAndViewRaw(parentsGroupEditRawForm, "parentsGroup.concurrency.error");
		} else {
			if (binding.hasErrors())
				result = this.createEditModelAndViewRaw(parentsGroupEditRawForm, "parentsGroup.commit.error");
			else
				try {
					final ParentsGroup parentsgroup = this.parentsGroupService.reconstructEdit(parentsGroupEditRawForm, binding);
					this.parentsGroupService.save(parentsgroup);
					result = new ModelAndView("redirect:mylist.do");
				} catch (final Throwable oops) {
					result = this.createEditModelAndViewRaw(parentsGroupEditRawForm, "parentsGroup.commit.error");

				}
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final ParentsGroup parentsgroup) {
		ModelAndView result;
		result = this.createEditModelAndView(parentsgroup, null);
		return result;

	}

	protected ModelAndView createEditModelAndView(final ParentsGroup parentsgroup, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/edit");
		result.addObject("parentsGroup", parentsgroup);
		result.addObject("message", message);

		final Collection<ClassGroup> all = this.classGroupService.findAll();
		result.addObject("all", all);

		return result;
	}
	protected ModelAndView createEditModelAndViewRaw(final ParentsGroupEditRawForm parentsGroupEditRawForm) {
		ModelAndView result;
		result = this.createEditModelAndViewRaw(parentsGroupEditRawForm, null);
		return result;

	}

	protected ModelAndView createEditModelAndViewRaw(final ParentsGroupEditRawForm pgrf, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/edit");
		result.addObject("parentsGroupEditRawForm", pgrf);
		result.addObject("message", message);
		return result;
	}
	protected ModelAndView createModelAndView(final ParentsGroup parentsgroup) {
		ModelAndView result;
		result = this.createModelAndView(parentsgroup, null);
		return result;

	}

	protected ModelAndView createModelAndView(final ParentsGroup parentsgroup, final String messageCode) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/create");
		result.addObject("parentsGroup", parentsgroup);
		result.addObject("message", messageCode);
		final Collection<ClassGroup> all = this.classGroupService.findAll();
		result.addObject("all", all);

		return result;
	}
	//************creacion******************************

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		final CreateGroupForm createGroupForm = new CreateGroupForm();
		result = this.createEditModelAndView1(createGroupForm);
		final String uriCancel = "parentsGroup/parent/mylist.do";
		final String uri = "parentsGroup/parent/secondStep.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);
		return result;
	}

	@RequestMapping(value = "/secondStep", method = RequestMethod.POST, params = "done")
	public ModelAndView step2(final CreateGroupForm createGroupForm, final BindingResult binding) {
		ModelAndView result = null;
		if (createGroupForm.getSchool() == null)
			result = this.createEditModelAndView1(createGroupForm, "parentsGroup.select.error");
		else
			try {
				result = this.createEditModelAndView2(createGroupForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView1(createGroupForm, "parentsGroup.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/thirdStep", method = RequestMethod.POST, params = "done")
	public ModelAndView step3(final CreateGroupForm createGroupForm, final BindingResult binding) {
		ModelAndView result = null;
		if (createGroupForm.getLevel() == null)
			result = this.createEditModelAndView2(createGroupForm, "parentsGroup.select2.error");
		else
			try {
				result = this.createEditModelAndView3(createGroupForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView2(createGroupForm, "parentsGroup.commit.error");
			}

		return result;
	}
	@RequestMapping(value = "/fourthStep", method = RequestMethod.POST, params = "done")
	public ModelAndView step4(final CreateGroupForm createGroupForm, final BindingResult binding) {
		ModelAndView result = null;
		if (createGroupForm.getClassGroup() == null)
			result = this.createEditModelAndView3(createGroupForm, "parentsGroup.select3.error");
		else
			try {
				result = this.createEditModelAndView4(createGroupForm);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView3(createGroupForm, "parentsGroup.commit.error");
			}

		return result;
	}
	@RequestMapping("/final")
	public ModelAndView backClass(@RequestParam final int levelId) {
		ModelAndView result;
		final CreateGroupForm createGroupForm = new CreateGroupForm();
		try {

			final Level level = this.levelService.findOne(levelId);
			createGroupForm.setLevel(level);
			createGroupForm.setSchool(level.getSchool());
			result = this.createEditModelAndView3(createGroupForm);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView3(createGroupForm, "parentsGroup.commit.error");
		}
		return result;
	}
	@RequestMapping(value = "/final", method = RequestMethod.POST, params = "save")
	public ModelAndView step5(final ParentsGroup parentsGroup, final BindingResult binding) {
		ModelAndView result = null;
		final ParentsGroup reconst = this.parentsGroupService.reconstruct(parentsGroup, binding);
		if (binding.hasErrors())
			result = this.createEditModelAndView5(parentsGroup);
		else
			try {
				result = new ModelAndView("redirect:mylist.do");
				this.parentsGroupService.save(reconst);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView5(parentsGroup, "parentsGroup.commit.error");

			}

		return result;
	}
	@RequestMapping(value = "/createClass", method = RequestMethod.POST, params = "done")
	public ModelAndView saveClass(@Valid final CreateGroupFormClass createGroupFormClass, final BindingResult binding) {
		ModelAndView result = null;
		if (binding.hasErrors())
			result = this.createEditModelAndViewClass(createGroupFormClass);
		else
			try {
				final ClassGroup newClass = this.classGroupService.create(createGroupFormClass.getLevel().getId());
				newClass.setName(createGroupFormClass.getClassName());

				if (this.classGroupService.checkname(newClass)) {
					result = this.createEditModelAndViewClass(createGroupFormClass, "classGroup.name.error");
				} else {
					this.classGroupService.save(newClass);
					result = new ModelAndView("redirect:final.do?levelId=" + createGroupFormClass.getLevel().getId());
				}
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewClass(createGroupFormClass, "parentsGroup.commit.error");

			}

		return result;
	}

	@RequestMapping(value = "/createClass", method = RequestMethod.GET)
	public ModelAndView createClass(@RequestParam final int levelId) {
		ModelAndView result;
		final CreateGroupFormClass createGroupFormClass = new CreateGroupFormClass();
		final Level level = this.levelService.findOne(levelId);
		createGroupFormClass.setLevel(level);
		result = this.createEditModelAndViewClass(createGroupFormClass);
		final String uriCancel = "parentsGroup/parent/create.do";
		final String uri = "parentsGroup/parent/createClass.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);
		return result;
	}
	//--------------------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView1(final CreateGroupForm createGroupForm) {
		ModelAndView result;
		result = this.createEditModelAndView1(createGroupForm, null);
		return result;

	}
	protected ModelAndView createEditModelAndView1(final CreateGroupForm createGroupForm, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/create");

		result.addObject("createGroupForm", createGroupForm);
		result.addObject("message", message);
		final Collection<School> all = this.schoolService.findAll();
		result.addObject("all", all);
		final String uriCancel = "parentsGroup/parent/mylist.do";
		final String uri = "parentsGroup/parent/secondStep.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);

		return result;
	}
	protected ModelAndView createEditModelAndView2(final CreateGroupForm createGroupForm) {
		ModelAndView result;
		result = this.createEditModelAndView2(createGroupForm, null);
		return result;

	}
	protected ModelAndView createEditModelAndView2(final CreateGroupForm createGroupForm, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/createSecondStep");

		result.addObject("createGroupForm", createGroupForm);
		result.addObject("message", message);
		final Collection<Level> all = createGroupForm.getSchool().getLevels();
		result.addObject("all", all);
		final String uriCancel = "parentsGroup/parent/create.do";
		final String uri = "parentsGroup/parent/thirdStep.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);

		return result;
	}

	protected ModelAndView createEditModelAndView3(final CreateGroupForm createGroupForm) {
		ModelAndView result;
		result = this.createEditModelAndView3(createGroupForm, null);
		return result;

	}
	protected ModelAndView createEditModelAndView3(final CreateGroupForm createGroupForm, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/createThirdStep");

		result.addObject("createGroupForm", createGroupForm);
		result.addObject("message", message);
		final Collection<ClassGroup> all = createGroupForm.getLevel().getClassGroups();
		result.addObject("all", all);
		final String uriCancel = "parentsGroup/parent/create.do";
		final String uri = "parentsGroup/parent/fourthStep.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);

		return result;
	}
	protected ModelAndView createEditModelAndView4(final CreateGroupForm createGroupForm) {
		ModelAndView result;
		result = this.createEditModelAndView4(createGroupForm, null);
		return result;

	}
	protected ModelAndView createEditModelAndView4(final CreateGroupForm createGroupForm, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/createFourthStep");//********
		final ParentsGroup parentsGroup = this.parentsGroupService.create();
		parentsGroup.setClassGroup(createGroupForm.getClassGroup());
		result.addObject("parentsGroup", parentsGroup);
		result.addObject("message", message);
		final String uriCancel = "parentsGroup/parent/create.do";
		final String uri = "parentsGroup/parent/final.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);

		return result;
	}
	protected ModelAndView createEditModelAndView5(final ParentsGroup parentsGroup) {
		ModelAndView result;
		result = this.createEditModelAndView5(parentsGroup, null);
		return result;

	}
	protected ModelAndView createEditModelAndView5(final ParentsGroup parentsGroup, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/createFourthStep");
		result.addObject("parentsGroup", parentsGroup);
		result.addObject("message", message);
		final String uriCancel = "parentsGroup/parent/create.do";
		final String uri = "parentsGroup/parent/final.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);

		return result;
	}
	protected ModelAndView createEditModelAndViewClass(final CreateGroupFormClass createGroupFormClass) {
		ModelAndView result;
		result = this.createEditModelAndViewClass(createGroupFormClass, null);
		return result;

	}
	protected ModelAndView createEditModelAndViewClass(final CreateGroupFormClass createGroupFormClass, final String message) {
		ModelAndView result;
		result = super.createModelAndView("parentsGroup/parent/createClass");

		result.addObject("createGroupFormClass", createGroupFormClass);
		result.addObject("message", message);
		final String uriCancel = "parentsGroup/parent/create.do";
		final String uri = "parentsGroup/parent/createClass.do";
		result.addObject("uri", uri);
		result.addObject("uriCancel", uriCancel);

		return result;
	}

	//--------------------------------------------------------------------------------------------
	//		Invitar a profesor al grupo
	//--------------------------------------------------------------------------------------------

	@RequestMapping(value = "/invite/teacher", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final Integer parentsGroupId, @RequestParam(required = false, defaultValue = "true") final Boolean english) {
		ModelAndView result;
		result = createModelAndView("privateMessage/invite/teacher");
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		if (parentsGroup != null && this.parentsGroupService.checkParentInGroup(parentsGroup)) {
			PrivateMessageForm privateMessageForm = new PrivateMessageForm(parentsGroup, english);
			result.addObject("actionUri", "parentsGroup/parent/invite/teacher.do?parentsGroupId=" + parentsGroupId);
			result.addObject("cancelUri", "parentsGroup/parent/display.do?parentsGroupId=" + parentsGroupId);
			result.addObject("esInvitacion", true);
			result.addObject("privateMessageForm", privateMessageForm);
		} else {
			result = new ModelAndView("redirect:/");
		}

		return result;
	}

	@RequestMapping(value = "/invite/teacher", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final PrivateMessageForm privateMessageForm, @RequestParam final Integer parentsGroupId, final BindingResult binding) {
		ModelAndView result;
		ParentsGroup parentsGroup = this.parentsGroupService.findOne(parentsGroupId);
		if (parentsGroup != null && this.parentsGroupService.checkParentInGroup(parentsGroup)) {
			String nombresIncorrectos = this.privateMessageService.checkSendToTeacher(privateMessageForm.getSendTo());
			if (nombresIncorrectos.isEmpty()) {
				PrivateMessage reconstructed = this.privateMessageService.reconstruct(privateMessageForm, binding);

				if (binding.hasErrors()) {
					result = this.createEditModelAndView(privateMessageForm, null, parentsGroupId);
				} else {
					try {
						Collection<Actor> teachers = this.privateMessageService.getActorsFromSendTo(privateMessageForm.getSendTo());
						this.privateMessageService.saveNotificationToTeacher(reconstructed, teachers);

						result = new ModelAndView("redirect:/parentsGroup/parent/display.do?parentsGroupId=" + parentsGroupId + "&actionParentsGroup=invited");
					} catch (final Throwable oops) {
						result = this.createEditModelAndView(privateMessageForm, null, parentsGroupId, "message.commit.error");
					}
				}
			} else {
				result = this.createEditModelAndView(privateMessageForm, nombresIncorrectos, parentsGroupId);
			}
		} else {
			result = new ModelAndView("redirect:/");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final PrivateMessageForm privateMessageForm, String nombresIncorrectos, Integer parentsGroupId) {
		ModelAndView result;

		result = this.createEditModelAndView(privateMessageForm, nombresIncorrectos, parentsGroupId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final PrivateMessageForm privateMessageForm, String nombresIncorrectos, Integer parentsGroupId, final String message) {
		final ModelAndView result;

		result = createModelAndView("privateMessage/invite/teacher");

		result.addObject("actionUri", "parentsGroup/parent/invite/teacher.do?parentsGroupId=" + parentsGroupId);
		result.addObject("cancelUri", "parentsGroup/parent/display.do?parentsGroupId=" + parentsGroupId);
		result.addObject("esInvitacion", true);
		if (nombresIncorrectos != null && !nombresIncorrectos.isEmpty()) {
			result.addObject("badNames", true);
			result.addObject("nombresIncorrectos", nombresIncorrectos);
		}
		result.addObject("privateMessageForm", privateMessageForm);
		result.addObject("message", message);

		return result;
	}
}
