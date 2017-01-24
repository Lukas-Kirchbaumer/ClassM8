package edu.classm8web.database.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Chat
 *
 */
@Entity
public class Chat implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8413523108574250877L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="chatid")
	private long id;
	
	@ElementCollection
	private List<Message> messages;

	public Chat() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
	
   
}
