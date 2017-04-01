package edu.classm8web.rs.result;

import java.util.List;

import edu.classm8web.database.dto.Emote;

public class EmoteResult extends Result {
	private List<Emote> emotes;
	
	public EmoteResult() {}
	
	public List<Emote> getEmotes() {
		return emotes;
	}
	
	public void setEmotes(List<Emote> ids) {
		this.emotes = ids;
	}
	
}
