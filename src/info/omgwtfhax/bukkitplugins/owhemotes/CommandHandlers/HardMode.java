package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

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
	
	HardMode(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
	}
	
	//create an EmoteCommand from an emote
	public void createEmoteCommand(Emote emote)
	{
		//TODO create new EmoteCommand & setup
	}
	
	//unregister EmoteCommand from cmap
	public void removeEmoteCommand(EmoteCommand emoteCommand)
	{
		org.bukkit.command.SimpleCommandMap cmap = myPlugin.getCommandMap();
		
		if(cmap != null)
			emoteCommand.unregister(cmap);
	}
	
	private class EmoteCommand extends Command{		
		//Used to create a command using the info of an emote
		
		Emote emote = null;

		protected EmoteCommand(Emote emote) 
		{
			super(emote.getCommand());
			this.emote = emote;
			
			org.bukkit.command.SimpleCommandMap cmap = myPlugin.getCommandMap();
			
			if(cmap != null)
				this.register(myPlugin.getCommandMap());
		}

		@Override
		public boolean execute(CommandSender sender, String commandLabel, String[] args) 
		{
			if (emote.getStyle() == Emote.Style.FIRST) {
				//TODO
			} else if (emote.getStyle() == Emote.Style.THIRD) {
				//TODO
			} else if (emote.getStyle() == Emote.Style.P2P) {
				//TODO
			}
			
			return false;
		}
		
	}

}
