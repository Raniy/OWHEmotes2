package info.omgwtfhax.bukkitplugins.core;

import org.bukkit.Bukkit;

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
	
}
