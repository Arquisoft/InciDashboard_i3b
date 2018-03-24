package controller;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import model.Incidence;
import model.Operator;
import repository.IncidenceRepository;
import repository.OperatorRepository;


@Controller
public class IndexController {
    
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	public static List<Incidence> incidences = new ArrayList<>();
	private static List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	
	public static List<SseEmitter> getSsEmitters() {
		return sseEmitters;
	}
	public static final String INDEX_VIEW = "index";
	public static final String MANAGE_VIEW = "manage";
	public static final String OPERATOR_VIEW = "operator";

	@Autowired
	private IncidenceRepository inciRepo;
	
	@Autowired
	private OperatorRepository operatorRepo;
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<Incidence> incis = inciRepo.findAll();
		model.addAttribute("incidences", incis);
		return INDEX_VIEW;
	}
	
	@GetMapping("/index/login")
	public ModelAndView login(@RequestParam(name="id", required=true) String operatorId,
			@RequestParam(name="pass", required=true) String password) {
		
		Operator operatorFromDB = operatorRepo.findOne(operatorId);
		if(operatorFromDB!=null && operatorFromDB.getPassword().equals(password)) {
			ModelAndView mav = new ModelAndView(OPERATOR_VIEW);
			OperatorController.loggedOperator = operatorFromDB;
			return mav;
		}
		else	
			return  new ModelAndView("redirect:/index");
	}
	
	@GetMapping("/index/searchIncidence")
	public ModelAndView searchIncidence(@RequestParam(name = "id", required=false) String myid) {
		if(myid==null)
	        return  new ModelAndView("redirect:/index");
		ModelAndView mav = new ModelAndView(MANAGE_VIEW);
		Incidence incidence = inciRepo.findOne(myid);
		if (incidence==null)
	        return  new ModelAndView("redirect:/index");	
		ManageController.lastIncidence = incidence;
		mav.addObject("inci",incidence);
		return mav;
	 }
   
	@RequestMapping(path = "/index", method = RequestMethod.POST)
  	public String showMessage(String data, String topic) {
	  final SseEmitter sseEmitter = new SseEmitter();
            try {
                sseEmitter.send(data);
                LOG.info("send");
            } catch (IOException e) {
            	LOG.error("Browser has closed");
            }
            return data;
        }
	
    @RequestMapping("/raw")
    SseEmitter sendMessages() {
    	SseEmitter emiter = new SseEmitter(100000L);
    	synchronized (sseEmitters) {
    		sseEmitters.add(emiter);
    		emiter.onCompletion(() -> {
    			synchronized(sseEmitters ) {
    				sseEmitters.remove(emiter);
    			}
    		});
    		return emiter;
		}
    }
    
   
}
