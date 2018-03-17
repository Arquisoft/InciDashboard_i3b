import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import kafka.sender.Sender;
import model.Incidence;

@SpringBootApplication
@ComponentScan({"controller", "kafka"})
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    @Autowired
    private Sender sender;

    @Override
    public void run(String... strings) throws Exception {
        int i = 0;
        while (true) {
        	Incidence inci = new Incidence("Example", String.valueOf(i));
        	sender.send(inci);
        	TimeUnit.SECONDS.sleep(2);
        	i++;
        }

    }
}