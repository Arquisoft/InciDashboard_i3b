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
		assertEquals("inci_location", inci.getInciLocation());
		assertNotNull(inci.getTags());
		assertNotNull(inci.getComments());
		assertNotNull(inci.getOtherfields());
		assertEquals(1, inci.getState());
		assertEquals(1, inci.getExpiration());
		assertEquals("operatorId", inci.getOperatorId());






		
	}

}
