package info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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
		// We have four 'Base' commands. AddEmote, DeleteEmote, ListEmotes, EmoteAll. These can all be called by either a player or the console
		if (sender instanceof Player)
		{
			if(cmd.getName().equalsIgnoreCase("listemotes"))
			{
				return(doListByPlayer((sender.getName())));
			}
			
			if(!(myPlugin.playerHasNode(sender.getName(),OWHEmotes2_0.getPermissionNodes().get("moderator").getMyNode()))) return false; // No Permission!
			
			if(cmd.getName().equalsIgnoreCase("addemote"))
			{
				if(args.length > 2) // Check that sender has given us atleast 3 arguments -- An emote name, emote style, and an emote message
					return (doAddByPlayer(sender.getName(), getEmoteFromStrings(args[0], myPlugin.arrayToString(2,args), Emote.Style.getStyleFromString(args[1]))));
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				if(args.length > 1) // Check that the sender has given an emote name and style
					return (doDeleteByPlayer(sender.getName(), getEmoteFromStrings(args[0], "", Emote.Style.getStyleFromString(args[1]))));
			}
			
			if(cmd.getName().equalsIgnoreCase("emoteall"))
			{
				if(args.length == 1) // Check that they have specified an emote.
				{
					return (doEmoteAll(getEmoteFromStrings(args[0],"",null), myPlugin.arrayToString(1, args), false));
				} 
				else if(args.length > 1)
				{
					return (doEmoteAll(getEmoteFromStrings(args[0],"",null), myPlugin.arrayToString(1, args), this.parseBoolean(args[1])));
				}
			}
			
			if(!(myPlugin.playerHasNode(sender.getName(),OWHEmotes2_0.getPermissionNodes().get("reload").getMyNode()))) return false; // No Permission!
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
				if(args.length > 2) // Check that sender has given us atleast 3 arguments -- An emote name, style, and an emote message
					return (doAddByConsole(getEmoteFromStrings(args[0], myPlugin.arrayToString(2,args), Emote.Style.getStyleFromString(args[1]))));
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				if(args.length > 1) // Check that the sender has given an emote name and style
					return (doDeleteByConsole(getEmoteFromStrings(args[0], "", null)));
			}
			
			if(cmd.getName().equalsIgnoreCase("emoteall"))
			{
				if(args.length > 0) // Check that they have specified an emote.
					return (doEmoteAll(getEmoteFromStrings(args[0],label,null), myPlugin.arrayToString(1, args), false));
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
			myPlugin.sendToPlayer(player, "New emote added!");
			myPlugin.consoleInfo("New emote \"" + emote.getCommand() + "\" added by " + player);
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

			myPlugin.consoleInfo("New emote added!");
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
			
			if(myPlugin.isHardMode())
			{
				if(!myPlugin.removeCommand(emote)) // Check if false
				{
					myPlugin.sendToPlayer(player, "Emote does not exist.");
					return false;
				}
			}
			
			myPlugin.sendToPlayer(player, "Emote successfully deleted!");
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
			
			if(myPlugin.isHardMode())
			{
				if(!myPlugin.removeCommand(emote))
				{
					myPlugin.consoleInfo("Emote does not exist.");
					return false;
				}
			}
			
			myPlugin.consoleInfo("Emote successfully deleted!");
			
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
				myPlugin.sendToPlayer(player, e.getCommand() + ": \"" + e.getMessage() + "\"");
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
				myPlugin.consoleInfo(e.getCommand() + ": \"" + e.getMessage() + "\"");
			}
			
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean doEmoteAll(Emote emote, String args, boolean useTransporter)
	{
		try{
			
			for(Player p : myPlugin.getOnlinePlayers())
			{
				p.performCommand(emote.getCommand() + " " + args);
			}
			
			if(useTransporter)
			{
				// send emoteall to all available servers via Transporter
				myPlugin.getTransporterAPI().doEmoteAll(emote, args);
			}
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}
	
	private boolean doPluginReload()
	{
		//TODO
		
		// Stop all listeners
		PlayerCommandPreprocessEvent.getHandlerList().unregister(myPlugin.getSoftModeListener());
		myPlugin.getHardModeExecutor().
		
		// Empty all lists
		//myPlugin.getMyEmotes().clear();
		myPlugin.setMyEmotes(new java.util.ArrayList<Emote>()); // Setting to new empty list instead of using clear(), due to weird weirdness I've experienced with clear() before
		
		// Reload all variables from config.
		myPlugin.processConfig();
		
		// Restart the Listeners/CommandExecutors
		myPlugin.startCommandHandler();
		
		// Return True for Woo, False for Boo
		return false;
		
	}
	
	// Check to see if the given String is equal to the keyword we want.
	public boolean parseBoolean(String bool)
	{
		if(bool.equalsIgnoreCase("-a"))
			return true;
		
		return false;
	}
	
	private Emote getEmoteFromStrings(String emoteCommand, String emoteMessage, Emote.Style emoteStyle)
	{
		return (new Emote(emoteCommand,emoteMessage,emoteStyle));
	}
}	
