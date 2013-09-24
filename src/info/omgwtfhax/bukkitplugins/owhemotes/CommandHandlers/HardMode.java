package info.omgwtfhax.bukkitplugins.owhemotes.commandhandlers;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

public class HardMode implements org.bukkit.command.CommandExecutor{
	
	/*
	 * In this class we do what we have to do to provide users the best plugin possible.
	 * 
	 * That means we hack directly into CraftBukkit and CraftServer to use their CommandMap.
	 * 
	 * 
	 * Sorry guys... write a better API.
	 * 
	 */
	
	OWHEmotes2_0 myPlugin = null;
	
	public HardMode(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		
		if(sender instanceof Player){

			//iterate through emotes, looking for a match
			for(Emote e: myPlugin.getMyEmotes()){
				
				if(e.getCommand().equalsIgnoreCase(command.getName())){
					
					//Check if sender has permission for emote
					if(this.myPlugin.playerHasNode(sender.getName(), OWHEmotes2_0.getPermissionNodes().get("base").getMyNode() + "." + e.getCommand().toLowerCase())) {
						
						String outputMessage = new String();
						
						if(args.length < 1) // No args, try to find third person version of given emote
						{
							if(e.getStyle() == Emote.Style.THIRD){
								
								// Matching style! get the message
								outputMessage = e.getOutputMessage(sender.getName(), "");
								
								if(outputMessage != null) // Check that the returned emote isn't null, which would indicate they tried a type of emote they didn't have perms for.
								{
									myPlugin.sendToAll(outputMessage); // Pass the first argument always, even if its empty.
								}
								
								return true;								
								
							}
							
							// At this point, we can assume it's not going to be anything else, and skip to next iteration
							continue;
						}
						
						if(Bukkit.getServer().getPlayer(args[0]) != null) // Check if the first argument passed in is a valid player
						{
							outputMessage = e.getOutputMessage(sender.getName(), Bukkit.getServer().getPlayer(args[0]).getName());
						} else 
						{
							if(this.myPlugin.playerHasNode(sender.getName(), OWHEmotes2_0.getPermissionNodes().get("base").getMyNode() + ".nonplayerp2p")) // May need to rename this atrocious node
							{		
								outputMessage = e.getOutputMessage(sender.getName(), myPlugin.arrayToString(0, args)); // Just send through whatever arguments player gave if they have this node
							} else
							{
								return true; // Returns here if they do not have access to non-player P2P
							}
						}
					
						if(outputMessage != null) // Check that the returned emote isn't null, which would indicate they tried a type of emote they didn't have perms for.
						{
							myPlugin.sendToAll(outputMessage); // Pass the first argument always, even if its empty.
						}
						
						return true;
						
					}
				}
			}
		}
		
		return false; //If no match, return false
	}
	


}
