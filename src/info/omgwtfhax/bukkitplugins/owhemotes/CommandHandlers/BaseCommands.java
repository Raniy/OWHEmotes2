package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BaseCommands implements org.bukkit.command.CommandExecutor
{
	private OWHEmotes2_0 myPlugin = null;
	
	public BaseCommands(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		// We have three 'Base' commands. AddEmote, DeleteEmote, ListEmotes. These can all be called by either a player or the console
		if (sender instanceof Player)
		{
			if(cmd.getName().equalsIgnoreCase("listemotes"))
			{
				doListByPlayer(label);
				return true;
			}
			
			if(!(myPlugin.playerHasNode(((Player) sender).getName(),OWHEmotes2_0.getPermissionNodes().get("moderator").getMyNode()))) return false; // No Permission!
			
			if(cmd.getName().equalsIgnoreCase("addemote"))
			{
				doAddByPlayer(label, label, label, null);
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				doDeleteByPlayer(label, label, label, null);
				return true;
			}
			
			if(!(myPlugin.playerHasNode(((Player) sender).getName(),OWHEmotes2_0.getPermissionNodes().get("reload").getMyNode()))) return false; // No Permission!
			{
				// Reload plugin
			}
			
		} else {
			// It is the console... They can do whatever.
			if(cmd.getName().equalsIgnoreCase("listemotes"))
			{
				doListByConsole();
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("addemote"))
			{
				doAddByConsole(label, label, null);
				return true;
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				doDeleteByConsole(label, label, null);
				return true;
			}
		}
		
		// Also check for Emotes, which can only be done by a Player!
		
		return false; // This means we did not handle the command. Only return false if we DONT do anything.
	}

	private boolean doAddByPlayer(String player,String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote.Style emoteStyle)
	{
	
		try{
			// initialize new emote that the player has specified
			Emote emote = new Emote(emoteCommand, emoteMessage, emoteStyle);
			
			// Add new emote to our list of emotes
			myPlugin.addEmoteToList(emote);
			
			// Extra shizzle when using HardMode
			if(myPlugin.isHardMode())
			{
				// Create a new command pertaining to the emote
				myPlugin.createEmoteCommand(emote); 
				
				// Tell player their new emote has been made, as well as alert console
				Bukkit.getPlayer(player).sendMessage("[HardMode] new emote added!");
				myPlugin.getLogger().info("[HardMode] new emote \"" + emoteCommand + "\" added by " + player);
			} else 
			{
				// Tell player their new emote has been made, as well as alert console
				Bukkit.getPlayer(player).sendMessage("[SoftMode] new emote added!");
				myPlugin.getLogger().info("[SoftMode] new emote \"" + emoteCommand + "\" added by " + player);
			}
			return true;
			
		} catch (Exception e)
		{
			return false;
		}
	}
	 
	private boolean doAddByConsole(String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote.Style emoteStyle)
	{
		//Exact same functionality as doAddByPlayer, with adjustments to respect that it is a console sender.
		
		try{
			// initialize new emote that the console has specified
			Emote emote = new Emote(emoteCommand, emoteMessage, emoteStyle);
			
			// Add new emote to our list of emotes
			myPlugin.addEmoteToList(emote);
			
			// Extra shizzle when using HardMode
			if(myPlugin.isHardMode())
			{
				// Create a new command pertaining to the emote
				myPlugin.createEmoteCommand(emote); 
				
				// Tell console the emote has been successfully created in HardMode
				myPlugin.getLogger().info("[HardMode] new emote added!");
			} else 
			{
				// Tell console the emote has been successfully created in SoftMode
				myPlugin.getLogger().info("[SoftMode] new emote added!");
			}
			return true;
			
		} catch (Exception e)
		{
			return false;
		}
	}

	private boolean doDeleteByPlayer(String player,String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote.Style emoteStyle)
	{
		try{
			
		} catch(Exception e){
			
		}
		return false;
	}
	 
	private boolean doDeleteByConsole(String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote.Style emoteStyle)
	{
		return false;
	}
	
	private boolean doListByPlayer(String player)
	{
		return false;
	}
	 
	private boolean doListByConsole()
	{
		return false;
	}
	
	
}	
