package Communcation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Message.Message;;

public class comm {
	public ObjectOutputStream out;
	public ObjectInputStream in;
	public Socket socket;
	
	
	
	public void sendMessage(Message msg) throws IOException
	{
		out.writeObject(msg);
		out.flush();
	}
	
	public Object receiveMessge() throws IOException, ClassNotFoundException
	{
		return  in.readObject();
	}
	
}
