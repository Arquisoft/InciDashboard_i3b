package kafka;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import controller.IndexController;
import model.Incidence;

@Service
public class Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    IndexController ic;
    @KafkaListener(topics = "test")
    public void listen(@Payload String message) {
        LOG.info("received message='{}'", message);
        //ic.showMessage(message, "test");//este método tiene que actualizar el html
        System.out.println("New message received: \"" + message + "\"");
        
        String[] text = message.split("-");
        
        Incidence inci = new Incidence(text[0], text[1]);
        IndexController.incidences.add(inci);
        for(SseEmitter s : IndexController.getSsEmitters()) {
        	try {
        		s.send("Message: " + message);
        	} catch(IOException e) {
        		e.printStackTrace();
        	}
        }
    }

}