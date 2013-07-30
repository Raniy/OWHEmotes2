package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

import info.omgwtfhax.bukkitplugins.owhemotes.Emote;
import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

public class HardMode {
	
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
	BaseCommands myExecutor = null;
	
	HardMode(OWHEmotes2_0 instance, BaseCommands exe)
	{
		myPlugin = instance;
		myExecutor = exe;
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
				
				return cmap.register(emote, new EmoteCommand(emote, myExecutor));
			
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
			//send through BaseCommands' onCommand function
			return myExecutor.onCommand(sender, this, commandLabel, args);
		}
		
	}

}
