package info.omgwtfhax.bukkitplugins.owhemotes;

import java.util.ArrayList;
import java.util.List;


public class OWHEmotes2_0 extends info.omgwtfhax.bukkitplugins.core.BukkitPlugin{
	public enum Mode 
	{ 
		HARD, SOFT; 
	}
	
	// Default MODE
	private Mode mode = Mode.HARD;
	
	// List of Emotes
	private List<Emote> myEmotes = null;
	
	// Plugin Loading 
	@Override
	public void onEnable()
	{
		this.consoleInfo("Enabling...");
		
		// Instance null variables
		this.myEmotes = new ArrayList<Emote>();
		
		
		// Load variables from Config
		this.getEmotesfromConfig();
		this.setMode(this.getModeFromConfig());
		
		// Start listening for commands.
		this.startCommanHandler();
		
		this.consoleInfo("Enabled.");

	}

	private void startCommanHandler() {
		// First: Make sure we are listening for our Add and Remove emote commands. Those being basic Bukkit style thingies it should be easy.
		
		// Second Figure out which mode we are in
		switch(this.getMode())
		{
			case HARD: // Yay, we like it HARD! 
				// Do whatever we do to listen for Emotes in Hard mode
		
			case SOFT:	// Eww... SOFT is for Nublets that only use Bukkit API to do things!
				// Start a PreCommand Event listener to catch people trying to do Emotes.
		}
		
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