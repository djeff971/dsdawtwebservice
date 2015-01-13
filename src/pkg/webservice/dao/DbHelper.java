package pkg.webservice.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pkg.webservice.beans.Tweet;
import pkg.webservice.beans.User;
import pkg.webservice.first.RESTRessource;

import javax.xml.bind.annotation.XmlRootElement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@XmlRootElement
public class DbHelper {

	private static String DBURL = "jdbc:mysql://localhost/isep_awt";
	private static String DBLOGIN = "root";
	private static String DBPASSWORD = "root";

	List<String> result = new ArrayList<String>();
	Connection conn = null;
	Statement stmt = null;
	ResultSet rset = null;
	String req = null;

	public List<User> getUsers() {

		// create the list of users to return
		List<User> listOfUsers = new ArrayList<User>();

		try {
			// register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// get a connection to the DB
			conn = (Connection) DriverManager.getConnection(DBURL, DBLOGIN,
					DBPASSWORD);

			// execute query to retrieve users
			stmt = (Statement) conn.createStatement();
			req = "select * from isep_awt.isep_awt_user";
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

	/*
	 * public int checkUserTb() { try { // register JDBC driver
	 * Class.forName("com.mysql.jdbc.Driver"); // get a connection to the DB
	 * conn = (Connection) DriverManager.getConnection(DBURL, DBLOGIN,
	 * DBPASSWORD);
	 * 
	 * // execute query to return the nb of lines in user table stmt =
	 * (Statement) conn.createStatement(); req =
	 * "select count(*) as nb from isep_awt.isep_awt_user;"; rset =
	 * stmt.executeQuery(req); return rset.getInt("nb"); } catch (Exception e) {
	 * e.printStackTrace(); } return -1; }
	 * 
	 * public int checkTweetTb() { try { // register JDBC driver
	 * Class.forName("com.mysql.jdbc.Driver"); // get a connection to the DB
	 * conn = (Connection) DriverManager.getConnection(DBURL, DBLOGIN,
	 * DBPASSWORD);
	 * 
	 * // execute query to return the nb of lines in user table stmt =
	 * (Statement) conn.createStatement(); req =
	 * "select count(*) as nb from isep_awt.isep_awt_tweet;"; rset =
	 * stmt.executeQuery(req); return rset.getInt("nb"); } catch (Exception e) {
	 * e.printStackTrace(); } return -1; }
	 * 
	 * public void insertTweetFakeData() { try { // register JDBC driver
	 * Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * // get a connection to the DB conn = (Connection)
	 * DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
	 * 
	 * 
	 * // execute query of multi-insertion stmt = (Statement)
	 * conn.createStatement(); req =
	 * "INSERT INTO isep_awt.isep_awt_tweet (author_id, message, tweet_date)" +
	 * "	VALUES (2,'this is my first tweet hehe ^^','2014-09-04 03:02:00')," +
	 * " (3,'@dpierre bienvenue !', '2014-09-04 08:35:14');";
	 * stmt.executeUpdate(req);
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } finally { try { if (stmt != null) { conn.close();
	 * } } catch (SQLException se) { } try { if (conn != null) { conn.close(); }
	 * } catch (SQLException se) { se.printStackTrace(); } } }
	 * 
	 * public void insertUserFakeData() { try { // register JDBC driver
	 * Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * // get a connection to the DB conn = (Connection)
	 * DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
	 * 
	 * // execute query of multi-insertion stmt = (Statement)
	 * conn.createStatement(); req =
	 * "INSERT INTO isep_awt.isep_awt_user (name, nickname, joined_date)" +
	 * "	VALUES ('doriane selva', '@dodo', '2010-09-04 15:10:35')," +
	 * " ('djeffrey pierre', '@dpierre', '2014-09-04 03:01:04')," +
	 * " ('sarah marcon', '@sma', '2014-06-30 14:36:21')," +
	 * " ('jonh doe', '@jdk', '2006-03-10 21:04:12')," +
	 * " ('pierre manu', '@pmn','2006-05-14 15:40:00')," +
	 * " ('alain','@altolabs','2004-10-12 12:35:04');"; stmt.executeUpdate(req);
	 * } catch (SQLException e) { e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); } finally { try { if (stmt != null) { conn.close();
	 * } } catch (SQLException se) { } try { if (conn != null) { conn.close(); }
	 * } catch (SQLException se) { se.printStackTrace(); } } }
	 * 
	 * public void updateData() { if (checkUserTb() == 0) {
	 * insertUserFakeData(); } if (checkTweetTb() == 0) { insertTweetFakeData();
	 * } }
	 */

	/*
	 * public List<Tweet> getTweets(String nickname) {
	 * 
	 * // create the list of tweets to return List<Tweet> listOfTweets = new
	 * ArrayList<Tweet>(); Tweet twt = new Tweet();
	 * 
	 * try { // register JDBC driver Class.forName("com.mysql.jdbc.Driver");
	 * 
	 * // get a connection to the DB log.info("connecting to db..."); conn =
	 * (Connection) DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
	 * log.info("connected to DB !");
	 * 
	 * // execute query to retrieve users stmt = (Statement)
	 * conn.createStatement(); req = "select * from isep_awt.isep_awt_tweet" +
	 * " INNER JOIN isep_awt.isep_awt_user ON isep_awt.isep_awt_user.user_id = isep_awt.isep_awt_tweet.author_id"
	 * + " WHERE isep_awt.isep_awt_user.nickname = '@" + nickname + "'"; rset =
	 * stmt.executeQuery(req); log.info("data retrieved");
	 * 
	 * //extract data from rset while(rset.next()){
	 * twt.setId(rset.getInt("tweet_id"));
	 * twt.setAuthorId(rset.getInt("author_id"));
	 * twt.setMessage(rset.getString("message"));
	 * twt.setTweetDate(rset.getString("tweet_date")); listOfTweets.add(twt);
	 * log.info("tweet added to list"); } } catch (SQLException e) {
	 * log.error("SQL error"); e.printStackTrace(); } catch (Exception e) {
	 * log.error("impossible to register JDBC driver"); e.printStackTrace(); }
	 * finally { try { if (stmt != null) { conn.close();
	 * log.info("connection closed !"); } } catch (SQLException se) {
	 * log.error("impossible to close SQL connection"); } try { if (conn !=
	 * null) { conn.close(); log.info("connection closed !"); } } catch
	 * (SQLException se) { log.error("impossible to close SQL connection");
	 * se.printStackTrace(); } }
	 * 
	 * return listOfTweets; }
	 */

}
