package info.omgwtfhax.bukkitplugins.owhemotes.transporter;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

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
		if(event.getRequest().containsKey(TransporterAPI.TRANSPORTER_MSG_KEY))
		{
			//Retrieve message from event
			String message = event.getRequest().getString(TransporterAPI.TRANSPORTER_MSG_KEY);
			
			myPlugin.sendToAll(message);
		}
	}
	
	
	
	

}
