package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.LinkedList;

import HelloWorld.HelloWorld;
import HelloWorld.HelloWorld_Interface;
import MyRemote.MyRemote;
import RMIRegistry.RMINaming;
import RMIRegistry.RegistryServer;
import StubGenerator.Stub_compiler;

public class Main {
	public static void main(String[] args) throws UnknownHostException,
			IOException, ClassNotFoundException {
		
	//	check_same();
		
		if (args[0].equals("server")) {

			HelloWorld hello = new HelloWorld();
			ServerInfo server = new ServerInfo(8001, 8002,  "127.0.0.1", 1099);
			RemoteObjectReference ror = new RemoteObjectReference(
					server.socketListener.server.getInetAddress().toString(), 8001,
					"HelloWorld.HelloWorld_Interface", "hello", "HelloWorld/HelloWorld_Interface_stub", 8002);
			
			server.registerROR("hello", ror, hello);

	//		 Stub_compiler compiler = new  Stub_compiler("src/HelloWorld/HelloWorld_Interface.java");
	//		 compiler.generate_stub();


		} else if (args[0].equals("client")) {

			
			RMINaming name_server = new RMINaming();
			name_server.ipaddress = "127.0.0.1";
			name_server.port = 1099;
			HelloWorld_Interface result = (HelloWorld_Interface)name_server.lookup("hello");
			String value = result.hellowitharg("test");
			System.out.println(value);
			value = result.hellowithoutarg();
			System.out.println(value);
			LinkedList<Character> list = new LinkedList<Character>();
			list.add('c');
			list.add('h');
			list.add('a');
			list.add('o');
			value = result.hellowithreference(list);
			System.out.println(value);
			
						
			
			
			HelloWorld_Interface a = result.helloreturnobject();
			//System.out.println(a.getClass().getSimpleName());
			
			String value1 = a.hellowithROR(result);
			System.out.println(value1);
			
			

			// System.out.println(value);
		} else if (args[0].equals("registry")) {
			RegistryServer server = new RegistryServer();

			// RemoteObjectReference ror = new RemoteObjectReference();

			// server.ror_map.put("test", ror);

			server.start();

		}
		else if (args[0].equals("compile"))
		{
			 Stub_compiler compiler = new  Stub_compiler(args[1]);
			 compiler.generate_stub();
		}

	}

	public static void check_same() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"./bin/HelloWorld/HelloWorld_Interface_stub_test.class"));
		BufferedReader br2 = new BufferedReader(new FileReader(
				"./bin/HelloWorld/HelloWorld_Interface_stub.class"));
		String response = "";
		System.out.println("123");
		while ((response = br.readLine()) != null) {
			String res = br2.readLine();
			if (!res.equals(response)) {
				System.out.println("failed");
				// break;
			}
		}

	}
}