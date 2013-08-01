package info.omgwtfhax.bukkitplugins.owhemotes;

import info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers.SoftMode;

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
	
	// Base Permission Node
	private static String NODE_BASE = "omgwtfhax.emotes";
	private String nodeMod = getNodeBase() + ".mod";
	
	
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
		
		// For Testing ONLY add a default Emote to the list.
		this.myEmotes.add(new Emote());
		
		
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
			getServer().getPluginManager().registerEvents(new SoftMode(this), this);
		}
		
	}
	
	/*public boolean doEmoteByPlayer(String player,String emoteCommand)
	{
		// Iterate through the list of valid commands, see if this is one.
		for(Emote emote:this.myEmotes)
		{
			if(emote.getCommand().equalsIgnoreCase(emoteCommand))
			{
				if(playerHasNode(player,nodeBase+"."+emote.getCommand().toLowerCase()))
				{
					
				}
				
			}
		}
		
		return false;
	}*/

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
		this.getMyConfig().set("owh.emotes.list", this.getMyEmotes());
		
	}

	public void setMode(Mode mode)
	{
		this.mode = mode;
	}

	public String getNodeMod() {
		return nodeMod;
	}

	public void setNodeMod(String nodeMod) {
		this.nodeMod = nodeMod;
	}

	public static String getNodeBase() {
		return NODE_BASE;
	}

	public void setNodeBase(String nodeBase) {
		NODE_BASE = nodeBase;
	}

	
}