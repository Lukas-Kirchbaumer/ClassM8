package com.example.backend.Results;

import com.example.backend.MappedObjects.MappedEmote;

import java.util.List;

public class EmoteResult extends Result {
	private List<MappedEmote> emotes;
	
	public EmoteResult() {}
	
	public List<MappedEmote> getEmotes() {
		return emotes;
	}
	
	public void setEmotes(List<MappedEmote> ids) {
		this.emotes = ids;
	}
	
}
