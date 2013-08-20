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
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		// We have three 'Base' commands. AddEmote, DeleteEmote, ListEmotes. These can all be called by either a player or the console
		if (sender instanceof Player)
		{
			if(cmd.getName().equalsIgnoreCase("listemotes"))
			{
				return (doListByPlayer(((Player) sender).getName()));
			}
			
			if(!(myPlugin.playerHasNode(((Player) sender).getName(),OWHEmotes2_0.getPermissionNodes().get("moderator").getMyNode()))) return false; // No Permission!
			
			if(cmd.getName().equalsIgnoreCase("addemote"))
			{
				if(args.length > 1) // Check that sender has given us atleast 2 arguments -- An emote name and an emote message
					return (doAddByPlayer(((Player) sender).getName(), getEmoteFromStrings(args[0], label, null)));
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				if(args.length > 0) // Check that the sender has given an emote name
					return (doDeleteByPlayer(((Player) sender).getName(), getEmoteFromStrings(args[0], label, null)));
			}
			
			if(cmd.getName().equalsIgnoreCase("emoteall"))
			{
				if(args.length > 0) // Check that they have specified an emote.
					return (doEmoteAllByPlayer(((Player) sender).getName(), getEmoteFromStrings(args[0],label,null)));
			}
			
			if(!(myPlugin.playerHasNode(((Player) sender).getName(),OWHEmotes2_0.getPermissionNodes().get("reload").getMyNode()))) return false; // No Permission!
			{
				// Reload plugin
				return (doPluginReload());
			}
			
		} else {
			// It is the console... They can do whatever.
			if(cmd.getName().equalsIgnoreCase("listemotes"))
			{
				return (doListByConsole());
			}
			
			if(cmd.getName().equalsIgnoreCase("addemote"))
			{
				if(args.length > 1) // Check that sender has given us atleast 2 arguments -- An emote name and an emote message
					return (doAddByConsole(getEmoteFromStrings(args[0], label, null)));
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				if(args.length > 0) // Check that the sender has given an emote name
					return (doDeleteByConsole(getEmoteFromStrings(args[0], label, null)));
			}
		}
		return false;
	}

	private boolean doAddByPlayer(String player,Emote emote)
	{
	
		try
		{
			// Add new emote to our list of emotes
			myPlugin.addEmoteToList(emote);
			
			// Extra shizzle when using HardMode
			if(myPlugin.isHardMode())
			{
				// Create a new command pertaining to the emote
				myPlugin.createEmoteCommand(emote); 
				
			}		

			// Tell player their new emote has been made, as well as alert console
			Bukkit.getPlayer(player).sendMessage("New emote added!");
			myPlugin.getLogger().info("New emote \"" + emote.getCommand() + "\" added by " + player);
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	 
	private boolean doAddByConsole(Emote emote)
	{
		//Exact same functionality as doAddByPlayer, with adjustments to respect that it is a console sender.
		
		try
		{
			// Add new emote to our list of emotes
			myPlugin.addEmoteToList(emote);
			
			// Extra shizzle when using HardMode
			if(myPlugin.isHardMode())
			{
				// Create a new command pertaining to the emote
				myPlugin.createEmoteCommand(emote); 
			}

			myPlugin.getLogger().info("New emote added!");
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	private boolean doDeleteByPlayer(String player, Emote emote)
	{	
		try
		{	
			//iterate through emotes, looking for a match
			for(Emote e : myPlugin.getMyEmotes())
			{
				//Check if emote is what our player is looking for
				if (e.getCommand().equals(emote.getCommand()) && e.getStyle().equals(emote.getStyle()))
				{
					// Found a matching emote! store it in emote variable
					emote = e;
				}
			}
			
			if(emote == null) // No matching emote. ////////////LOL, oops. Flawed logic ftw.. emote will NEVER be null, so I need to fix this & doDeleteByConsole soon./////////////////////////
			{
				Bukkit.getPlayer(player).sendMessage("Given emote does not exist");
				return false;
			}
			
			if(myPlugin.isHardMode())
			{
				myPlugin.removeCommand(emote);
			}
			
			Bukkit.getPlayer(player).sendMessage("Emote successfully deleted!");
			return true;
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	 
	private boolean doDeleteByConsole(Emote emote) // Same as doDeleteByPlayer, with adjustments for console sender.
	{
		try
		{	
			//iterate through emotes, looking for a match
			for(Emote e : myPlugin.getMyEmotes())
			{
				//Check if emote is what our player is looking for
				if (e.getCommand().equals(emote.getCommand()) && e.getStyle().equals(emote.getStyle()))
				{
					// Found a matching emote! store it in emote variable
					emote = e;
				}
			}
			
			if(emote == null) // No matching emote.
			{
				myPlugin.getLogger().info("Given emote does not exist");
				return false;
			}
			
			if(myPlugin.isHardMode())
			{
				myPlugin.removeCommand(emote);
			}
			
			myPlugin.getLogger().info("Emote successfully deleted!");
			
			return true;
			
		} catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	private boolean doListByPlayer(String player) // How dare you add a list emotes, you traitor!
	{
		// Exception handling
		try{
			
			// Iterate through stored emotes
			for(Emote e : myPlugin.getMyEmotes())
			{
				// Send the player a message with info about this emote
				Bukkit.getPlayer(player).sendMessage(e.getCommand() + " - \"" + e.getMessage() + "\"");
			}
			
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	 
	private boolean doListByConsole() // Same as doListByPlayer, with adjustments for console
	{
		// Exception handling
		try{
			
			// Iterate through stored emotes
			for(Emote e : myPlugin.getMyEmotes())
			{
				// Send the console a message with info about this emote
				myPlugin.getLogger().info(e.getCommand() + " - \"" + e.getMessage() + "\"");
			}
			
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean doEmoteAllByPlayer(String player, Emote emote)
	{
		//TODO iterate through players, make them all take a steamy dump on the lawn (or other things... I guess...)
		return false;
	}
	
	private boolean doEmoteAllByConsole(Emote emote)
	{
		//TODO steal logic from doEmoteAllByPlayer
		return false;
	}
	
	private boolean doPluginReload()
	{
		// TODO
		
		// Stop all listeners
		
		// Empty all lists
		
		// Reload all variables from config.
		
		// Rebuild all lists
		
		// Restart the Listeners/CommandExecutors
		
		// Return True for Woo, False for Boo
		return false;
		
	}
	
	private Emote getEmoteFromStrings(String emoteCommand, String emoteMessage, info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote.Style emoteStyle)
	{
		return (new Emote(emoteCommand,emoteMessage,emoteStyle));
	}
}	
