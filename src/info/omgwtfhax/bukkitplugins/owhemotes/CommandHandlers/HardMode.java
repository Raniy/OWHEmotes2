package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;

import info.omgwtfhax.bukkitplugins.owhemotes.Emote;
import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

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
	
	//unregister command from command map. Returns true if successfully removed
	public boolean removeCommand(Emote emote)
	{
		try{
			SimpleCommandMap cmap = myPlugin.getCommandMap();
			
			//If cmap exists, unregister (remove) command from it
			if(cmap != null){
				
				for(Emote e : myPlugin.getMyEmotes()){
					
					//Check that the specified emote is an actual emote,
					//otherwise any command may be deleted
					if((e.getCommand().contentEquals(emote.getCommand())) && (e.getStyle().equals(emote.getStyle())))
					{
					    // This is our Emote, lets get rid of it.
						cmap.getCommand(e.getCommand()).unregister(cmap);
						return true;
					}
				}
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		
		if(sender instanceof Player){

			//iterate through emotes, looking for a match
			for(Emote e: myPlugin.getMyEmotes()){
				
				if(e.getCommand().equalsIgnoreCase(command.getName())){
					
					//Check if sender has permission for emote
					if(OWHEmotes2_0.playerHasNode(sender.getName(), OWHEmotes2_0.getPermissionNodes().get("base") + "." + e.getCommand().toLowerCase())) {
						
						String outputMessage = e.getOutputMessage(sender.getName(), args[0]);
					
						if(outputMessage != null)
						{
							myPlugin.sendToAll(e.getOutputMessage(sender.getName(), args[0])); // Pass the first argument always, even if its empty.
						}
						
						return true;
						
					}
				}
			}
		}
		
		return false; //If no match, return false
	}
	


}
