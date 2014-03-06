package info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote.Style;

import org.bukkit.Bukkit;
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
			if(!(myPlugin.playerHasNode(sender.getName(),OWHEmotes2_0.getPermissionNodes().get("moderator").getMyNode()))) return false; // No Permission!

			if(cmd.getName().equalsIgnoreCase("listemotes"))
			{
				return(doListByPlayer((sender.getName())));
			}
			
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
					return (doEmoteAll(getEmoteFromStrings(args[0],"",null), myPlugin.arrayToString(1, args), false));
				}
			}
			
			if(!(myPlugin.playerHasNode(sender.getName(),OWHEmotes2_0.getPermissionNodes().get("reload").getMyNode()))) return false; // No Permission!
			{
				if(cmd.getName().equalsIgnoreCase("reloademotes"))
				{
					// Reload plugin
					return (doPluginReload());
				}
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
			
			Emote matchingEmote = null;
			
			for(Emote e : myPlugin.getMyEmotes())
			{
				if(e.getCommand().equalsIgnoreCase(emote.getCommand()))
				{
					//possible match
					if(args.equals("") && e.getStyle() == Style.THIRD)
						matchingEmote = e;
					else if(!(args.equals("")) && e.getStyle() == Style.P2P)
					{
						if(Bukkit.getPlayer(args.substring(args.indexOf(emote.getCommand())+emote.getCommand().length()))!=null)
							args = Bukkit.getPlayer(args.substring(args.indexOf(emote.getCommand())+emote.getCommand().length())).getName();
						
						matchingEmote = e;
					}
				}
			}
			
			if(matchingEmote != null)
			{
				for(Player p : myPlugin.getOnlinePlayers())
				{
					myPlugin.sendToAll(matchingEmote.getOutputMessage(p.getName(), args));
				}
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
		
		// Stop all listeners
		PlayerCommandPreprocessEvent.getHandlerList().unregister(myPlugin.getSoftModeListener());
		
		// Empty all lists
		//myPlugin.getMyEmotes().clear();
		myPlugin.setMyEmotes(new java.util.ArrayList<Emote>()); // Setting to new empty list instead of using clear(), due to weird weirdness I've experienced with clear() before
		
		// Reload all variables from config.
		myPlugin.processConfig();
		
		// Restart the Listeners/CommandExecutors
		myPlugin.startCommandHandler();
		
		// Return True for Woo, False for Boo
		return true;
		
	}
	
	private Emote getEmoteFromStrings(String emoteCommand, String emoteMessage, Emote.Style emoteStyle)
	{
		return (new Emote(emoteCommand,emoteMessage,emoteStyle));
	}
}	
