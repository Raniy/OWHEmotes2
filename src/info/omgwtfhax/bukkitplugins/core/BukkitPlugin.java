package info.omgwtfhax.bukkitplugins.core;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;


import org.bukkit.craftbukkit.v1_6_R2.CraftServer; // HARD MODE REFERENCE, SUBJECT TO RANDOM CHANGES!!!


public class BukkitPlugin extends org.bukkit.plugin.java.JavaPlugin{ // WARNING MAY RETURN ' null ' ALWAYS CHECK!!!
	public org.bukkit.configuration.file.FileConfiguration getMyConfig() {
		try {
			return this.getConfig();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveMyConfig()
	{
		this.saveConfig();
	}
	
	protected boolean sendToPlayer(String player, String msg)
	{/*
	 * Wrap the broadcast to player function
	*/	
		
		//Anytime we are working with Bukkit or CraftBukkit, do Error handling!!!	
		try {
			Bukkit.getServer().getPlayer(player).sendMessage(msg); // Send msg to player with that name.
				
		} catch (Exception e) {
			e.printStackTrace();
			return false; // Somehow we were unable to send a message to the player!
		}
			return true;
	}
	
	protected boolean consoleInfo(String msg)
	{
		try {
			this.getLogger().info(msg);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected SimpleCommandMap getCommandMap() // WARNING MAY RETURN ' null ' ALWAYS CHECK!!!
	{
		/*
		 *  Hacky reflection based method of working with commands. Bukkit API is too limited!
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
		return cmap;
	}
}
