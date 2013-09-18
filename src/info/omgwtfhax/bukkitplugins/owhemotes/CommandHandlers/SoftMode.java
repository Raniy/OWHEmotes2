package info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

public class SoftMode implements org.bukkit.event.Listener{
	/*
	 * This class provides a means to listen for and add commands using Bukkit API only.
	 * 
	 * IE one that does not violate the CraftBukkit project's goal of keeping people out of NMO and OBC.
	 * 
	 * Uses the PreCommand Event to listen for players typing something that MAY be an Emote.
	 * 
	 */
	
	OWHEmotes2_0 myPlugin = null;
	
	public SoftMode(OWHEmotes2_0 instance)
	{
		myPlugin = instance;	
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		
		String cmd = event.getMessage() + " "; //Event message contains EVERYTHING, including the command itself
		
		String args = cmd.substring(cmd.indexOf(" ")+1); // Arguments to be passed into getOutputMessage(). This substring cuts out the command
		cmd = cmd.substring(1, cmd.indexOf(" ")); // Remove the "/" from the beginning of the command
		
		for (Emote e:myPlugin.getMyEmotes())
		{
			if(e.getCommand().equalsIgnoreCase(cmd.substring(0,e.getCommand().length())))
			{
				if(e.getCommand().equalsIgnoreCase(cmd)){
					System.out.println("======================TRUE. STOP DOING STUPID EXTRA WORK YOU IDIOT=========================");
				}
				//Found a potential MATCH!
				if(this.myPlugin.playerHasNode(event.getPlayer().getName(),OWHEmotes2_0.getPermissionNodes().get("base") + "." + e.getCommand().toLowerCase()))
				{
					// They are allowed to do it!
					// For now ignore any extra processing, assume all emotes are in third person.
					
					String outputMessage = new String();
					String playerName = args.substring(0, args.indexOf(" ")); // Retrieve player name specified by CommandSender
					
					if(Bukkit.getPlayer(playerName) != null){
						
						playerName = Bukkit.getServer().getPlayer(playerName).getName();
						outputMessage = e.getOutputMessage(event.getPlayer().getName(), playerName);		
						
					} else
					{					
						if(this.myPlugin.playerHasNode(event.getPlayer().getName(), OWHEmotes2_0.getPermissionNodes().get("base") + ".nonplayerp2p")) // May need to rename this atrocious node
						{		
							outputMessage = e.getOutputMessage(event.getPlayer().getName(), args); // Just send through whatever arguments player gave if they have this node
						} else
						{
							event.setCancelled(true);
							return; // Will return here if the player didn't specify another player's name, and didn't have perms for non player p2p messages.
						}
					}
					
					if(outputMessage != null)
					{
						this.myPlugin.sendToAll(outputMessage); // Pass in the arguments, even if they may be null.
						event.setCancelled(true);
					}
					
				}
			}
			
		}
	}
}
