package info.omgwtfhax.bukkitplugins.owhemotes.transporter;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;
import info.omgwtfhax.bukkitplugins.owhemotes.emotes.Emote;

import org.bukkit.entity.Player;
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
		if(event.getRequest().containsKey(TransporterAPI.TRANSPORTER_EMOTEALL_EMOTE_KEY))
		{
			//Retrieve emote and arguments from request
			Emote emote = (Emote)event.getRequest().get(TransporterAPI.TRANSPORTER_EMOTEALL_EMOTE_KEY);
			String args = null;
			
			// If the request contains args, set args variable accordingly
			if(event.getRequest().containsKey(TransporterAPI.TRANSPORTER_EMOTEALL_ARGS_KEY))
			{
				args = event.getRequest().getString(TransporterAPI.TRANSPORTER_EMOTEALL_ARGS_KEY);
			}
			
			for(Player p : myPlugin.getOnlinePlayers())
			{
				myPlugin.sendToAll(emote.getOutputMessage(p.getName(), args));
			}
		}
		
		
		// Handle received emotes to add to this server
		if(event.getRequest().containsKey(TransporterAPI.TRANSPORTER_EMOTEADD_KEY))
		{
			
			Emote emote = (Emote)event.getRequest().get(TransporterAPI.TRANSPORTER_EMOTEADD_KEY);
			
			myPlugin.addEmoteToList(emote);
			
			// Tell the console the emote has been added from the specified server
			myPlugin.consoleInfo("New emote " + emote.getCommand() + " added from " + event.getRemoteServer().getName());
			
		}
	}
	
	
	
	

}
