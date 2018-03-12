package controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kafka.Receiver;

@Controller

public class IndexController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);


	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
    @RequestMapping(path = "/index", method = RequestMethod.POST)
  	public String showMessage(String data, String topic) {
	  final SseEmitter sseEmitter = new SseEmitter();
            try {
                sseEmitter.send("Message: " + data);
                LOG.info("send");
            } catch (IOException e) {
            	LOG.error("Browser has closed");
            }
            return data;
        }
  
      
}
