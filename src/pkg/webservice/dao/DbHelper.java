package pkg.webservice.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pkg.webservice.beans.Tweet;
import pkg.webservice.beans.User;

import javax.xml.bind.annotation.XmlRootElement;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

@XmlRootElement
public class DbHelper {

	private static String DBURL = "jdbc:mysql://localhost:8889/isep_awt";
	private static String DBLOGIN = "isep_user";
	private static String DBPASSWORD = "isep_pwd";

	List<String> result = new ArrayList<String>();
	Connection conn = null;
	Statement stmt = null;
	Statement stmt2 = null;
	ResultSet rset = null;
	String req = null;

	public void connectionToDb() {

		// register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			// get a connection to the DB
			conn = (Connection) DriverManager.getConnection(DBURL, DBLOGIN,
					DBPASSWORD);
			stmt = (Statement) conn.createStatement();
			stmt2 = (Statement) conn.createStatement();
		} catch (SQLException e) {
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
			return rset.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public void updateData() {
		connectionToDb();
		int nbUsers = checkUserTb();
		String reqUser = null;
		String reqTweet = null;
		if (nbUsers == 0) {
			try {
				reqUser = "INSERT INTO isep_awt.isep_awt_user (name, nickname, joined_date)"
						+ "	VALUES ('doriane selva', '@dodo', '2010-09-04 15:10:35'),"
						+ " ('djeffrey pierre', '@dpierre', '2014-09-04 03:01:04'),"
						+ " ('sarah marcon', '@sma', '2014-06-30 14:36:21'),"
						+ " ('jonh doe', '@jdk', '2006-03-10 21:04:12'),"
						+ " ('pierre manu', '@pmn','2006-05-14 15:40:00'),"
						+ " ('alain','@altolabs','2004-10-12 12:35:04');";
				stmt.executeUpdate(reqUser);

				reqTweet = "INSERT INTO isep_awt.isep_awt_tweet (author_id, message, tweet_date)"
						+ " VALUES ((select user_id from isep_awt.isep_awt_user where nickname='@dpierre'),'this is my first tweet hehe ^^','2014-09-04 03:02:00'),"
						+ " ((select user_id from isep_awt.isep_awt_user where nickname='@sma'),'@dpierre bienvenue !', '2014-09-04 08:35:14');";
				stmt2.executeUpdate(reqTweet);
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
	}

	public List<User> getUserIdFromNickname(String nickname) {
		connectionToDb();
		List<User> user = new ArrayList<User>();

		try {
			// execute query to retrieve id
			req = "SELECT user_id FROM isep_awt.isep_awt_user WHERE nickname = '"
					+ nickname + "'";
			rset = stmt.executeQuery(req);

			// extract data FROM rset
			while (rset.next()) {
				User uzr = new User();
				uzr.setId(rset.getInt("user_id"));
				user.add(uzr);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<Tweet> getTweets(long userid) {
		connectionToDb();
		// create the list of tweets to return
		List<Tweet> listOfTweets = new ArrayList<Tweet>();
		Tweet twt = new Tweet();
		try {
			req = "SELECT * FROM isep_awt.isep_awt_tweet WHERE author_id = "
					+ userid;
			rset = stmt.executeQuery(req);

			// extract data from rset
			while (rset.next()) {
				twt.setId(rset.getInt("tweet_id"));
				twt.setAuthorId(rset.getInt("author_id"));
				twt.setMessage(rset.getString("message"));
				twt.setTweetDate(rset.getString("tweet_date"));
				listOfTweets.add(twt);
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
