package allTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controller.AllControllerTests;
import model.AllModelTests;
import selenium.AllSeleniumTests;
import utils.IncidenceGeneratorTest;

@RunWith(Suite.class)
@SuiteClasses({IncidenceGeneratorTest.class,AllSeleniumTests.class, AllControllerTests.class,
	AllModelTests.class})
public class AllTests {

}
