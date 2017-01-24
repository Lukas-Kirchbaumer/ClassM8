package edu.classm8web.rs.result;

import java.util.Vector;

import javax.xml.bind.annotation.XmlRootElement;

import edu.classm8web.database.dto.Chat;

@XmlRootElement
public class ChatResult extends Result {

	private Chat schoolclassChat;
	
	public ChatResult() {}

	public Chat getSchoolclassChat() {
		return schoolclassChat;
	}

	public void setSchoolclassChat(Chat schoolclassChat) {
		this.schoolclassChat = schoolclassChat;
	}
	
	
}
