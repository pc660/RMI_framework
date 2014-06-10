package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import Message.InvokeMessage;

public class SocketListening extends Thread{
	
	ServerSocket server; 
	int port;
	HashMap<String, Object> object_map ;
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
				
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				//ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				InvokeMessage  msg = (InvokeMessage) input.readObject();
				String obj_name = msg.obj_name();
				System.out.println(obj_name);
				if (object_map.containsKey(obj_name))
				{
					Object callee = object_map.get(obj_name);
					parseArgs(msg);
					ClientHandler handler= new ClientHandler(callee, socket, msg);
					handler.start();
				}
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
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
	
}
