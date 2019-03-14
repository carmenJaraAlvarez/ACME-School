
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EntryService;
import domain.Entry;
import domain.Teacher;

@Controller
@RequestMapping("/entry")
public class EntryController extends AbstractController {

	@Autowired
	private EntryService	entryService;


	// Panic handler ----------------------------------------------------------

	public EntryController() {
		super();
	}

	//  ---------------------------------------------------------------	

	//Display público de "Entrada"
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int entryId) {
		ModelAndView result;
		result = createModelAndView("actor/display");
		Entry entry = this.entryService.findOne(entryId);
		if (entry == null) {
			result.addObject("entryNull", true);
		} else {
			Teacher teacher = entry.getTeacher();
			result.addObject("actor", teacher);
			result.addObject("actorType", "teacher");
			result.addObject("isMyProfile", false);
			result.addObject("entry", entry);
			result.addObject("display", true);
			result.addObject("uriList", "actor/display.do?actorId=" + teacher.getId());
			result.addObject("uriEntry", "entry/display.do?entryId=" + entryId);
		}
		return result;
	}

}
