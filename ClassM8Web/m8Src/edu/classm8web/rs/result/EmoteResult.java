package edu.classm8web.rs.result;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import edu.classm8web.database.dto.Emote;
import edu.classm8web.mapper.objects.MappedEmote;

@XmlRootElement
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
