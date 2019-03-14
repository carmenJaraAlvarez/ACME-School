/*
 * AbstractController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import services.AdvertisementService;
import domain.Advertisement;

@Controller
public class AbstractController {

	@Autowired
	private AdvertisementService	advertisementService;


	// Panic handler ----------------------------------------------------------

	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;

		result = new ModelAndView("misc/panic");
		result.addObject("name", ClassUtils.getShortName(oops.getClass()));
		result.addObject("exception", oops.getMessage());
		result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));

		return result;
	}

	// Announcement banner method ----------------------------------------------------------

	public ModelAndView createModelAndView(final String viewName) {

		//Para mostrar el banner del anuncio [inicio]
		final Map<String, String> model = new HashMap<String, String>();
		final Advertisement announcement = this.advertisementService.getValidRandom();
		if (announcement != null) {
			final String bannerAnnouncement = announcement.getBanner();
			final String webAnnouncement = announcement.getWeb();
			final String titleAnnouncement = announcement.getTitle();
			model.put("bannerAnnouncement", bannerAnnouncement);
			model.put("webAnnouncement", webAnnouncement);
			model.put("titleAnnouncement", titleAnnouncement);
		}
		//Para mostrar el banner del anuncio [fin]

		final ModelAndView result = new ModelAndView(viewName, model);

		return result;
	}

}
