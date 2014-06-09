package Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Message.InvokeMessage;

public class ClientHandler extends Thread{
	public Socket socket;
	public Object callee;
	InvokeMessage msg;
	public ClientHandler(Object callee, Socket socket, InvokeMessage msg)
	{
		this.callee = callee;
		this.socket = socket;
		this.msg = msg;
	}
	@Override
	public void run()
	{
		ObjectOutputStream output;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			InvokeMessage reply = msg.invoke(callee);
			
			output.writeObject(reply);
			output.flush();
			System.out.println("Call finished");
			output.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
