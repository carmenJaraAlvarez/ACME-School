
package controllers.administrator;

import java.text.DecimalFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	//Services

	//Constructors

	public DashboardAdministratorController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;

		final DecimalFormat df = new DecimalFormat("###.##");

		result = new ModelAndView("administrator/dashboard");
		result.addObject("requestURI", "/dashboard/administrator/display.do");

		return result;
	}
}
