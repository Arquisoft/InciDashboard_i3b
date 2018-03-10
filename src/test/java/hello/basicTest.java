package hello;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import hello.listeners.MessageListener;
import sender.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class basicTest {
	
	@Autowired
	  private MessageListener listener;

	  @Autowired
	  private Sender sender;

	@Test
	public void test()  throws Exception {
	    sender.send("exampleTopic", "Hello Spring Kafka!");

	    listener.getLatch().await(10000, TimeUnit.MILLISECONDS);
	    }

}
