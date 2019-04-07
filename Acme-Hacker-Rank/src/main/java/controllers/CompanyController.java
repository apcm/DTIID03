package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Company;

import services.CompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController{
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		
		Collection<Company> companies = companyService.findAll();
		
		res = new ModelAndView("company/list");
		res.addObject("requestURI", "company/list.do");
		res.addObject("companies", companies);
		
		return res;
		
	}

}
