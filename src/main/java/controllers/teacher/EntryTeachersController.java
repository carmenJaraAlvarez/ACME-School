
package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EntryService;
import services.TeacherService;
import controllers.AbstractController;
import domain.Entry;
import domain.Teacher;

@Controller
@RequestMapping("/entry/teacher")
public class EntryTeachersController extends AbstractController {

	@Autowired
	private TeacherService	teacherService;

	@Autowired
	private EntryService	entryService;


	// Panic handler ----------------------------------------------------------

	public EntryTeachersController() {
		super();
	}

	//  ---------------------------------------------------------------	

	// Lista de "Mis entradas"
	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = createModelAndView("entry/myList");
		Teacher logged = this.teacherService.findByPrincipal();
		result.addObject("teacher", logged);
		result.addObject("entries", logged.getEntries());
		result.addObject("myList", true);
		result.addObject("uri", "entry/teacher/myList.do");
		return result;
	}

	//Display de "Mi entrada"
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int entryId, @RequestParam(required = false, defaultValue = "") final String actorDisplay) {
		ModelAndView result;
		if (actorDisplay.equals("true")) {
			result = createModelAndView("actor/display");
		} else {
			result = createModelAndView("entry/display");
		}
		Entry entry = this.entryService.findOne(entryId);
		if (entry == null) {
			result.addObject("entryNull", true);
		} else {
			Teacher teacher = entry.getTeacher();
			if (actorDisplay.equals("true")) {
				result.addObject("actor", teacher);
				result.addObject("actorType", "teacher");
				result.addObject("isMyProfile", true);
				result.addObject("entry", entry);
				result.addObject("display", true);
				result.addObject("uriList", "actor/myDisplay.do");
				result.addObject("uriEntry", "entry/teacher/display.do?actorDisplay=true&entryId=" + entryId);
			} else {
				result.addObject("teacher", teacher);
				result.addObject("entry", entry);
				result.addObject("myDisplay", true);
				result.addObject("uri", "entry/teacher/myList.do");
			}
		}
		return result;
	}

	//Crear entrada nueva
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result = this.createModelAndView("entry/create");
		final Entry entry = this.entryService.create();
		result.addObject("entry", entry);
		result.addObject("uri", "entry/teacher/edit.do");
		result.addObject("cancelUri", "entry/teacher/myList.do");
		return result;
	}

	//Editar entrada existente
	@RequestMapping(value = "/edit", method = RequestMethod.GET, params = "entryId")
	public ModelAndView edit(@RequestParam final int entryId) {
		ModelAndView result;
		Entry entry = this.entryService.findOneToEdit(entryId);
		if (entry != null && entry.getTeacher().equals(this.teacherService.findByPrincipal())) {
			result = this.createEditModelAndView(entry);
		} else {
			result = createModelAndView("entry/edit");
			result.addObject("accessError", true);
		}

		return result;
	}

	//Método para guardar la creacion y edicion de entradas
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final Entry entry, final BindingResult binding) {
		ModelAndView result;

		final Entry reconstructed = this.entryService.reconstruct(entry, binding);
		if (reconstructed == null) {
			result = createModelAndView("entry/edit");
			result.addObject("accessError", true);
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(entry);
		else
			try {
				this.entryService.save(reconstructed);
				result = new ModelAndView("redirect:myList.do");
			} catch (final Throwable oops) {
				String msg = "entry.commit.error";
				if (oops.getMessage().contains("concurrency")) {
					msg = "entry.concurrency.error";
				}
				result = this.createEditModelAndView(reconstructed, msg);
			}
		return result;
	}

	private ModelAndView createEditModelAndView(final Entry entry) {
		ModelAndView result;
		result = this.createEditModelAndView(entry, null);
		return result;
	}

	private ModelAndView createEditModelAndView(final Entry entry, final String message) {
		ModelAndView result;
		if (entry.getId() == 0) {
			result = createModelAndView("entry/create");
		} else {
			result = createModelAndView("entry/edit");
		}
		result.addObject("entry", entry);
		result.addObject("message", message);
		result.addObject("uri", "entry/teacher/edit.do?entryId=" + entry.getId());
		result.addObject("cancelUri", "entry/teacher/myList.do");
		return result;
	}

}
