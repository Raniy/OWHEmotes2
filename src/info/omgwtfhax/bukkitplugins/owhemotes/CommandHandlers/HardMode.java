package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
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
	
	HardMode(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
	}
	
	//create a new command with name of emote. Returns true if successfully made
	public boolean createCommand(String emote)
	{
		
		try{
		
			SimpleCommandMap cmap = myPlugin.getCommandMap();
			
			//if cmap exists, register (add) command to it
			if(cmap != null){
				
				//Make sure the emote isn't already registered
				if(cmap.getCommand(emote) != null){				
					if(cmap.getCommand(emote).isRegistered())
						return false;
				}
				
				return cmap.register(emote, new EmoteCommand(emote, this));
			
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	//unregister command from command map. Returns true if successfully removed
	public boolean removeCommand(String emote)
	{
		try{
			SimpleCommandMap cmap = myPlugin.getCommandMap();
			
			//If cmap exists, unregister (remove) command from it
			if(cmap != null){
				
				for(Emote e : myPlugin.getMyEmotes()){
					
					//Check that the specified emote is an actual emote,
					//otherwise any command may be deleted
					if(e.getCommand().equalsIgnoreCase(emote)){
						
						return cmap.getCommand(emote).unregister(cmap);
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
					if(myPlugin.playerHasNode(sender.getName(), myPlugin.getNodeBase() + "." + e.getCommand().toLowerCase())) {
					
						myPlugin.sendToAll(e.getOutputMessage(sender.getName(), args[0])); // Pass the first argument always, even if its empty.
						return true;
						
					}
				}
			}
		}
		
		return false; //If no match, return false
	}
	
	private class EmoteCommand extends Command{		
		//Used to create a new command
		
		CommandExecutor myExecutor = null;

		protected EmoteCommand(String emote, CommandExecutor exe) 
		{
			super(emote);
			myExecutor = exe;
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) 
		{
			return myExecutor.onCommand(sender, this, commandLabel, args);
		}
		
	}

}
