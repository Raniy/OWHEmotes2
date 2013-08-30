package info.omgwtfhax.bukkitplugins.owhemotes.emotes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.SerializableAs;

public class Emote implements org.bukkit.configuration.serialization.ConfigurationSerializable{ // Class to contain data about any given emote.
	@SerializableAs("Emote")
	public enum Style 
	{
		THIRD,FIRST,P2P; 
		
		public static Style getStyleFromString(String style){
			if (style.equalsIgnoreCase("0"))
			{
				return THIRD;
			} else if (style.equalsIgnoreCase("1"))
			{
				return P2P;
			}
			
			return THIRD; // Default to third
		}
	}
	// Static Strings
	public static String serializedCommand = "EmoteCommand",serializedMessage = "EmoteMessage", serializedStyle = "EmoteStyle";
	
	// Set up a basic Emote;
	private String command = "steamer"; // Default Command 
	private String message = "makes a steaming mess all over the lawn."; // Default Emote
	private Style style = Emote.Style.THIRD; // Default tense
	
	// Constructors
	
	// Base
	public Emote(String cmd, String msg, Emote.Style stl)
	{
		this.setCommand(cmd);
		this.setMessage(msg);
		this.setStyle(stl);
	}
	
	// Convenience function for adding emote to config.
	public Map<String, String> getEmoteInfo()
	{
		Map<String, String> info = new HashMap<String,String>();
		
		info.put(serializedCommand, command);
		info.put(serializedMessage, message);
		info.put(serializedStyle, style.toString());
		
		return info;	
	}
	
	// Bukkit Config Loader
	public Emote(Map<String, Object> map)
	{
		this.setCommand((String) map.get(serializedCommand));
		this.setMessage((String) map.get(serializedMessage));
		this.setStyle((Style)Style.valueOf((String)map.get(serializedStyle)));
	}
	
	//Empty constructor that will return a default Emote.
	public Emote() {
		// Yay for needing to exist for no good reason!
	}

	// Logic!
	public String getOutputMessage(String player, String args)
	{
		
		String message = this.getMessage().replace("-p1", player);
		// Determine if there are any arguments being passed.
		
		if(args.indexOf(" ") == -1 || (args == null)) // No arguments implies no need for further processing.
		{
			if(this.getStyle() == Emote.Style.THIRD)//Third Person processing
			{
				return(message);
			}
		} else {
			// Arguments, assume an attempt at a P2P emote.
			// Try to resolve the argument to the name of a target player.
			
			String playerName = args.substring(0, args.indexOf(" "));
			
			if(Bukkit.getServer().getPlayer(playerName) != null)
			{
				playerName = Bukkit.getServer().getPlayer(playerName).getName(); // Reset variable to be the PROPER name of the target.				
				message = message.replace("-p2", playerName);
				
				return message;
			} else 
			{
				// TODO handle the string proccesing stuff for a NON-PLAYER p2p
			}
			
		}
		
		return null;
	}
	
	
	// Getters and Setters
	public String getCommand()
	{
		return command;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public Style getStyle() {
		return style;
	}
	
	public void setCommand(String command)
	{
		this.command = command;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	// Serialization stuff.
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put(serializedCommand, this.getCommand());
		map.put(serializedMessage, this.getMessage());
		map.put(serializedStyle, this.getStyle());
		
		return map;
	}
	
}
