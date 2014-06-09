package Communcation;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import Message.*;
public class Client extends comm{
	
	
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
		
		try {
			Socket socket = new Socket(ipaddress, port);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(msg);
			output.flush();
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			InvokeMessage reply = (InvokeMessage) input.readObject();
			return reply.return_value;
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
