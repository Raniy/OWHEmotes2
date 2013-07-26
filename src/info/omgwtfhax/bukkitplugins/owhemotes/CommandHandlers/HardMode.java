package info.omgwtfhax.bukkitplugins.owhemotes.CommandHandlers;

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

}
