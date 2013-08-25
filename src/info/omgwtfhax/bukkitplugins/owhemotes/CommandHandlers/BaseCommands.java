package info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;
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
					return (doAddByPlayer(sender.getName(), getEmoteFromStrings(args[0], arrayToString(2,args), Emote.Style.getStyleFromString(args[1]))));
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				if(args.length > 1) // Check that the sender has given an emote name and style
					return (doDeleteByPlayer(sender.getName(), getEmoteFromStrings(args[0], "", Emote.Style.getStyleFromString(args[1]))));
			}
			
			if(cmd.getName().equalsIgnoreCase("emoteall"))
			{
				if(args.length > 0) // Check that they have specified an emote.
					return (doEmoteAll(getEmoteFromStrings(args[0],"",null), false));
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
					return (doAddByConsole(getEmoteFromStrings(args[0], arrayToString(2,args), Emote.Style.getStyleFromString(args[1]))));
			}
			
			if(cmd.getName().equalsIgnoreCase("deleteemote"))
			{
				if(args.length > 1) // Check that the sender has given an emote name and style
					return (doDeleteByConsole(getEmoteFromStrings(args[0], "", null)));
			}
			
			if(cmd.getName().equalsIgnoreCase("emoteall"))
			{
				if(args.length > 0) // Check that they have specified an emote.
					return (doEmoteAll(getEmoteFromStrings(args[0],label,null), false));
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
				myPlugin.sendToPlayer(player, e.getCommand() + " - \"" + e.getMessage() + "\"");
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
				myPlugin.consoleInfo(e.getCommand() + " - \"" + e.getMessage() + "\"");
			}
			
			return true;
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private boolean doEmoteAll(Emote emote, boolean useTransporter)
	{
		for(Player p : myPlugin.getOnlinePlayers()){
			myPlugin.dispatchCommand(p.getDisplayName(), emote.getCommand());
		}
		
		if(useTransporter)
		{
			//TODO send emoteall to all available servers via Transporter
		}
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
	
	public String arrayToString(int startpoint, String[] args)
	{
		//Return a string made from an array seperated by spaces. Startpoint will exclude starting objects in the array.
		String message = "";
		for(int i = startpoint; i < args.length; i++){
			message = message + " " + args[i];
		}
		return message;
	}
	
	private Emote getEmoteFromStrings(String emoteCommand, String emoteMessage, Emote.Style emoteStyle)
	{
		return (new Emote(emoteCommand,emoteMessage,emoteStyle));
	}
}	
