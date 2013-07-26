package info.omgwtfhax.bukkitplugins.owhemotes;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;

public class Emote implements org.bukkit.configuration.serialization.ConfigurationSerializable{ // Class to contain data about any given emote.
	@SerializableAs("Emote")
	public enum Style 
	{
		THIRD,FIRST,P2P; 
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
	
	// Bukkit Config Loader
	public Emote(Map<String, Object> map)
	{
		this.setCommand((String) map.get(serializedCommand));
		this.setMessage((String) map.get(serializedMessage));
		this.setStyle((Emote.Style) map.get(serializedStyle));
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
