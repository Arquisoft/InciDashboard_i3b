package controller;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	@Autowired
	private IncidenceRepository repo;
	
	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("incidences", incidences);
		return "index";
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
    
    
    @RequestMapping(value="/manage/{id}")
    public String manage(Model model, @PathVariable("id") String id) {
    	Incidence aux = repo.findOne(id);
    	model.addAttribute("inci", aux);
    	return "manage";
    }
}
