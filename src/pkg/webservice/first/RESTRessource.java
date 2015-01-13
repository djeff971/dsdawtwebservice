package pkg.webservice.first;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pkg.webservice.beans.Tweet;
import pkg.webservice.beans.User;
import pkg.webservice.dao.DbHelper;

@Path("/res")
public class RESTRessource {
	/*
	 * @GET
	 * 
	 * @Produces(MediaType.TEXT_PLAIN) public String testText() { return
	 * "just a text test"; }
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.TEXT_XML) public String testXML() { return
	 * "<body><i>just</i> an XML <b>test</b></body>"; }
	 */

	// DbHelper dbhelp;
	// static Logger log = Logger.getLogger(RESTRessource.class);

	/*
	 * public void updateData() { //TODO: checkout if the TB is empty
	 * dbhelp.updateData(); }
	 */

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<User> getUsers() {
		// log.info("let's go into db...");
		DbHelper dbhelper = new DbHelper();
		List<User> users = dbhelper.getUsers();

		for (User u : users) {
			System.out.println(u.getId() + u.getName());
		}
		return dbhelper.getUsers();
		// return dbhelp.getUsers();
	}
	/*
	 * public List<Tweet> getTweets(String nickname) { return
	 * dbhelp.getTweets(nickname); }
	 */
}
