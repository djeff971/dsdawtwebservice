package pkg.webservice.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;

import pkg.webservice.beans.Tweet;
import pkg.webservice.beans.User;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@XmlRootElement
public class DbHelper {

	static Logger log = Logger.getLogger(DbHelper.class);

	private static String DBURL = "jdbc:mysql://localhost:8889/isep_awt";
	private static String DBLOGIN = "isep_user";
	private static String DBPASSWORD = "isep_pwd";

	List<String> result = new ArrayList<String>();
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet rset = null;
	String req = null;

	// twitter keys
	String consumerKey = "YdPbvDQuVlZrW1rjOnjtuUvFj";
	String consumerSecret = "rNX24tBcFrqT7F72v3XfhHlUv3fTg4pHL75cAkWzr6WY4VD38I";
	String oauthToken = "1324576304-DyBvhlX92bcQ4OFyJa6rtTWCIH7NF5Re3xlLnaU";
	String oauthTokenSecret = "1r6qqJFpSuAjIRi04GqAjcoqA4UHKZ3pi3PmSUpREY4QA";

	public void connectionToDb() {
		log.info("trying to connect to DB");
		// register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			log.error("unable to find driver for mysql");
			e.printStackTrace();
		}

		try {
			// get a connection to the DB
			conn = (Connection) DriverManager.getConnection(DBURL, DBLOGIN,
					DBPASSWORD);
			stmt = (Statement) conn.createStatement();
			stmt2 = (Statement) conn.createStatement();
			log.info("[SUCCESS] connected to DB !");
		} catch (SQLException e) {
			log.error("unable to connect to DB. Please check your port in DBURL and if WAMP is running.");
			e.printStackTrace();
		}
	}

	public List<User> getUsers() {

		connectionToDb();

		// create the list of users to return
		List<User> listOfUsers = new ArrayList<User>();

		try {
			// execute query and retrieve results
			req = "SELECT * FROM isep_awt.isep_awt_user";
			rset = stmt.executeQuery(req);

			// extract data from rset
			while (rset.next()) {
				User uzr = new User();
				uzr.setId(rset.getInt("user_id"));
				uzr.setName(rset.getString("name"));
				uzr.setNickname(rset.getString("nickname"));
				uzr.setJoinedDate(rset.getString("joined_date"));
				listOfUsers.add(uzr);
			}
			log.trace(listOfUsers.size() + " users retrieved");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return listOfUsers;
	}

	public int checkUserTb() {
		try {
			// execute query to return the nb of lines in user table
			req = "SELECT count(*) as nb FROM isep_awt.isep_awt_user;";
			rset = stmt.executeQuery(req);
			rset.next();
			log.trace(rset.getInt(1) + " users in the DB");
			// return rset.getInt(1);
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void updateData() {

		// Create configuration builder and set key, token etc
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey(consumerKey);
		builder.setOAuthConsumerSecret(consumerSecret);
		builder.setOAuthAccessToken(oauthToken);
		builder.setOAuthAccessTokenSecret(oauthTokenSecret);
		Twitter twitter = new TwitterFactory(builder.build()).getInstance();

		try {
			ResponseList<Status> listStatusAltoLabs = twitter.getUserTimeline(
					"@altolabs", new Paging(1, 10));
			ResponseList<Status> listStatusGlassFrance = twitter
					.getUserTimeline("@GlassFrance", new Paging(1, 10));
			ResponseList<Status> listStatusStartupVillage = twitter
					.getUserTimeline("@startupvillage", new Paging(1, 10));
			log.info("status retrieved from Twitter API");

			// USER INFO
			User altoLabs = new User();
			altoLabs.setId((int) listStatusAltoLabs.get(0).getUser().getId());
			altoLabs.setNickname("@altolabs");
			altoLabs.setName(listStatusAltoLabs.get(0).getUser().getName());

			User glassFrance = new User();
			glassFrance.setId((int) listStatusGlassFrance.get(0).getUser().getId());
			glassFrance.setNickname("@GlassFrance");
			glassFrance.setName(listStatusGlassFrance.get(0).getUser().getName());

			User startupVillage = new User();
			startupVillage.setId((int) listStatusStartupVillage.get(0).getUser().getId());
			startupVillage.setNickname("@startupvillage");
			startupVillage.setName(listStatusStartupVillage.get(0).getUser().getName());

			// parsing of created date
			Date altoParsed = null;
			Date glassParsed = null;
			Date villageParsed = null;
			String altoDate = listStatusAltoLabs.get(0).getUser()
					.getCreatedAt().toString();
			String glassDate = listStatusGlassFrance.get(0).getUser()
					.getCreatedAt().toString();
			String villageDate = listStatusStartupVillage.get(0).getUser()
					.getCreatedAt().toString();
			SimpleDateFormat formatReceived = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
			SimpleDateFormat formatWanted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				altoParsed = formatReceived.parse(altoDate);
				glassParsed = formatReceived.parse(glassDate);
				villageParsed = formatReceived.parse(villageDate);

				altoLabs.setJoinedDate(formatWanted.format(altoParsed));
				glassFrance.setJoinedDate(formatWanted.format(glassParsed));
				startupVillage.setJoinedDate(formatWanted.format(villageParsed));

			} catch (Exception e) {
				log.error("Exception: " + e.getMessage());
			}
			

			//TWEET INFO
			List<Tweet> listTweets = new ArrayList<Tweet>();
			for (Status a : listStatusAltoLabs) {
				Tweet monTweet = new Tweet();
				monTweet.setId((int) a.getId());
				monTweet.setAuthorId((int) a.getUser().getId());
				monTweet.setMessage(a.getText());
				
				Date dateCreationParsed = null;
				String dateCreation = a.getCreatedAt().toString();
				try {
					dateCreationParsed = formatReceived.parse(dateCreation);
					monTweet.setTweetDate(formatWanted.format(dateCreationParsed));
					listTweets.add(monTweet);
				} catch (Exception e) {
					log.error("Exception: " + e.getMessage());
				}
			}
			for (Status a : listStatusGlassFrance) {
				Tweet monTweet = new Tweet();
				monTweet.setId((int) a.getId());
				monTweet.setAuthorId((int) a.getUser().getId());
				monTweet.setMessage(a.getText());
				
				Date dateCreationParsed = null;
				String dateCreation = a.getCreatedAt().toString();
				try {
					dateCreationParsed = formatReceived.parse(dateCreation);
					monTweet.setTweetDate(formatWanted.format(dateCreationParsed));
					listTweets.add(monTweet);
				} catch (Exception e) {
					log.error("Exception: " + e.getMessage());
				}

			}
			for (Status a : listStatusStartupVillage) {
				Tweet monTweet = new Tweet();
				monTweet.setId((int) a.getId());
				monTweet.setAuthorId((int) a.getUser().getId());
				monTweet.setMessage(a.getText());
				
				Date dateCreationParsed = null;
				String dateCreation = a.getCreatedAt().toString();
				try {
					dateCreationParsed = formatReceived.parse(dateCreation);
					monTweet.setTweetDate(formatWanted.format(dateCreationParsed));
					listTweets.add(monTweet);
				} catch (Exception e) {
					log.error("Exception: " + e.getMessage());
				}

			}

			
			connectionToDb();
			int nbUsers = checkUserTb();
			String reqUser = null;
			String reqTweet = null;
			if (nbUsers == 0) {
				try {
					reqUser = "INSERT INTO isep_awt.isep_awt_user (user_id, name, nickname, joined_date)"
							+ "	VALUES ('"
							+ altoLabs.getId() +"','"
							+altoLabs.getName()+"','"
							+altoLabs.getNickname()+"','"
							+altoLabs.getJoinedDate()
							+ "'),('"
							+ glassFrance.getId() +"','"
							+glassFrance.getName()+"','"
							+glassFrance.getNickname()+"','"
							+glassFrance.getJoinedDate()
							+ "'),('"
							+ startupVillage.getId() +"','"
							+startupVillage.getName()+"','"
							+startupVillage.getNickname()+"','"
							+startupVillage.getJoinedDate()
							+ "');";
					stmt.executeUpdate(reqUser);

					reqTweet = "INSERT INTO isep_awt.isep_awt_tweet (tweet_id, author_id, message, tweet_date) VALUES ";
							
					for (Tweet t: listTweets){
								reqTweet = reqTweet+"('"+t.getId()+"','"+t.getAuthorId()+"','"+t.getMessage().replaceAll("'", "")+"','"+t.getTweetDate()+"'),";
							}
					reqTweet = reqTweet.substring(0, reqTweet.length()-1);
					stmt2.executeUpdate(reqTweet);

					log.trace("fake data inserted");
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (stmt != null && stmt2 != null) {
							conn.close();
						}
					} catch (SQLException se) {
					}
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException se) {
						se.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			log.error("no access to Twitter");
		}

	
	}

	public long getUserIdFromNickname(String nickname) {
		connectionToDb();
		log.debug("nickname = " + nickname);
		try {
			// execute query to retrieve id
			req = "SELECT user_id FROM isep_awt.isep_awt_user WHERE nickname = '"
					+ nickname + "'";
			rset = stmt.executeQuery(req);
			rset.next();
			return rset.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<Tweet> getTweets(long userid) {
		connectionToDb();
		log.debug("user ID = " + userid);
		// create the list of tweets to return
		List<Tweet> listOfTweets = new ArrayList<Tweet>();
		try {
			req = "SELECT * FROM isep_awt.isep_awt_tweet WHERE author_id = "
					+ userid;
			rset = stmt.executeQuery(req);

			// extract data from rset
			while (rset.next()) {
				Tweet twt = new Tweet();
				twt.setId(rset.getInt("tweet_id"));
				twt.setAuthorId(rset.getInt("author_id"));
				twt.setMessage(rset.getString("message"));
				twt.setTweetDate(rset.getString("tweet_date"));
				listOfTweets.add(twt);
			}
			log.trace(listOfTweets.size() + " tweets retrieved");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		return listOfTweets;
	}
}
