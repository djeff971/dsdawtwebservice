package pkg.webservice.first;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.owlike.genson.Genson;

import pkg.webservice.beans.Tweet;
import pkg.webservice.beans.User;
import pkg.webservice.dao.DbHelper;

@Path("/res")
public class RESTRessource {
	
	Genson genson = new Genson();
	
	
	// DbHelper dbhelp;
	// static Logger log = Logger.getLogger(RESTRessource.class);

	/*
	 * public void updateData() { //TODO: checkout if the TB is empty
	 * dbhelp.updateData(); }
	 */

	@GET
	@Produces({MediaType.APPLICATION_JSON })
	public String getUsers() {
		// log.info("let's go into db...");
		DbHelper dbhelper = new DbHelper();
		List<User> users = dbhelper.getUsers();
		String allUsers = new String();
		for (User u : users) {
			allUsers += genson.serialize(u);
		}
		return allUsers;
	}
	/*
	 * public List<Tweet> getTweets(String nickname) { return
	 * dbhelp.getTweets(nickname); }
	 */
}
