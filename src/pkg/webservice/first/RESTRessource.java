package pkg.webservice.first;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pkg.webservice.beans.Tweet;
import pkg.webservice.beans.User;
import pkg.webservice.dao.DbHelper;

@Path("/res")
public class RESTRessource {

	DbHelper dbhelp = new DbHelper();

	// static Logger log = Logger.getLogger(RESTRessource.class);


	@GET
	@Path("updateData")
	public String updateData() {
		dbhelp.updateData();
		return "ok";
	}

	@GET
	@Path("getUsers")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUsers() {
		return dbhelp.getUsers();
	}

	@GET
	@Path("getTweets/{nickname}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Tweet> getTweets(@PathParam("nickname") String nickname) {
		if (!nickname.startsWith("@")) {
			nickname = "@" + nickname;
		}
		if (dbhelp.getUserIdFromNickname(nickname).isEmpty()) {
			return null;
		} else {
			return dbhelp.getTweets((long) dbhelp
					.getUserIdFromNickname(nickname).get(0).getId());
		}

	}

}
