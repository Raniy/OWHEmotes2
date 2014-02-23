package info.omgwtfhax.bukkitplugins.owhemotes;

import info.omgwtfhax.bukkitplugins.core.PermissionNode;
import info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers.BaseCommands;
import info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers.SoftMode;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;

public class OWHEmotes2_0 extends info.omgwtfhax.bukkitplugins.core.BukkitPlugin{
	private List<String> defaultCommands = new ArrayList<String>();		
	
	// Default Modes
	private boolean noConfig = false; // if True then do not ever store Emotes. They will disappear at restart. DO NOT COMBINE WITH THE FOLLOWING!
	private boolean defaultEmotes = true; // if False then the default emotes will never be written into the memory. DO NOT COMBINE WITH THE ABOVE!
	
	// List of Emotes
	private List<Emote> myEmotes = null;
	
	// Default Commands Handler
	private BaseCommands baseCommands = null;
	private SoftMode softModeListener = null;

	// Plugin Loading 
	@Override
	public void onEnable()
	{
		this.consoleInfo("Enabling...");
		
		this.serializeEmotes();
		
		// Process Config
		this.processConfig();
		
		// Make sure Vault is working...
		if(!(this.setupPermissions())) this.consoleInfo("Failed to setup Vault Permisions...");
		if(!(this.setupEconomy())) this.consoleInfo("Failed to setup Vault Economy...");
		if(!(this.setupChat())) this.consoleInfo("Failed to setup Vault Chat...");
		
		// Start listening for commands.
		this.startCommandHandler();
		
		this.consoleInfo("Enabled.");

	}

	// Plugin Unloading
	@Override
	public void onDisable()
	{
		this.consoleInfo("Disabling...");
		
		this.addEmotesToConfig();
		
		// Save Config. 
		this.saveMyConfig();
		
		this.consoleInfo("Disabled.");
	}
	
	// Plugin Logic
	public void startCommandHandler()
	{	
		// First: Make sure we are listening for our Default commands.
		for(String cmd:this.getDefaultCommands())
		{
			this.getCommand(cmd).setExecutor(baseCommands);
		}
		
		// Start SOFT Mode command listener
		getServer().getPluginManager().registerEvents(this.getSoftModeListener(),this);
		
	}

	public void processConfig()
	{	
		// Instance null variables
		this.myEmotes = new ArrayList<Emote>();
		this.baseCommands = new BaseCommands(this);
		this.softModeListener = new SoftMode(this);
		
		// Add our default commands to our internal list.
		defaultCommands.add("addemote");
		defaultCommands.add("deleteemote");
		defaultCommands.add("listemotes");
		defaultCommands.add("reloademotes");
		defaultCommands.add("emoteall");
		
		// Setup basic permissions.
		getPermissionNodes().put("base",new PermissionNode("omgwtfhax.emotes"));
		getPermissionNodes().put("moderator",new PermissionNode("omgwtfhax.emotes.moderator"));
		getPermissionNodes().put("reload",new PermissionNode("omgwtfhax.emotes.reload"));
			
		// Load Modes from Config
		this.getModesFromConfig();
		
		// Load Emotes
		this.getEmotes();
	}

	@SuppressWarnings("unchecked")
	private List<Emote> getDefaultEmotes()
	{
		List<Emote> emotes = new ArrayList<Emote>();
		
		if(this.isDefaultEmotes())
		{
			// Loading default Emotes into List!
			if(this.getMyConfig().contains("OWH.Emotes.Defaults"))
			{
				emotes = (List<Emote>)this.getMyConfig().getList("OWH.Emotes.Defaults");

				for(Emote e : emotes)
					e.setDefault(true);
			}
			else
			{
				Emote e = new Emote(); // An object to store an emote in until it is added to emotes list
				e.setDefault(true);
				emotes.add(e);
			}
		} 
		
		// DEFAULT EMOTE EMERGENCY!!!
		if( (this.isNoConfig()) && (!(this.isDefaultEmotes())) )
		{
			emotes.add(new Emote()); // Make sure we always have atleast one emote!
		}
		
		return emotes;
	}
	
	private void getEmotes() 
	{	
		if(!(this.isNoConfig())) // Check if we are loading stored emotes. 
		{
			@SuppressWarnings("unchecked")
			List<Emote> emotes = (List<Emote>)this.getMyConfig().getList("OWH.Emotes.Emotes");
			
			if(emotes != null)
			{
				this.setMyEmotes(emotes);
			}		
		}		
		getDefaultEmotes();
	}
	
	public void addEmoteToList(Emote emote)
	{
		// Check through list for Emote
		for(Emote em:this.getMyEmotes())
		{
			if((em.getCommand().contentEquals(emote.getCommand())) && (em.getStyle().equals(emote.getStyle())))  // Emote is in the list.
			{
				// Duplicate Emote!
				return;
			}
		}
		
		// Add this emote to the list.
		this.getMyEmotes().add(emote);
	}
	
	//unregister command from command map. Returns true if successfully removed
	public boolean removeCommand(Emote emote)
	{
		try{
			SimpleCommandMap cmap = this.getCommandMap();
			
			//If cmap exists, unregister (remove) command from it
			if(cmap != null){
				
				for(Emote e : this.getMyEmotes()){
					
					//Check that the specified emote is an actual emote,
					//otherwise any command may be deleted
					if((e.getCommand().contentEquals(emote.getCommand())) && (e.getStyle().equals(emote.getStyle())))
					{
					    // This is our Emote, lets get rid of it.
						cmap.getCommand(e.getCommand()).unregister(cmap);
						this.getMyEmotes().remove(e);
						
						return true;
					}
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	// Getters and Setters
	private void getModesFromConfig() 
	{
		this.setNoConfig(this.getMyConfig().getBoolean("OWH.Emotes.EmoteSaving",this.isNoConfig()));
		this.setDefaultEmotes(this.getMyConfig().getBoolean("OWH.Emotes.DefaultEmotes",this.isDefaultEmotes()));
	}
	
	public BaseCommands getBaseCommands() {
		return baseCommands;
	}

	public void setBaseCommands(BaseCommands baseCommands) {
		this.baseCommands = baseCommands;
	}



	public void setSoftModeListener(SoftMode softModeListener) {
		this.softModeListener = softModeListener;
	}



	public List<String> getDefaultCommands() {
		return defaultCommands;
	}



	public void setDefaultCommands(List<String> defaultCommands) {
		this.defaultCommands = defaultCommands;
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
		List<Emote> nonDefaults = this.getMyEmotes();
		List<Emote> defaults = new ArrayList<Emote>();
		
		for(Emote e : nonDefaults)
		{
			if(e.isDefault())
			{
				nonDefaults.remove(e);
				defaults.add(e);
			}
		}

		this.getMyConfig().set("OWH.Emotes.Emotes", nonDefaults);
		this.getMyConfig().set("OWH.Emotes.Defaults", defaults);
		
	} 
	
	public Listener getSoftModeListener() {
		return softModeListener;
	}
	
	// Mode Setters/Getters
	public boolean isNoConfig() {
		return noConfig;
	}

	public void setNoConfig(boolean noConfig) {
		this.noConfig = noConfig;
	}

	public boolean isDefaultEmotes() {
		return defaultEmotes;
	}

	public void setDefaultEmotes(boolean defaultEmotes) {
		this.defaultEmotes = defaultEmotes;
	}

	public String arrayToString(int startpoint, String[] args)
	{
		//Return a string made from an array seperated by spaces. Startpoint will exclude starting objects in the array.
		String message = "";
		for(int i = startpoint; i < args.length; i++){
			message = message + args[i] + " ";
		}
		return message;
	}

}