package Communcation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Message.*;
public class Client extends comm{
	
	private static SocketCache cache = new SocketCache(10);
	public static LookupMessage lookup(String ipaddress, int port, LookupMessage msg) throws UnknownHostException, IOException, ClassNotFoundException
	{
		//socket cache
		Socket socket = new Socket (ipaddress, port);
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeObject(msg);
		output.flush();
		//System.out.println(msg.obj_name);
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		LookupMessage reply = (LookupMessage) input.readObject();
		//System.out.println(reply.obj_name);
		//System.out.println(reply.ror);
		
		return reply;
	}
	
	public static Object connect_to_server (InvokeMessage msg)
	{
		
		String ipaddress = msg.ror.ipaddress;
		int port = msg.ror.port;
		//get cache
		String key = ipaddress + ":" + port;
		if (cache.containsKey(key))
		{
			
			try {
				SocketInfo socket_info = cache.get(key);
				ObjectOutputStream output = new ObjectOutputStream(socket_info.socket.getOutputStream());
				output.writeObject(msg);
				output.flush();
				ObjectInputStream input = new ObjectInputStream(socket_info.socket.getInputStream());
				InvokeMessage reply = (InvokeMessage) input.readObject();
				return reply.return_value;
			} catch (IOException e) {
				
				try {
					exceptionHandler(ipaddress, port, msg, key);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Failed to connect the server");
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			try {
				System.out.println(ipaddress);
				System.out.println(port);
				Socket socket = new Socket(ipaddress, port);
				
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				output.writeObject(msg);
				output.flush();
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				SocketInfo info = new SocketInfo (socket, output, input);
				cache.set(key, info);
				InvokeMessage reply = (InvokeMessage) input.readObject();
				return reply.return_value;
				
			} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
			// TODO Auto-generated catch block
				System.out.println("Failed to connect the server");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
		
	}
	public static Object exceptionHandler(String ipaddress, int port , InvokeMessage msg, String key) throws UnknownHostException, IOException, ClassNotFoundException
	{
		Socket socket = new Socket(ipaddress, port);
		
		ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
		output.writeObject(msg);
		output.flush();
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		SocketInfo info = new SocketInfo (socket, output, input);
		cache.set(key, info);
		InvokeMessage reply = (InvokeMessage) input.readObject();
		return reply.return_value;
	}
}
