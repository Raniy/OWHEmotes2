package info.omgwtfhax.bukkitplugins.owhemotes;

import java.util.List;

import info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers.HardMode;
import info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers.SoftMode;

public class OWHEmotes2_0 extends info.omgwtfhax.bukkitplugins.core.BukkitPlugin{
	public enum mode 
	{ 
		HARD, SOFT; 
	}
	
	// List of Emotes
	private List<Emote> myEmotes = null;
			
	//Command Handlers
	SoftMode cmdHandlerSoft = null;
	HardMode cmdHandlerHard = null;
	
	// Getters and Setters
	
	public List<Emote> getMyEmotes() {
		return myEmotes;
	}
	public void setMyEmotes(List<Emote> myEmotes) {
		this.myEmotes = myEmotes;
	}
	
	
}
