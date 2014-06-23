package RMIRegistry;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import Message.Message;

import Communcation.Client;
import Message.LookupMessage;
import MyRemote.MyRemote;
import Communcation.Client;

/*
 * *rmi registry local copy
 */
public class RMINaming {
	private HashMap<String, MyRemote> remote_obj_map;
	public String ipaddress;
	public int port;
	
	public RMINaming()
	{
		remote_obj_map = new HashMap<String, MyRemote> ();
	}
	public MyRemote lookup(String name) throws UnknownHostException, IOException, ClassNotFoundException
	{
		if (remote_obj_map.containsKey(name))
		{
			return remote_obj_map.get(name);
		}
		else
		{
			LookupMessage look = new LookupMessage (name);
			//System.out.print(look.obj_name);
			LookupMessage reply = Client.lookup(ipaddress, port, look);
			System.out.println("finished look up in naming");
			//System.out.println(reply.ror);
//			System.out.println("234\n");
			MyRemote remote = reply.ror.localize();
			remote.setror(reply.ror);
			//System.out.print(reply.ror.ipaddress);
			return remote;
		}
		
		
	}
}
