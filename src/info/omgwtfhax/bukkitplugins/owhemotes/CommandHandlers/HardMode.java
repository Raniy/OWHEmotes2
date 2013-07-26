package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import info.omgwtfhax.bukkitplugins.owhemotes.Emote;
import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

public class HardMode {
	
	org.bukkit.command.SimpleCommandMap cmap = null;
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
		cmap = myPlugin.getCommandMap();
	}
	
	//create an EmoteCommand from an emote
	public void addEmote(Emote emote)
	{
		//TODO create new EmoteCommand & setup
	}
	
	//unregister EmoteCommand from cmap
	public void removeEmote(EmoteCommand emoteCommand)
	{
		if(cmap != null)
			emoteCommand.unregister(cmap);
	}
	
	private class EmoteCommand extends Command{		
		//Used to create a command using the info of an emote
		
		org.bukkit.command.CommandExecutor exe = myPlugin;

		protected EmoteCommand(Emote emote) 
		{
			super(emote.getCommand());
			
			if(cmap != null)
			{
				this.register(cmap);
			}
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) 
		{				
				return exe.onCommand(sender, this, commandLabel, args);
		}
		
	}

}
