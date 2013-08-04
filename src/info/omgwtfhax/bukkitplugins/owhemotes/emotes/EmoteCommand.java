package info.omgwtfhax.bukkitplugins.owhemotes.emotes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EmoteCommand extends Command
{		
	//Used to create a new command
	private CommandExecutor myExecutor = null;
	private Emote myEmote = null;

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) 
	{
		return myExecutor.onCommand(sender, this, commandLabel, args);
	}
	
	public EmoteCommand(Emote emote, CommandExecutor exe) 
	{
		// Register the command string we listen for with our Command
		super(emote.getCommand());
		
		// Set the class that executes this command.
		this.setMyExecutor(exe);
		
		// Set the Emote this Command provides, makes it nice and easy to get the Emote for processing.
		this.setMyEmote(emote);
	}
	
	public CommandExecutor getMyExecutor() {
		return myExecutor;
	}

	public Emote getMyEmote() {
		return myEmote;
	}

	public void setMyExecutor(CommandExecutor myExecutor) {
		this.myExecutor = myExecutor;
	}

	public void setMyEmote(Emote myEmote) {
		this.myEmote = myEmote;
	}
	
}