
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.LevelService;
import services.SubjectService;
import domain.Level;
import domain.Subject;

@Controller
@RequestMapping("/subject")
public class SubjectController extends AbstractController {

	@Autowired
	private SubjectService	subjectService;

	@Autowired
	private LevelService	levelService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam int levelId) {
		ModelAndView result;
		final Collection<Subject> subjects = this.subjectService.findByLevel(levelId);
		result = super.createModelAndView("subject/general/list");
		result.addObject("subjects", subjects);
		Level level = this.levelService.findOne(levelId);
		String levelSchool = "subject." + String.valueOf(level.getLevel());
		result.addObject("levelSchool", levelSchool);
		result.addObject("levelId", levelId);
		result.addObject("level", level);
		return result;
	}
}
