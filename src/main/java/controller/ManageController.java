package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import model.Incidence;
import repository.IncidenceRepository;

@Controller
@RequestMapping("/manage")
public class ManageController {
	
	public static final String FORM_VIEW = "manage";
	
	@Autowired
	private IncidenceRepository repo;
	
	 @RequestMapping(value="/{id}")
	    public String manage(Model model, @PathVariable("id") String id) {
	    	Incidence aux = repo.findOne(id);
	    	model.addAttribute("inci", aux);
	    	return FORM_VIEW;
	    }

}
