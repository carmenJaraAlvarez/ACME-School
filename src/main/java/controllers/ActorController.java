
package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import services.ActorService;
import services.AgentService;
import services.ClassGroupService;
import services.SchoolService;
import services.TeacherService;
import services.ParentService;
import services.StudentService;
import domain.Agent;
import domain.ClassGroup;
import domain.Parent;
import domain.Student;
import domain.Teacher;
import domain.School;
import forms.CreateActorForm;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService	actorService;
	@Autowired
	private TeacherService	teacherService;
	@Autowired
	private StudentService	studentService;
	@Autowired
	private ParentService	parentService;
	@Autowired
	private AgentService	agentService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private ClassGroupService classGroupService;


	// ------------------------Creación de Teacher----------------------------

	@RequestMapping(value = "/createTeacher", method = RequestMethod.GET)
	public ModelAndView createUserAccountTeacher() {
		return this.createEditModelAndViewTeacher(new CreateActorForm());
	}

	// Este método save guarda la primera vez que se ha creado un teacher
	@RequestMapping(value = "/createTeacher", method = RequestMethod.POST, params = "saveTeacher")
	public ModelAndView saveTeacher( final CreateActorForm createActorForm, final BindingResult binding) {
		ModelAndView result;
		if (!createActorForm.getValida()) {
			result = this.createEditModelAndViewTeacher(createActorForm, "actor.terms");
			return result;
		}

		if (!this.actorService.checkPassword(createActorForm)) {
			result = this.createEditModelAndViewTeacher(createActorForm, "actor.password.fail");
			return result;
		}

		final Teacher teacher = this.teacherService.reconstruct(createActorForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewTeacher(createActorForm);
		else
			try {
				this.teacherService.save(teacher);

				final String name = teacher.getName();
				final Date moment = new Date();

				result = new ModelAndView("welcome/index");
				result.addObject("name", name);
				result.addObject("moment", moment);
				return result;

			} catch (final Throwable oops) {
				final CreateActorForm copia = createActorForm;
				copia.setPassword(" ");
				result = this.createEditModelAndViewTeacher(copia, "userAccount.commit.error");
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
		final ModelAndView result = new ModelAndView("actor/createTeacher");
		List<School> schools = new ArrayList<>(this.schoolService.findAll());
		result.addObject("createActorForm", createActorForm);
		result.addObject("message", message);
		result.addObject("direction", "actor/createTeacher.do");
		result.addObject("save", "saveTeacher");
		result.addObject("teacher",true);
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
		public ModelAndView saveStudent(final CreateActorForm createActorForm, final BindingResult binding) {
			ModelAndView result;
			if (!createActorForm.getValida()) {
				result = this.createEditModelAndViewStudent(createActorForm, "actor.terms");
				return result;
			}

			if (!this.actorService.checkPassword(createActorForm)) {
				result = this.createEditModelAndViewStudent(createActorForm, "actor.password.fail");
				return result;
			}

			final Student student = this.studentService.reconstruct(createActorForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndViewStudent(createActorForm);
			else
				try {
					this.studentService.save(student);

					final String name = student.getName();
					final Date moment = new Date();

					result = new ModelAndView("welcome/index");
					result.addObject("name", name);
					result.addObject("moment", moment);
					return result;

				} catch (final Throwable oops) {
					final CreateActorForm copia = createActorForm;
					copia.setPassword(" ");
					result = this.createEditModelAndViewStudent(copia, "userAccount.commit.error");
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
			final ModelAndView result = new ModelAndView("actor/createStudent");
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
		public ModelAndView saveParent( final CreateActorForm createActorForm, final BindingResult binding) {
			ModelAndView result;
			if (!createActorForm.getValida()) {
				result = this.createEditModelAndViewParent(createActorForm, "actor.terms");
				return result;
			}

			if (!this.actorService.checkPassword(createActorForm)) {
				result = this.createEditModelAndViewParent(createActorForm, "actor.password.fail");
				return result;
			}

			final Parent parent = this.parentService.reconstruct(createActorForm, binding);

			if (binding.hasErrors())
				result = this.createEditModelAndViewParent(createActorForm);
			else
				try {
					this.parentService.save(parent);

					final String name = parent.getName();
					final Date moment = new Date();

					result = new ModelAndView("welcome/index");
					result.addObject("name", name);
					result.addObject("moment", moment);
					return result;

				} catch (final Throwable oops) {
					final CreateActorForm copia = createActorForm;
					copia.setPassword(" ");
					
					result = this.createEditModelAndViewParent(copia, "userAccount.commit.error");
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
			final ModelAndView result = new ModelAndView("actor/createParent");
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
	public ModelAndView saveAgent(final CreateActorForm createActorForm, final BindingResult binding) {
		ModelAndView result;
		if (!createActorForm.getValida()) {
			result = this.createEditModelAndViewAgent(createActorForm, "actor.terms");
			return result;
		}

		if (!this.actorService.checkPassword(createActorForm)) {
			result = this.createEditModelAndViewAgent(createActorForm, "actor.password.fail");
			return result;
		}

		final Agent agent = this.agentService.reconstruct(createActorForm, binding);

		if (binding.hasErrors())
			result = this.createEditModelAndViewAgent(createActorForm);
		else
			try {
				this.agentService.save(agent);

				final String name = agent.getName();
				final Date moment = new Date();

				result = new ModelAndView("welcome/index");
				result.addObject("name", name);
				result.addObject("moment", moment);
				return result;

			} catch (final Throwable oops) {
				final CreateActorForm copia = createActorForm;
				copia.setPassword(" ");
				result = this.createEditModelAndViewAgent(copia, "userAccount.commit.error");
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
		final ModelAndView result = new ModelAndView("actor/createAgent");
		result.addObject("createActorForm", createActorForm);
		result.addObject("message", message);
		result.addObject("direction", "actor/createAgent.do");
		result.addObject("save", "saveAgent");
		result.addObject("agent", true);

		return result;
	}

	// Display de cualquier perfil
//	@RequestMapping(value = "/display", method = RequestMethod.GET)
//	public ModelAndView display(@RequestParam final int actorId, @RequestParam(required = false, defaultValue = "") final String follow, @RequestParam(required = false, defaultValue = "") final String unfollow) {
//		final ModelAndView result = new ModelAndView("actor/display");
//		final Authority authorityUser = new Authority();
//		authorityUser.setAuthority(Authority.USER);
//		try {
//			final User actor = this.userService.findOne(actorId);
//			result.addObject("actor", actor);
//			result.addObject("requestURI", "user/edit.do");
//			result.addObject("actorType", "user");
//			result.addObject("userArticles", this.userService.articlesPublished(actorId));
//			result.addObject("userChirps", actor.getChirps());
//			if (this.actorService.checkAuthenticate() && this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityUser)) {
//				final Collection<User> users = this.userService.findFollowing(this.userService.findByPrincipal().getId());
//				result.addObject("isFollowing", users.contains(actor));
//				result.addObject("follow", follow);
//				result.addObject("unfollow", unfollow);
//				result.addObject("canFollow", !actor.equals(this.userService.findByPrincipal()));
//			}
//			result.addObject("articleURI", "actor/display.do?actorId=" + actor.getId());
//			result.addObject("isMyProfile", false);
//			result.addObject("error", false);
//
//		} catch (final Throwable oops) {
//			result.addObject("error", true);
//		}
//
//		return result;
//	}

	// MyDisplay para todos los actores
	// ----------------------------------------------------------------
//	@RequestMapping(value = "/myDisplay", method = RequestMethod.GET)
//	public ModelAndView myDisplay() {
//		final ModelAndView result = new ModelAndView("actor/display");
//
//		try {
//			//final Collection<Authority> authorities = this.actorService.findByPrincipal().getUserAccount().getAuthorities();
//
//			final Authority authorityAdmin = new Authority();
//			authorityAdmin.setAuthority(Authority.ADMIN);
//			final Authority authorityTeacher = new Authority();
//			authorityTeacher.setAuthority(Authority.TEACHER);
//			final Authority authorityDirector = new Authority();
//			authorityDirector.setAuthority(Authority.DIRECTOR);
//			final Authority authorityAgent = new Authority();
//			authorityAgent.setAuthority(Authority.AGENT);
//
//			if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityUser)) {
//				final User actor = this.userService.findByPrincipal();
//				result.addObject("actor", actor);
//				result.addObject("requestURI", "user/edit.do");
//				result.addObject("actorType", "user");
//				result.addObject("userArticles", actor.getArticles());
//				result.addObject("userChirps", actor.getChirps());
//				result.addObject("articleURI", "actor/myDisplay.do");
//			} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityTeacher)) {
//				final Teacher actor = this.teacherService.findByPrincipal();
//				result.addObject("actor", actor);
//				result.addObject("requestURI", "teacher/edit.do");
//				result.addObject("actorType", "teacher");
//			} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityAdmin)) {
//				final Admin actor = this.adminService.findByPrincipal();
//				result.addObject("actor", actor);
//				result.addObject("requestURI", "administrator/edit.do");
//				result.addObject("actorType", "administrator");
//			} else if (this.actorService.findByPrincipal().getUserAccount().getAuthorities().contains(authorityAgent)) {
//				final Agent actor = this.agentService.findByPrincipal();
//				result.addObject("actor", actor);
//				result.addObject("requestURI", "agent/edit.do");
//				result.addObject("actorType", "agent");
//			} else
//				throw new IllegalArgumentException("Authority is not valid!");
//			result.addObject("isMyProfile", true);
//			result.addObject("error", false);
//
//		} catch (final Throwable oops) {
//			result.addObject("error", true);
//		}
//
//		return result;
//	}
}
