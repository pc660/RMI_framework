package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;

import Communcation.Server;
import HelloWorld.HelloWorld;
import MyRemote.MyRemote;
import RMIRegistry.RMINaming;
import RMIRegistry.RegistryServer;

public class Main {
	public static void main(String[] args) throws UnknownHostException,
			IOException, ClassNotFoundException {
		if (args[0].equals("server")) {

			HelloWorld hello = new HelloWorld();
			Server server = new Server(8001, "127.0.0.1", 1099);
			RemoteObjectReference ror = new RemoteObjectReference(
					server.clientForRig.getInetAddress().toString(), 8001,
					"HelloWorld_Interface", "hello", "URL");

			server.registerROR("hello", ror);
			// SocketListening server = new SocketListening (8001);

			// Class<?> class_name =
			// Class.forName("HelloWorld.HelloWorld_Interface");

			// Stub_compiler compiler = new
			// Stub_compiler("src/HelloWorld/HelloWorld_Interface.java");
			// compiler.generate_stub();

			// SocketDownloading down = new SocketDownloading(8002);
			// down.start();
			// server.start();

		} else if (args[0].equals("client")) {

			/*
			 * Client client = new Client(); LookupMessage lookup = new
			 * LookupMessage("hello"); LookupMessage result =
			 * client.lookup("127.0.0.1", 1099, lookup);
			 * System.out.println(result.ror.ipaddress.toString() + ":"
			 * +result.ror.port);
			 */

			RMINaming name_server = new RMINaming();
			name_server.ipaddress = "127.0.0.1";
			name_server.port = 1099;
			MyRemote result = name_server.lookup("hello");

			// HelloWorld_Interface a= new HelloWorld_Interface();
			// HelloWorld_Interface hello = (HelloWorld_Interface)
			// name_server.lookup("test");
			// LinkedList<Character> list = new LinkedList<Character>();
			// list.add('c');
			// list.add('h');
			// list.add('a');
			// list.add('o');
			// list.add('p');
			// String value = hello.hellowithreference(list);
			// value = hello.hellowithROR(hello);

			// System.out.println(value);
		} else if (args[0].equals("registry")) {
			RegistryServer server = new RegistryServer();

			// RemoteObjectReference ror = new RemoteObjectReference();

			// server.ror_map.put("test", ror);

			server.start();

		}

	}

	public static void check_same() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"./bin/HelloWorld/HelloWorld_Interface_stub_test.class"));
		BufferedReader br2 = new BufferedReader(new FileReader(
				"./bin/HelloWorld/HelloWorld_Interface_stub.class"));
		String response = "";
		while ((response = br.readLine()) != null) {
			String res = br2.readLine();
			if (!res.equals(response)) {
				System.out.println("failed");
				// break;
			}
		}

	}
}
