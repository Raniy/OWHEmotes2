package info.omgwtfhax.bukkitplugins.core;

import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

import java.util.HashMap;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.RegisteredServiceProvider;


public class BukkitPlugin extends org.bukkit.plugin.java.JavaPlugin{ 
	// Vault stuffs.
	
	public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;

    // Internal list of Permission Nodes!
    private static HashMap<String,PermissionNode> permissionNodes = new HashMap<String,PermissionNode>();
    
	
	public org.bukkit.configuration.file.FileConfiguration getMyConfig()
	{	/*
		 * Wrapper for Bukkit's built in FileConfiguration pluginname/config.yml Config File
		 */
		return this.getConfig();
	}
	
	public void saveMyConfig()
	{	/*
		 * Wrapper for Bukkit's built in FileConfiguration pluginname/config.yml Config File
		 */
		
		this.saveConfig();
	}
	
	public void serializeEmotes()
	{
		ConfigurationSerialization.registerClass(Emote.class, "Emote");
	}
	
	public org.bukkit.entity.Player[] getOnlinePlayers(){
		try 
		{
			return this.getServer().getOnlinePlayers();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean sendToPlayer(String player, String msg)
	{	/*
	 	 * Wrap the broadcast to player function
	 	 */	
		
		//Anytime we are working with Craft/Bukkit, do Error handling!!!	
		try {
			Bukkit.getServer().getPlayer(player).sendMessage(msg); // Send msg to player with that name.
				
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Somehow we were unable to send a message to the player!
		}
			return true;
	}
	
	public boolean sendToAll(String msg)
	{	/*
	 	 *Wrap the broadcast function
	 	 */
		
		try{
			this.getServer().broadcastMessage(msg);
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean dispatchCommand(String player, String command) // The command string should include any desired args.
	{
		return this.getServer().dispatchCommand(Bukkit.getPlayer(player), command);
	}
	
	public boolean consoleInfo(String msg)
	{	/* 
	 	 *	Wrapper for Bukkits built in Console Messaging
	 	 */
		
		try {
			this.getLogger().info(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

    protected boolean setupChat()
    {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }

        return (chat != null);
    }

    protected boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
    
    public boolean playerHasNode(String player, String node)
    {
    	// Check Vault
    	if(BukkitPlugin.permission != null) // Always check for null!
    	{
    		if(BukkitPlugin.permission.has(Bukkit.getServer().getPlayer(player), node)) return true;
    	}
    	
    	// Check SuperPerms
    	if(Bukkit.getServer().getPlayer(player).hasPermission(node)) return true;
    	
    	// For now, Ops always have permission.
    	if(Bukkit.getServer().getPlayer(player).isOp()) return true;
    	
    	// Return False
    	return false;
    }

	public static HashMap<String,PermissionNode> getPermissionNodes() {
		return permissionNodes;
	}

	public static void setPermissionNodes(HashMap<String,PermissionNode> permissionNodes) {
		BukkitPlugin.permissionNodes = permissionNodes;
	}


}
