package online.blickle.pi;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.owlike.genson.Genson;

public class PortDescriptionTest {
	
	public static String OUTPUT_0 = "OUTPUT_0";
	public static String INPUT_0 = "INPUT_0";

	@Test
	public void portDescriptionList_findDescriptionById_portDescriptionFound(){
		
		PortDescriptionList p = getTestPortDescriptionList();
		PortDescription pd = p.getPortDecriptionById(OUTPUT_0);
		assertTrue(pd!=null);
		
		 pd = p.getPortDecriptionById(INPUT_0);
		assertTrue(pd!=null);
		
	}
	
	public static PortDescriptionList getTestPortDescriptionList() {
		String sConfig = "{ \"portDescriptions\": [ "
				+ "{\"id\": \""+OUTPUT_0+"\",\"description\": \"Ausgang 0\",\"isOutput\": true,\"raspPortNr\": 0},"
				+ "{\"id\": \""+INPUT_0+"\",\"description\": \"Eingang 0\",\"isOutput\": false,\"raspPortNr\": 9}"
				+ "]}";
		Genson genson = new Genson();
		PortDescriptionList p = genson.deserialize(sConfig, PortDescriptionList.class);
		return p;
	}
}
