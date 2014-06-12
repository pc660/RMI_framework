package Communcation;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketInfo {
	public Socket socket;
	public ObjectOutputStream output;
	public ObjectInputStream input;
	public SocketInfo (Socket socket, ObjectOutputStream output, ObjectInputStream input )
	{
		this.socket = socket; 
		this.output = output;
		this.input = input;
	}
}
