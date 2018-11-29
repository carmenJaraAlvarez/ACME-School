/*
 * SearcherController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ParentsGroupService;
import services.SchoolService;
import domain.ParentsGroup;
import domain.School;

@Controller
@RequestMapping("/searcher")
public class SearcherController extends AbstractController {

	// ------------------------------------------------------------------------
	@Autowired
	private ParentsGroupService	parentsGroupService;

	@Autowired
	private SchoolService		schoolService;


	// Constructors -----------------------------------------------------------

	public SearcherController() {
		super();
	}

	// ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result = createDisplayModelAndView();
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET, params = "search")
	public ModelAndView searcherList(@RequestParam String keyWord, @RequestParam String search) {
		ModelAndView result;
		if (search.equals("parentsGroup")) {
			result = createParentsGroupModelAndView(keyWord, search);
		} else if (search.equals("school")) {
			result = createSchoolModelAndView(keyWord, search);
		} else {
			result = createDisplayModelAndView();
		}
		return result;
	}

	private ModelAndView createParentsGroupModelAndView(final String keyWord, final String search) {
		ModelAndView result = new ModelAndView("search/parentsGroups");
		final Collection<ParentsGroup> parentsGroups = this.parentsGroupService.findByKeyWord(keyWord);
		result.addObject("uri", "searcher/display.do");
		result.addObject("parentsGroups", parentsGroups);
		result.addObject("search", "parentsGroup");
		result.addObject("keyWord", keyWord);
		return result;
	}
	private ModelAndView createSchoolModelAndView(final String keyWord, final String search) {
		ModelAndView result = new ModelAndView("search/schools");
		final Collection<School> schools = this.schoolService.findByKeyWord(keyWord);
		result.addObject("uri", "searcher/display.do");
		result.addObject("schools", schools);
		result.addObject("search", "school");
		result.addObject("keyWord", keyWord);

		return result;
	}
	private ModelAndView createDisplayModelAndView() {
		ModelAndView result = new ModelAndView("search/display");
		result.addObject("uri", "searcher/display.do");
		result.addObject("search", "display");

		return result;
	}

}
