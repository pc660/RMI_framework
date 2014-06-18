package Server;


import Server.RemoteObjectReference;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import Message.InvokeMessage;

public class SocketListening extends Thread{
	public ServerSocket server; 
	int port;
	public HashMap<String, Object> object_map ;
	public SocketListening (int port) throws IOException
	{
		server = new ServerSocket (port);
		object_map = new HashMap<String, Object>();
	}
	
	
	@Override
	public void run()
	{
		while(true)
		{
			try {
				Socket socket = server.accept();
				
				System.out.println("connected");
				ClientHandler handler =  new ClientHandler( socket);
				handler.start();			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
	}
	public void parseArgs(InvokeMessage msg)
	{
		Object[] args = msg.args;
		for (int i= 0; i< args.length ; i++)
		{
			if (args[i] instanceof RemoteObjectReference)
			{
				System.out.println("This is a remote reference");
				args[i] = object_map.get( (  (RemoteObjectReference)args[i] ).get_obj_name());
				msg.type[i] = args[i].getClass().getInterfaces()[0];
			//	System.out.println(msg.type[i].getSimpleName());
			}
			//System.out.println(args[i].getClass().getSimpleName());
		}
	}
	
	private  class ClientHandler extends Thread{
		public Socket socket;
		public Object callee;
		public InvokeMessage msg;
		public ClientHandler( Socket socket)
		{
//			this.callee = callee;
			
			this.socket = socket;
			try {
				this.socket.setSoTimeout(1000);
			} catch (SocketException e) {
				System.out.println("Cannot set up socket connection");
				
			}
			
			//this.msg = msg;
		}
		@Override
		public void run()
		{
			
			while(true)
			{
					try {
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
						//ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					    msg = (InvokeMessage) input.readObject();
					    if (msg.method_name == null)
					    	break;
						String obj_name = msg.obj_name();
						System.out.println(obj_name);
						if (object_map.containsKey(obj_name))
						{
							this.callee = object_map.get(obj_name);
							parseArgs(msg);
							ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
							InvokeMessage reply = msg.invoke(callee);
							
							output.writeObject(reply);
							output.flush();
							System.out.println("Call finished");
						//	output.close();
						}
						
					} catch (IOException e) {
						System.out.println("Socket disconnect");
						break;
						// TODO Auto-generated catch block
						//e.printStackTrace();
					} catch (ClassNotFoundException e) {
						System.out.println("Cannot find class");
						break;
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					
				}
			}			
		}
	
	
}
