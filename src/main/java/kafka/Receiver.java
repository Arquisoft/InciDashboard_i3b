package kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import controller.IndexController;

@Service
public class Receiver {

    private static final Logger LOG = LoggerFactory.getLogger(Receiver.class);

    @Autowired
    IndexController ic;
    @KafkaListener(topics = "test")
    public void listen(@Payload String message) {
        LOG.info("received message='{}'", message);
        ic.showMessage(message, "test");//este método tiene que actualizar el html
    }

}