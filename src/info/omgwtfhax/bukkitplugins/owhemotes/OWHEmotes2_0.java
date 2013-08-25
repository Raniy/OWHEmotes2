package info.omgwtfhax.bukkitplugins.owhemotes;

import info.omgwtfhax.bukkitplugins.core.PermissionNode;
import info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers.BaseCommands;
import info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers.HardMode;
import info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers.SoftMode;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.EmoteCommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class OWHEmotes2_0 extends info.omgwtfhax.bukkitplugins.core.BukkitPlugin{
	private List<String> defaultCommands = new ArrayList<String>();		
	
	// Default Modes
	private boolean hardMode = false; // if True then the CraftBukkit kludge will be used. NOT SUGGESTED bY THE BUKKIT DEV TEAM!!! EH, Screw em.
	private boolean noConfig = true; // if True then do not ever store Emotes. They will disappear at restart. DO NOT COMBINE WITH THE FOLLOWING!
	private boolean defaultEmotes = false; // if False then the default emotes will never be written into the memory. DO NOT COMBINE WITH THE ABOVE!
	
	// List of Emotes
	private List<Emote> myEmotes = null;
	
	// Default Commands Handler
	private BaseCommands baseCommands = null;
	private SoftMode softModeListener = null;
	private HardMode hardModeExecutor = null;

	// Plugin Loading 
	@Override
	public void onEnable()
	{
		this.consoleInfo("Enabling...");
		
		// Process Config
		this.processConfig(this.getMyConfig());
		
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
		
		// Save Config. 
		this.saveMyConfig();
		
		this.consoleInfo("Disabled.");
	}
	
	// Plugin Logic
	public void startCommanHandler()
	{	
		// First: Make sure we are listening for our Default commands.
		for(String cmd:this.getDefaultCommands())
		{
			this.getCommand(cmd).setExecutor(baseCommands);
		}
		
		// Second Figure out which mode we are in
		if(this.isHardMode())
		{
			System.out.println("Enabling hardmode handler");
			// Start HARD Mode command Handler
			for(Emote emote:this.getMyEmotes())
			{
				this.getCommand(emote.getCommand()).setExecutor(this.getHardModeExecutor());
			}
		} else {
			// Start SOFT Mode command listener
			System.out.println("Enabling softmode listener");
			getServer().getPluginManager().registerEvents(this.getSoftModeListener(),this);
		}
		
	}

	private void processConfig(FileConfiguration myConfig)
	{	
		// Instance null variables
		this.myEmotes = new ArrayList<Emote>();
		this.baseCommands = new BaseCommands(this);
		this.hardModeExecutor = new HardMode(this);
		this.softModeListener = new SoftMode(this);
		
		// Add our default commands to our internal list.
		defaultCommands.add("addemote");
		defaultCommands.add("deleteemote");
		defaultCommands.add("listemotes");
		defaultCommands.add("reloademotes");
		
		// Setup basic permissions.
		getPermissionNodes().put("base",new PermissionNode("omgwtfhax.emotes"));
		getPermissionNodes().put("moderator",new PermissionNode("omgwtfhax.emotes.moderator"));
		getPermissionNodes().put("reload",new PermissionNode("omgwtfhax.emotes.reload"));
			
		// Load Modes from Config
		this.getModesFromConfig();
		
		// Load Emotes
		this.getEmotes();
	}

	private List<Emote> getDefaultEmotes()
	{
		List<Emote> emotes = new ArrayList<Emote>();
		if(this.isDefaultEmotes())
		{
			// Loading default Emotes into List!
			
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
			// Allowed to load emotes from Config
			getEmotesFromConfig();
			
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
		System.out.println(this.getMyEmotes());
		
	}
	// Create a new command with name of emote. Returns true if successfully made
	public boolean createEmoteCommand(Emote emote)
	{
		
		try{
		
			SimpleCommandMap cmap = getCommandMap();
			
			//if cmap exists, register (add) command to it
			if(cmap != null){
				
				//Make sure the emote isn't already registered
				if(cmap.getCommand(emote.getCommand()) != null){				
					if(cmap.getCommand(emote.getCommand()).isRegistered())
						return false;
				}
				
				cmap.register(emote.getCommand(),new EmoteCommand(emote, this.getHardModeExecutor()));
				return true;
			
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
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
		this.setHardMode(this.getMyConfig().getBoolean("OWH.Emotes.Use_CraftBukkit_Reflection_Or_BukkitAPI", this.isHardMode()));
		this.setNoConfig(this.getMyConfig().getBoolean("OWH.Emotes.EmoteSaving",this.isNoConfig()));
		this.setDefaultEmotes(this.getMyConfig().getBoolean("OWH.Emotes.DefaultEmotes",this.isDefaultEmotes()));
	}
	
	private List<String> getEmotesFromConfig()
	{
		// TODO Get the current list of Emotes from the Config.yml file	
		return null;
	}
	
	public BaseCommands getBaseCommands() {
		return baseCommands;
	}



	public HardMode getHardModeExecutor() {
		return hardModeExecutor;
	}



	public void setHardMode(boolean hardMode) {
		this.hardMode = hardMode;
	}



	public void setBaseCommands(BaseCommands baseCommands) {
		this.baseCommands = baseCommands;
	}



	public void setSoftModeListener(SoftMode softModeListener) {
		this.softModeListener = softModeListener;
	}



	public void setHardModeExecutor(HardMode hardModeExecutor) {
		this.hardModeExecutor = hardModeExecutor;
	}



	public List<String> getDefaultCommands() {
		return defaultCommands;
	}



	public void setDefaultCommands(List<String> defaultCommands) {
		this.defaultCommands = defaultCommands;
	}



	public boolean isHardMode()
	{
		return hardMode;
	}
	
	public List<Emote> getMyEmotes()
	{
		return myEmotes;
	}
	
	public void setMyEmotes(List<Emote> myEmotes)
	{
		this.myEmotes = myEmotes;
	}
	
	@SuppressWarnings("unused") // Needs to be overhauled
	// TODO: Overhaul to not save Defaults. 
	private void addEmotesToConfig()
	{
		// First remove any Defaults...
		this.getMyConfig().set("owh.emotes.list", this.getMyEmotes());
		
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

}