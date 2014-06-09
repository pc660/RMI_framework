package Server;

import java.io.IOException;
import java.net.UnknownHostException;

import HelloWorld.*;
import RMIRegistry.*;
public class Main {
	public static void main(String [] args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		if( args[0].equals("server")){
		try {
			SocketListening server = new SocketListening (8001);
			HelloWorld hello = new HelloWorld();
			
			
			server.object_map.put("test", hello);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if (args[0].equals("client"))
		{
			
			RMINaming name_server = new RMINaming ();
			name_server.ipaddress = "127.0.0.1";
			name_server.port = 1099;
			HelloWorld_Interface hello = (HelloWorld_Interface) name_server.lookup("test");
			String value = hello.hellowitharg("chaop");
			System.out.println(value);
		}
		else if (args[0].equals("registry"))
		{
			RegistryServer server = new RegistryServer ();
			
			RemoteObjectReference ror = new RemoteObjectReference();
			
			server.ror_map.put("test", ror);
		
			server.start();
		}
		
	}
}
