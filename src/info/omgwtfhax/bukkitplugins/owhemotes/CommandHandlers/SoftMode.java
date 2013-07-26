package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

public class SoftMode {
	/*
	 * This class provides a means to listen for and add commands using Bukkit API only.
	 * 
	 * IE one that does not violate the CraftBukkit project's goal of keeping people out of NMO and OBC.
	 * 
	 * Uses the PreCommand Event to listen for players typing something that MAY be an Emote.
	 * 
	 */
	
	OWHEmotes2_0 myPlugin = null;
	
	SoftMode(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
	}
}
