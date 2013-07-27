package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;


import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

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
			
			if(!(myPlugin.playerHasNode(((Player) sender).getName(),myPlugin.getNodeMod()))) return false; // No Permission!
			
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

	private boolean doAddByPlayer(String player,String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.Emote.Style emoteStyle)
	{
		return false;
	}
	 
	private boolean doAddByConsole(String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.Emote.Style emoteStyle)
	{
		return false;
	}

	private boolean doDeleteByPlayer(String player,String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.Emote.Style emoteStyle)
	{
		return false;
	}
	 
	private boolean doDeleteByConsole(String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.Emote.Style emoteStyle)
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
