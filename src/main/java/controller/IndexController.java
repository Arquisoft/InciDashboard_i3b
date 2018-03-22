package controller;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import model.Incidence;
import repository.IncidenceRepository;


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

	@Autowired
	private IncidenceRepository repo;
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("incidences", incidences);
		return INDEX_VIEW;
	}
	
	@GetMapping("/index/searchIncidence")
	public ModelAndView searchIncidence(@RequestParam(name = "id", required=true) String myid) {
		if(myid==null)
	        return  new ModelAndView(INDEX_VIEW);
		ModelAndView mav = new ModelAndView(MANAGE_VIEW);
		Incidence incidence = repo.findOne(myid);
		if (incidence==null)
	        return  new ModelAndView(INDEX_VIEW);	
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
