package pkg.webservice.first;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/res")
public class RestRessourceJersey {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String testText(){
		return "just a text test";
	}
	
	@GET 
	@Produces(MediaType.TEXT_XML) 
	public String testXML(){
		return "<body><i>just</i> an XML <b>test</b></body>";
	}

}
