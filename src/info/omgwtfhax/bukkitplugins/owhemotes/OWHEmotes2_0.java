package info.omgwtfhax.bukkitplugins.owhemotes;

import java.util.ArrayList;
import java.util.List;

import info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers.HardMode;
import info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers.SoftMode;

public class OWHEmotes2_0 extends info.omgwtfhax.bukkitplugins.core.BukkitPlugin{
	public enum Mode 
	{ 
		HARD, SOFT; 
	}
	
	// Default MODE
	private Mode mode = Mode.HARD;
	
	// List of Emotes
	private List<Emote> myEmotes = null;
	
	//Command Handlers
	SoftMode cmdHandlerSoft = null;
	HardMode cmdHandlerHard = null;
	
	// Plugin Loading 
	@Override
	public void onEnable()
	{
		this.consoleInfo("Enabling...");
		
		this.myEmotes = new ArrayList<Emote>();
		
		this.getEmotesfromConfig();
		this.setMode(this.getModeFromConfig());
		this.consoleInfo("Enabled.");

	}

	// Plugin Unloading
	@Override
	public void onDisable()
	{
		this.consoleInfo("Disabling...");
		
		// Add any emotes that are new to the list in Memory to the list in the Config.
		this.addEmotesToConfig();
		
		// Save Config.
		this.saveMyConfig();
		
		this.consoleInfo("Disabled.");
	}
	
	// Plugin Logic
	
	// Getters and Setters
	private List<String> getEmotesfromConfig()
	{
		// TODO Get the current list of Emotes from the Config.yml file	
		return null;
	}
	
	private Mode getModeFromConfig()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public Mode getMode()
	{
		return mode;
	}
	
	public List<Emote> getMyEmotes()
	{
		return myEmotes;
	}
	
	public void setMyEmotes(List<Emote> myEmotes)
	{
		this.myEmotes = myEmotes;
	}
	
	private void addEmotesToConfig()
	{
		// TODO Add the current list of Emotes to the Config.yml file
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
	}
	
}