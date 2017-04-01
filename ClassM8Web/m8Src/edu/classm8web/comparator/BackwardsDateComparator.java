package edu.classm8web.comparator;

import java.util.Comparator;

import edu.classm8web.database.dto.Message;

public class BackwardsDateComparator implements Comparator<Message> {

	@Override
	public int compare(Message arg0, Message arg1) {
		return arg0.getDateTime().compareTo(arg1.getDateTime()) * -1;
	}

}
