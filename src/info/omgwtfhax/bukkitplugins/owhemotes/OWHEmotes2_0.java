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
		
		// Make sure Vault is working...
		if(!(this.setupPermissions())) this.consoleInfo("Failed to setup Vault Permisions...");
		if(!(this.setupEconomy())) this.consoleInfo("Failed to setup Vault Economy...");
		if(!(this.setupChat())) this.consoleInfo("Failed to setup Vault Chat...");
		
		// Start listening for commands.
		this.startCommanHandler();
		
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
	public void startCommanHandler() {
		// First: Make sure we are listening for our Add and Remove emote commands. Those being basic Bukkit style thingies it should be easy.
		
		// Second Figure out which mode we are in
		if((this.getMode() == Mode.HARD))
		{
			// Start HARD Mode command Handler
			
		} else if((this.getMode() == Mode.SOFT))
		{
			// Start SOFT Mode command Handler
		}
		
	}

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