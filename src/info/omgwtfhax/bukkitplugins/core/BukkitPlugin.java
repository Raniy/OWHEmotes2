package info.omgwtfhax.bukkitplugins.core;

import java.lang.reflect.Field;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;


import org.bukkit.craftbukkit.v1_6_R2.CraftServer; // HARD MODE REFERENCE, SUBJECT TO RANDOM CHANGES!!!
import org.bukkit.plugin.RegisteredServiceProvider;


public class BukkitPlugin extends org.bukkit.plugin.java.JavaPlugin{ 
	// Vault stuffs.
	
	public static Permission permission = null;
    public static Economy economy = null;
    public static Chat chat = null;
	
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
	
	protected boolean sendToPlayer(String player, String msg)
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
	
	protected boolean consoleInfo(String msg)
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
	
	public SimpleCommandMap getCommandMap() // WARNING MAY RETURN ' null ' ALWAYS CHECK!!!
	{	/*
		 *  Hacky reflection based method of working with CraftBukkit commands. Bukkit API is too limited!
		 */
		
		SimpleCommandMap cmap = null;

		//Anytime we are working with Bukkit or CraftBukkit, do Error handling!!!
		try{
			if(Bukkit.getServer() instanceof CraftServer){
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				
				f.setAccessible(true);
				cmap = (SimpleCommandMap) f.get(Bukkit.getServer());
				
		
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return cmap; // May be a valid CraftBukkit Map, may not be.
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

}
