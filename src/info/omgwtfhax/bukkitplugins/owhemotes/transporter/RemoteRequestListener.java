package info.omgwtfhax.bukkitplugins.owhemotes.transporter;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.frdfsnlght.transporter.api.event.RemoteRequestReceivedEvent;

class RemoteRequestListener implements Listener{
	
	OWHEmotes2_0 myPlugin;
	
	RemoteRequestListener(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
	}
	
	@EventHandler
	public void onRemoteRequestReceived(RemoteRequestReceivedEvent event)
	{
		
		// Handle received emote messages
		if(event.getRequest().containsKey(TransporterAPI.TRANSPORTER_MSG_KEY))
		{
			//Retrieve message from event
			String message = event.getRequest().getString(TransporterAPI.TRANSPORTER_MSG_KEY);
			
			myPlugin.sendToAll(message);
		}
		
		
		// Handle received emotes to add to this server
		if(event.getRequest().containsKey(TransporterAPI.TRANSPORTER_EMOTE_KEY))
		{
			
			Emote emote = (Emote)event.getRequest().get(TransporterAPI.TRANSPORTER_EMOTE_KEY);
			
			myPlugin.addEmoteToList(emote);
			
			// Tell the console the emote has been added from the specified server
			myPlugin.consoleInfo("New emote " + emote.getCommand() + " added from " + event.getRemoteServer().getName());
			
		}
	}
	
	
	
	

}
