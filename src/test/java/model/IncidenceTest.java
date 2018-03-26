package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IncidenceTest {

	@Test
	public void testIncidence() {
		Incidence inci = new Incidence();
		assertNotNull(inci.getComments());
		assertNotNull(inci.getTags());
		assertNotNull(inci.getOtherfields());
	}
	@Test
	public void testIncidence2() {
		List<String> tags = new ArrayList<String>();
		List<String> otherfields = new ArrayList<String>();
		List<String> comments = new ArrayList<String>();

		Incidence inci = new Incidence("Id1", "Terremoto", 2, "Inci_name",
										"inci_description", "inci_location", "inci_info", tags, 
										otherfields, 1, 1, "operatorId", comments);
		assertEquals("Id1", inci.getInciId());
		assertEquals("Terremoto", inci.getUsername());
		assertEquals(2, inci.getUsertype());
		assertEquals("Inci_name", inci.getInciName());
		assertEquals("inci_description", inci.getInciDescription());
		assertEquals("inci_location", inci.getInciLocation());
		assertEquals("inci_info", inci.getInciInfo());
		assertNotNull(inci.getTags());
		assertNotNull(inci.getComments());
		assertNotNull(inci.getOtherfields());
		assertEquals(1, inci.getState());
		assertEquals(1, inci.getExpiration());
		assertEquals("operatorId", inci.getOperatorId());

		/*Test setters*/
		inci.setInciId("Id2");
		assertEquals("Id2", inci.getInciId());

		inci.setUsername("Tsunami");
		assertEquals("Tsunami", inci.getUsername());

		inci.setUsertype(3);
		assertEquals(3, inci.getUsertype());

		inci.setInciName("a");
		assertEquals("a", inci.getInciName());

		inci.setInciDescription("b");
		assertEquals("b", inci.getInciDescription());

		inci.setInciLocation("c");
		assertEquals("c", inci.getInciLocation());

		inci.setInciInfo("d");
		assertEquals("d", inci.getInciInfo());

		inci.setOperatorId("e");
		assertEquals("e", inci.getOperatorId());

		
	}

}
