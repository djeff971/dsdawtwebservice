package pkg.webservice.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Tweet {
	
	private int id;
	private int authorId;
	private String message;
	private String tweetDate;
	
	public Tweet(){
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTweetDate() {
		return tweetDate;
	}

	public void setTweetDate(String tweetDate) {
		this.tweetDate = tweetDate;
	}

}
