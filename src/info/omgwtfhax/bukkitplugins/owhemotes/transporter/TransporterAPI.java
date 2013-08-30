package info.omgwtfhax.bukkitplugins.owhemotes.transporter;

import info.omgwtfhax.bukkitplugins.owhemotes.OWHEmotes2_0;

import org.bukkit.plugin.Plugin;

import com.frdfsnlght.transporter.Transporter;
import com.frdfsnlght.transporter.api.API;
import com.frdfsnlght.transporter.api.Callback;
import com.frdfsnlght.transporter.api.RemoteServer;
import com.frdfsnlght.transporter.api.TypeMap;

public class TransporterAPI {
	
	//Key to use when sending / receiving any sent message.
	public static String TRANSPORTER_KEY = "TRP-EMT";
	
	API api;
	OWHEmotes2_0 myPlugin;
	
	public TransporterAPI(OWHEmotes2_0 instance)
	{
		myPlugin = instance;
		
		setupTransporterAPI();
	}
	
	public boolean setupTransporterAPI()
	{
		Plugin transporter = myPlugin.getServer().getPluginManager().getPlugin("Transporter");	
		
		//Check that transporter is both installed and running
		if ((transporter != null) && transporter.isEnabled())
		{
			api = ((Transporter)transporter).getAPI();
			return true;
		}
		return false;
	}
	
	//Send a message to all servers
	public void sendToAllServers(String message)
	{
		for(RemoteServer server : api.getRemoteServers())
		{
			TypeMap request = new TypeMap();
			request.put(TRANSPORTER_KEY, message);
			
			server.sendRemoteRequest(new Callback<TypeMap>(){}, request);
		}
		
	}
	
	

}
