package info.omgwtfhax.bukkitplugins.owhemotes.transporter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.frdfsnlght.transporter.api.event.RemoteRequestReceivedEvent;

class RemoteRequestListener implements Listener{
	
	RemoteRequestListener(){}
	
	@EventHandler
	public void onRemoteRequestReceived(RemoteRequestReceivedEvent event)
	{
		//Retrieve message from event
		String message = event.getRequest().getString(TransporterAPI.TRANSPORTER_KEY);
	}
	
	
	
	

}
