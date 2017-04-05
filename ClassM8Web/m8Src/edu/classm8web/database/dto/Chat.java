package edu.classm8web.database.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.*;

import java.sql.Timestamp;

import edu.classm8web.comparator.BackwardsDateComparator;

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
	
	public List<Message> getMessagesAfterDate(Timestamp date){
		List<Message> recentMessages = new ArrayList<Message>();
		List<Message> backwards = new ArrayList<Message>();
		backwards.addAll(messages);
		backwards.sort(new BackwardsDateComparator());
		
		for(Message m : backwards){
			if(m.getDateTime().after(date)){
				recentMessages.add(m);
			}
		}
		
		return recentMessages;
		
	}
	
	
	
   
}
