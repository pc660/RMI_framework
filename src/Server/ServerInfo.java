package Server;

import Communcation.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ServerInfo {
	public int port;
	public SocketListening socketListener;
	public Socket clientForRig = null;
	public ObjectOutputStream outObj;
	public ObjectInputStream inObj;	
	public int downloadport;
	public SocketDownloading socketdownload;
	public ServerInfo(int localPort, int download, String registryHostname, int registryServerPort) {
		this.port = localPort;
		this.downloadport = download;
		socketListener = startASocket();
		socketdownload = startDownload();
		socketdownload.start();
		if (socketListener != null) {
			socketListener.start();
			
			System.out.println("Server: start a socket linsterner");
		} else {
			System.out.println("socketListener IS NULL");
		}
		
		clientForRig = Server.connectToRigster(registryHostname, registryServerPort);
		System.out.println("Server: get a clientForReg"+clientForRig.toString() );

	}
	
	public  void registerROR(  String name, RemoteObjectReference ror, Object obj)
	{
		try {
			Server.registerROR(name, ror, clientForRig );
			socketListener.object_map.put(name, obj);
			System.out.println("Server: ror sent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private SocketDownloading startDownload()
	{
		SocketDownloading socket = null;
		
		socket = new SocketDownloading (downloadport);	
		//	socket.start();
		
		return socket;
	}
	private SocketListening startASocket() {
		SocketListening socket = null;
		try {
			socket = new SocketListening (port);	
		//	socket.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Socket start failed");
		}	
		return socket;
	}
}
