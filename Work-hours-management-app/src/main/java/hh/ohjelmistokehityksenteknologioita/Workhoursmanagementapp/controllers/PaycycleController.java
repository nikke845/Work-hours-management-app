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

import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Paycycle;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.PaycycleRepository;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.Workday;
import hh.ohjelmistokehityksenteknologioita.Workhoursmanagementapp.domain.WorkdayRepository;
@CrossOrigin
@Controller
public class PaycycleController {

	@Autowired
	private PaycycleRepository pRepository;
		
		// palauttaa paycycle listan omistajan perusteella.
		@RequestMapping(value="/paycyclelist", method = RequestMethod.GET)
		public String findUsersPaycycles(Model model, Principal principal) {
			String name = principal.getName();
			model.addAttribute("paycycles", pRepository.findByOwner(name));
			if (name.contains("admin")){
		    	  model.addAttribute("paycycles", pRepository.findAll());
		      }
		      else {
		    	  model.addAttribute("paycycles", pRepository.findByOwner(name));
		      }
			
			return "paycyclelist";
		}
		
		// RESTful service to get all paycycles
	    @RequestMapping(value="/paycycles", method = RequestMethod.GET)
	    public @ResponseBody List<Paycycle> getPaycyclesRest(Principal principal) {
	    	String name = principal.getName();
	        return (List<Paycycle>) pRepository.findByOwner(name);
	    }    

//		// RESTful service to get paycycle by id
//	    @RequestMapping(value="/paycycle/{id}", method = RequestMethod.GET)
//	    public @ResponseBody Optional<Paycycle> getPaycycleRest(@PathVariable("id") Long paycycleId) {	
//	    	return pRepository.findById(paycycleId);
//	    }
	    
	    
		 // RESTful service to get all paycycles for specific user
	    @RequestMapping(value="/paycycles/{owner}", method = RequestMethod.GET)
//	    @PreAuthorize("hasAuthority('ADMIN')") //vain admin pystyy hakemaan
	    public @ResponseBody List<Paycycle> getPaycyclesByOwnerRest(@PathVariable("owner") String paycycleOwner) {	
	        return (List<Paycycle>) pRepository.findByOwner(paycycleOwner);

	    }
	    
	    // RESTful service to get all paycycles for specific user and specific paycycle season (/paycycles/user1/Tammikuu)
	    @RequestMapping(value="/paycycles/{owner}/{name}", method = RequestMethod.GET)
//	    @PreAuthorize("hasAuthority('ADMIN')") //vain admin pystyy hakemaan
	    public @ResponseBody List<Paycycle> getPaycyclesByOwnerAndNameRest(@PathVariable("owner") String paycycleOwner, @PathVariable("name") String paycycleName) {	
	        return (List<Paycycle>) pRepository.findByOwnerAndName(paycycleOwner, paycycleName);

	    }

	    // RESTful service to save new paycycle
	    @RequestMapping(value="/postpaycycles", method = RequestMethod.POST)
	    public @ResponseBody Paycycle savePaycycleRest(@RequestBody Paycycle paycycle, Model model) {	
	    	return pRepository.save(paycycle);
	    }
	    
	    // RESTful service to delete a paycycle by id 
		@RequestMapping(value = "/paycycle/delete/{id}", method = RequestMethod.GET)
		@PreAuthorize("hasAuthority('ADMIN')")
		public String deletePaycycle(@PathVariable("id") Long paycycleid) {
			pRepository.deleteById(paycycleid);
			return "redirect:/paycyclelist";
		}
	    
		// service for the form to add a new paycycle
		@RequestMapping(value = "/savepaycycle", method = RequestMethod.POST)
		public String savePaycycle(@ModelAttribute Paycycle paycycle, BindingResult errors, Model model, Principal principal) {
			String owner = principal.getName(); //get logged in username
			paycycle.setOwner(owner);
			pRepository.save(paycycle);
			return "redirect:paycyclelist";
		}

		@RequestMapping(value = "/addpaycycle", method = RequestMethod.GET)
		public String addPaycycle(Model model) {
			model.addAttribute("paycycle", new Paycycle());
	        return "addpaycycle";
		}
}
