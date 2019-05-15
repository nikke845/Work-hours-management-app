package hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Workday;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.WorkdayRepository;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Paycycle;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.PaycycleRepository;
@CrossOrigin
@Controller
public class WorkdayController {

	@Autowired
	private WorkdayRepository wRepository;
	@Autowired
	private PaycycleRepository pRepository;

	@RequestMapping(value="/login")
	public String login() {
		return "login";
	}
		
		// to see all your own workdays, not others workdays
		@RequestMapping(value="/workdaylist", method = RequestMethod.GET)
		public String findUserMessages(Model model, Principal principal) {
	      String name = principal.getName(); //hakee kirjautuneen käyttäjän nimen muistiin.
	      if (name.contains("admin")){
	    	  model.addAttribute("workdays", wRepository.findAll());
	      }
	      else {
	    	  model.addAttribute("workdays", wRepository.findByOwner(name));
	      }
	      return "workdaylist";

	}


		// RESTful service to get all workdays for user who is logged in
	    @RequestMapping(value="/workdays", method = RequestMethod.GET)
	    public @ResponseBody List<Workday> getWorkdaysRest(Principal principal) {	
//	        return (List<Workday>) wRepository.findAll();
	    	String name = principal.getName();
	    	return (List<Workday>) wRepository.findByOwner(name);
	    }
	    
	 // RESTful service to get all workdays for specific user
	    @RequestMapping(value="/workdays/{owner}", method = RequestMethod.GET)
//	    @PreAuthorize("hasAuthority('ADMIN')") //vain admin pystyy hakemaan
	    public @ResponseBody List<Workday> getWorkdaysRestAll(@PathVariable("owner") String workdayOwner) {	
	        return (List<Workday>) wRepository.findByOwner(workdayOwner);

	    }

//		// RESTful service to get workday by id 
//	    @RequestMapping(value="/workday/{id}", method = RequestMethod.GET)
//	    public @ResponseBody Optional<Workday> getWorkdayRest(@PathVariable("id") Long workdayId) {	
//	    	return wRepository.findById(workdayId);
//	    }
	    
	    // RESTful service to save new workday 
	    @RequestMapping(value="/workdays", method = RequestMethod.POST)
	    public @ResponseBody Workday saveWorkdayRest(@RequestBody Workday workday, Model model) {	
	    	return wRepository.save(workday);
	    }
	    
	    
	    // Home page of REST services
	    @RequestMapping(value="/resthome", method = RequestMethod.GET)
	    public String getRestHome() {	
	    	return "resthomepage";
	    }
	    // RESTful service to delete a workday by id 
		@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
		@PreAuthorize("hasAuthority('ADMIN')")
		public String deleteWorkday(@PathVariable("id") Long workdayId) {
			wRepository.deleteById(workdayId);
			return "redirect:/workdaylist";
		}
		// service for the form to add a new workday 
		@RequestMapping(value = "/save", method = RequestMethod.POST)
		public String saveBook(@ModelAttribute Workday workday, BindingResult errors, Model model, Principal principal) {
			String owner = principal.getName(); //get logged in username
			workday.setOwner(owner);
			wRepository.save(workday);
			return "redirect:workdaylist";
		}

		@RequestMapping(value = "/addworkday", method = RequestMethod.GET)
		public String addWorkday(Model model, Principal principal) {
			String name = principal.getName(); //get logged in username
			model.addAttribute("workday", new Workday());
			model.addAttribute("paycycles", pRepository.findByOwner(name));
	        return "addworkday";
		}
}