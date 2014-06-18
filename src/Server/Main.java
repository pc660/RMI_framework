package Server;
import StubGenerator.Stub_compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.lang.*;

import HelloWorld.*;
import RMIRegistry.*;
public class Main {
	public static void main(String [] args) throws UnknownHostException, IOException, ClassNotFoundException
	{
		if( args[0].equals("server")){
		try {
			SocketListening server = new SocketListening (8001);
			HelloWorld hello = new HelloWorld();
			RemoteObjectReference test = new RemoteObjectReference("127.0.0.1", 8001, "HelloWorld_Interface", "test", "HelloWorld/HelloWorld_Interface_stub", 8002);
			//Class<?> class_name = Class.forName("HelloWorld.HelloWorld_Interface");
			
		//	Stub_compiler compiler = new Stub_compiler("src/HelloWorld/HelloWorld_Interface.java");
		//	compiler.generate_stub();
		
	        SocketDownloading down = new 	SocketDownloading(8002);
	        down.start();
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		else if (args[0].equals("client"))
		{
			
			/*RMINaming name_server = new RMINaming ();
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
			String value = hello.hellowithreference(list);
			value = hello.hellowithROR(hello);
			*/
			
			
			
		//	System.out.println(value);
		}
		else if (args[0].equals("registry"))
		{
			RegistryServer server = new RegistryServer ();
			
			RemoteObjectReference ror = new RemoteObjectReference();
			
			server.ror_map.put("test", ror);
		
			server.start();
		}
		
		
	}
	public static void check_same() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader("./bin/HelloWorld/HelloWorld_Interface_stub_test.class"));
		BufferedReader br2 = new BufferedReader(new FileReader("./bin/HelloWorld/HelloWorld_Interface_stub.class"));
		String response = "";
		while(( response = br.readLine() ) != null)	
		{
			String res = br2.readLine();
			if(!res.equals(response))
			{
				System.out.println("failed");
				//break;
			}
		}
		
		
	}
}
