import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import kafka.sender.Sender;
import model.Incidence;
import repository.IncidenceRepository;
import utils.IncidenceGenerator;

@SpringBootApplication
@ComponentScan({"controller", "kafka"})
@EnableMongoRepositories(basePackageClasses = IncidenceRepository.class)
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    private Sender sender;
    
    @Autowired
    private IncidenceRepository repository;

    @Override
    public void run(String... strings) throws Exception {
    	repository.deleteAll();
        int i = 0;
        while (true) {
        	Incidence inci = IncidenceGenerator.randomInci(i);
        	sender.send(inci);
        	repository.save(inci);
        	if(repository.count()>i)
        		System.err.println("Incidence saved");
        	TimeUnit.SECONDS.sleep(7);
        	i++;
        }

    }
}