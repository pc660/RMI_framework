package Server;
import StubGenerator.Stub_compiler;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import HelloWorld.*;
import RMIRegistry.*;
public class Main {
	public static void main(String [] args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		if( args[0].equals("server")){
		try {
			SocketListening server = new SocketListening (8001);
			HelloWorld hello = new HelloWorld();
			Stub_compiler compiler = new Stub_compiler("src/HelloWorld/HelloWorld_Interface.java");
			compiler.generate_stub();
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
			//HelloWorld_Interface a= new HelloWorld_Interface();
			HelloWorld_Interface hello = (HelloWorld_Interface) name_server.lookup("test");
			LinkedList<Character> list = new LinkedList<Character>();
			list.add('c');
			list.add('h');
			list.add('a');
			list.add('o');
			//list.add('p');
			hello.hellowithreference(list);
			String value = hello.hellowithROR(hello);
			
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
