
package controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.AgentService;
import services.ClassGroupService;
import services.FolderService;
import services.ParentService;
import services.SchoolService;
import services.StudentService;
import services.TeacherService;
import domain.Administrator;
import domain.Agent;
import domain.ClassGroup;
import domain.Folder;
import domain.Parent;
import domain.School;
import domain.Student;
import domain.Teacher;
import forms.CreateActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private TeacherService			teacherService;
	@Autowired
	private StudentService			studentService;
	@Autowired
	private ParentService			parentService;
	@Autowired
	private AgentService			agentService;
	@Autowired
	private SchoolService			schoolService;
	@Autowired
	private ClassGroupService		classGroupService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private FolderService			folderService;


	// ------------------------Creación de Teacher----------------------------

	@RequestMapping(value = "/createTeacher", method = RequestMethod.GET)
	public ModelAndView createUserAccountTeacher() {
		return this.createEditModelAndViewTeacher(new CreateActorForm());
	}

	// Este método save guarda la primera vez que se ha creado un teacher
	@RequestMapping(value = "/createTeacher", method = RequestMethod.POST, params = "saveTeacher")
	public ModelAndView saveTeacher(@Valid final CreateActorForm createActorForm, final BindingResult binding) {
		ModelAndView result;
		final Teacher teacher = this.teacherService.reconstruct(createActorForm, binding);

		if (!createActorForm.getValida()) {
			result = this.createEditModelAndViewTeacher(createActorForm, "actor.terms");
			return result;
		}

		if (!this.actorService.checkPassword(createActorForm)) {
			result = this.createEditModelAndViewTeacher(createActorForm, "actor.password.fail");
			return result;
		}

		if (binding.hasErrors())
			result = this.createEditModelAndViewTeacher(createActorForm);
		else
			try {
				Teacher save = this.teacherService.save(teacher);
				save.setFolders(this.folderService.folderOfSystem());
				Collection<Folder> folders2 = save.getFolders();
				for (Folder f : folders2) {
					f.setActor(save);
					this.folderService.save(f);
				}
				save.setFolders(folders2);
				this.teacherService.saveForFolders(save);
				final String name = teacher.getName();
				final Date moment = new Date();

				result = createModelAndView("welcome/index");
				result.addObject("name", name);
				result.addObject("moment", moment);
				return result;

			} catch (final Throwable oops) {
				final CreateActorForm copia = createActorForm;
				copia.setPassword(" ");

				if (oops.getCause().getCause().toString().contains("@")) {
					result = this.createEditModelAndViewTeacher(copia, "email.commit.error");
				} else {
					result = this.createEditModelAndViewTeacher(copia, "userAccount.commit.error");
				}
			}
		return result;
	}

	// ------------Métodos auxiliares para crear Teacher
	// ----------------------

	private ModelAndView createEditModelAndViewTeacher(final CreateActorForm createActorForm) {
		ModelAndView result;

		result = this.createEditModelAndViewTeacher(createActorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewTeacher(final CreateActorForm createActorForm, final String message) {
		final ModelAndView result = createModelAndView("actor/createTeacher");
		List<School> schools = new ArrayList<>(this.schoolService.findAll());
		result.addObject("createActorForm", createActorForm);
		result.addObject("message", message);
		result.addObject("direction", "actor/createTeacher.do");
		result.addObject("save", "saveTeacher");
		result.addObject("teacher", true);
		result.addObject("schools", schools);
		return result;
	}

	// ------------------------Creación de Student----------------------------

	@RequestMapping(value = "/createStudent", method = RequestMethod.GET)
	public ModelAndView createUserAccountStudent(@RequestParam final int classGroupId) {
		CreateActorForm createActorForm = new CreateActorForm();
		createActorForm.setClassGroup(this.classGroupService.findOne(classGroupId));
		return this.createEditModelAndViewStudent(createActorForm);
	}

	// Este método save guarda la primera vez que se ha creado un student
	@RequestMapping(value = "/createStudent", method = RequestMethod.POST, params = "saveStudent")
	public ModelAndView saveStudent(@Valid final CreateActorForm createActorForm, final BindingResult binding) {
		ModelAndView result;

		final Student student = this.studentService.reconstruct(createActorForm, binding);
		if (!this.actorService.checkPassword(createActorForm)) {
			result = this.createEditModelAndViewStudent(createActorForm, "actor.password.fail");
			return result;
		}

		if (binding.hasErrors())
			result = this.createEditModelAndViewStudent(createActorForm);
		else
			try {
				Student save = this.studentService.saveEdit(student);
				save.setFolders(this.folderService.folderOfSystem());
				for (Folder f : save.getFolders()) {
					f.setActor(save);
					this.folderService.save(f);
				}
				this.studentService.saveEdit(save);
				final String name = student.getName();
				final Date moment = new Date();

				result = createModelAndView("welcome/index");
				result.addObject("name", name);
				result.addObject("moment", moment);
				return result;

			} catch (final Throwable oops) {
				final CreateActorForm copia = createActorForm;
				copia.setPassword(" ");
				if (oops.getCause().getCause().toString().contains("@")) {
					result = this.createEditModelAndViewStudent(copia, "email.commit.error");
				} else {
					result = this.createEditModelAndViewStudent(copia, "userAccount.commit.error");
				}
			}
		return result;
	}

	// ------------Métodos auxiliares para crear Student
	// ----------------------

	private ModelAndView createEditModelAndViewStudent(final CreateActorForm createActorForm) {
		ModelAndView result;

		result = this.createEditModelAndViewStudent(createActorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewStudent(final CreateActorForm createActorForm, final String message) {
		final ModelAndView result = createModelAndView("actor/createStudent");
		List<ClassGroup> classGroups = new ArrayList<>(this.classGroupService.findAll());
		result.addObject("createActorForm", createActorForm);
		result.addObject("message", message);
		result.addObject("direction", "actor/createStudent.do");
		result.addObject("save", "saveStudent");
		result.addObject("student", true);
		result.addObject("classGroups", classGroups);

		return result;
	}

	// ------------------------Creación de Parent----------------------------

	@RequestMapping(value = "/createParent", method = RequestMethod.GET)
	public ModelAndView createUserAccountParent() {
		return this.createEditModelAndViewParent(new CreateActorForm());
	}

	// Este método save guarda la primera vez que se ha creado un parent
	@RequestMapping(value = "/createParent", method = RequestMethod.POST, params = "saveParent")
	public ModelAndView saveParent(@Valid final CreateActorForm createActorForm, final BindingResult binding) {
		ModelAndView result;

		final Parent parent = this.parentService.reconstruct(createActorForm, binding);
		if (!createActorForm.getValida()) {
			result = this.createEditModelAndViewParent(createActorForm, "actor.terms");
			return result;
		}

		if (!this.actorService.checkPassword(createActorForm)) {
			result = this.createEditModelAndViewParent(createActorForm, "actor.password.fail");
			return result;
		}

		if (binding.hasErrors())
			result = this.createEditModelAndViewParent(createActorForm);
		else
			try {
				Parent save = this.parentService.save(parent);
				save.setFolders(this.folderService.folderOfSystem());
				for (Folder f : save.getFolders()) {
					f.setActor(save);
					this.folderService.save(f);
				}
				this.parentService.saveForFolders(save);
				final String name = parent.getName();
				final Date moment = new Date();

				result = createModelAndView("welcome/index");
				result.addObject("name", name);
				result.addObject("moment", moment);
				return result;

			} catch (final Throwable oops) {
				final CreateActorForm copia = createActorForm;
				copia.setPassword(" ");
				if (oops.getCause().getCause().toString().contains("@")) {
					result = this.createEditModelAndViewParent(copia, "email.commit.error");
				} else {
					result = this.createEditModelAndViewParent(copia, "userAccount.commit.error");
				}
			}
		return result;
	}

	// ------------Métodos auxiliares para crear Parent
	// ----------------------

	private ModelAndView createEditModelAndViewParent(final CreateActorForm createActorForm) {
		ModelAndView result;

		result = this.createEditModelAndViewParent(createActorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewParent(final CreateActorForm createActorForm, final String message) {
		final ModelAndView result = createModelAndView("actor/createParent");
		result.addObject("createActorForm", createActorForm);
		result.addObject("message", message);
		result.addObject("direction", "actor/createParent.do");
		result.addObject("save", "saveParent");

		return result;
	}

	/*---Creación de Agents ---*/
	@RequestMapping(value = "/createAgent", method = RequestMethod.GET)
	public ModelAndView createUserAccountAgent() {
		return this.createEditModelAndViewAgent(new CreateActorForm());
	}

	// Este método save guarda la primera vez que se ha creado un agent
	@RequestMapping(value = "/createAgent", method = RequestMethod.POST, params = "saveAgent")
	public ModelAndView saveAgent(@Valid final CreateActorForm createActorForm, final BindingResult binding) {
		ModelAndView result;

		final Agent agent = this.agentService.reconstruct(createActorForm, binding);
		if (!createActorForm.getValida()) {
			result = this.createEditModelAndViewAgent(createActorForm, "actor.terms");
			return result;
		}

		if (!this.actorService.checkPassword(createActorForm)) {
			result = this.createEditModelAndViewAgent(createActorForm, "actor.password.fail");
			return result;
		}

		if (binding.hasErrors())
			result = this.createEditModelAndViewAgent(createActorForm);
		else
			try {
				Agent save = this.agentService.save(agent);
				save.setFolders(this.folderService.folderOfSystem());
				for (Folder f : save.getFolders()) {
					f.setActor(save);
					this.folderService.save(f);
				}
				this.agentService.saveForFolders(save);

				final String name = agent.getName();
				final Date moment = new Date();

				result = createModelAndView("welcome/index");
				result.addObject("name", name);
				result.addObject("moment", moment);
				return result;

			} catch (final Throwable oops) {
				final CreateActorForm copia = createActorForm;
				copia.setPassword(" ");
				if (oops.getCause().getCause().toString().contains("@")) {
					result = this.createEditModelAndViewAgent(copia, "email.commit.error");
				} else {
					result = this.createEditModelAndViewAgent(copia, "userAccount.commit.error");
				}
			}
		return result;
	}
	// ------------Métodos auxiliares para crear AGENTS ----------------------

	private ModelAndView createEditModelAndViewAgent(final CreateActorForm createActorForm) {
		ModelAndView result;

		result = this.createEditModelAndViewAgent(createActorForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAgent(final CreateActorForm createActorForm, final String message) {
		final ModelAndView result = createModelAndView("actor/createAgent");
		result.addObject("createActorForm", createActorForm);
		result.addObject("message", message);
		result.addObject("direction", "actor/createAgent.do");
		result.addObject("save", "saveAgent");
		result.addObject("agent", true);

		return result;
	}

	// Display publico
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) {
		final ModelAndView result = createModelAndView("actor/display");
		final Teacher actor = this.teacherService.findOne(actorId);
		if (actor == null) {
			result.addObject("actorNull", true);
		} else {
			result.addObject("actor", actor);
			result.addObject("actorType", "teacher");
			result.addObject("isMyProfile", false);
			result.addObject("entries", actor.getEntries());
			result.addObject("uriList", "actor/display.do?actorId=" + actor.getId());
			result.addObject("uriEntryDisplay", "entry/display.do?entryId=");
		}

		return result;
	}

	// MyDisplay para todos los actores
	@RequestMapping(value = "/myDisplay", method = RequestMethod.GET)
	public ModelAndView myDisplay() {
		final ModelAndView result = createModelAndView("actor/display");
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		final Authority authorityTeacher = new Authority();
		authorityTeacher.setAuthority(Authority.TEACHER);
		final Authority authorityParent = new Authority();
		authorityParent.setAuthority(Authority.PARENT);
		final Authority authorityAgent = new Authority();
		authorityAgent.setAuthority(Authority.AGENT);
		final Authority authorityStudent = new Authority();
		authorityStudent.setAuthority(Authority.STUDENT);

		if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityParent)) {
			final Parent actor = this.parentService.findByPrincipal();
			result.addObject("actor", actor);
			result.addObject("actorType", "parent");
		} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityTeacher)) {
			final Teacher actor = this.teacherService.findByPrincipal();
			result.addObject("actor", actor);
			result.addObject("actorType", "teacher");
			result.addObject("entries", actor.getEntries());
			result.addObject("uriList", "actor/myDisplay.do");
			result.addObject("uriEntryDisplay", "entry/teacher/display.do?actorDisplay=true&entryId=");
		} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityAdmin)) {
			final Administrator actor = this.administratorService.findByPrincipal();
			result.addObject("actor", actor);
			result.addObject("actorType", "administrator");
		} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityAgent)) {
			final Agent actor = this.agentService.findByPrincipal();
			result.addObject("actor", actor);
			result.addObject("actorType", "agent");
		} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityStudent)) {
			final Student actor = this.studentService.findByPrincipal();
			result.addObject("actor", actor);
			;
			result.addObject("actorType", "student");
		} else
			throw new IllegalArgumentException("Authority is not valid!");
		result.addObject("isMyProfile", true);

		return result;
	}
}
