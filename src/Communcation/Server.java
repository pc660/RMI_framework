package Communcation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import HelloWorld.HelloWorld;
import Message.RegisterMessage;
import Server.RemoteObjectReference;
import Server.SocketListening;

public class Server {
	

	public static Socket connectToRigster(String registryHostname, int port) {
		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByName(registryHostname), port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Server cann't connect to RigsterServer");
		}
		return socket;

	}

	public static void registerROR(  String name, RemoteObjectReference ror, Socket clientForRig) throws IOException {
		
		System.out.println("Server: start register ror");
		ObjectOutputStream out = new ObjectOutputStream(clientForRig.getOutputStream());
		System.out.println("Server: init ObjectOutputStream");
		RegisterMessage registerMessage = new RegisterMessage(name, ror);

		try {
			out.writeObject(registerMessage);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Can't send registerMessage to registry server");
		}


	}
}