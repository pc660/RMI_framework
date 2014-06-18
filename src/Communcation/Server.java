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
	public int port;
	public SocketListening socketListener;
	public Socket clientForRig = null;
	public ObjectOutputStream outObj;
	public ObjectInputStream inObj;	

	public Server(int localPort, String registryHostname, int registryServerPort) {
		this.port = localPort;
		socketListener = startASocket();
		if (socketListener != null) {
			socketListener.start();
			System.out.println("Server: start a socket linsterner");
		} else {
			System.out.println("socketListener IS NULL");
		}
		clientForRig = connectToRigster(registryHostname, registryServerPort);
		System.out.println("Server: get a clientForReg"+clientForRig.toString() );

	}

	private SocketListening startASocket() {
		SocketListening socket = null;
		try {
			socket = new SocketListening (port);	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket start failed");
		}	
		return socket;
	}

	private Socket connectToRigster(String registryHostname, int port) {
		Socket socket = null;
		try {
			socket = new Socket(InetAddress.getByName(registryHostname), port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Server cann't connect to RigsterServer");
		}
		return socket;

	}

	public void registerROR(String name, RemoteObjectReference ror) throws IOException {
		
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


		socketListener.object_map.put(name, ror);
		System.out.println("Server: ror sent");
	}
}