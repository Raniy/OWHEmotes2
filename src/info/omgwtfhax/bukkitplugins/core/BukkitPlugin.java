package info.omgwtfhax.bukkitplugins.core;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;


import org.bukkit.craftbukkit.v1_6_R2.CraftServer; // HARD MODE REFERENCE, SUBJECT TO RANDOM CHANGES!!!


public class BukkitPlugin extends org.bukkit.plugin.java.JavaPlugin{
	
	
	// Wrap the built in Config.yml file
	org.bukkit.configuration.file.FileConfiguration myConfig = null;
	
	// Wrap the broadcast to player function
	boolean sendToPlayer(String player, String msg)
	{
		//Anytime we are working with Bukkit or CraftBukkit, do Error handling!!!	
		try {
			Bukkit.getServer().getPlayer(player).sendMessage(msg); // Send msg to player with that name.
				
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Somehow we were unable to send a message to the player!
		}
			return true;
	}
	
	public SimpleCommandMap getCommandMap() // WARNING MAY RETURN ' null ' ALWAYS CHECK!!!
	
	{
		SimpleCommandMap cmap = null;
		try{
			if(Bukkit.getServer() instanceof CraftServer){
				final Field f = CraftServer.class.getDeclaredField("commandMap");
				
				f.setAccessible(true);
				cmap = (SimpleCommandMap) f.get(Bukkit.getServer());
				
		
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return cmap;
	}
}
