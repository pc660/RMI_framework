package RMIRegistry;
import Message.LookupMessage;
import Message.RegisterMessage;
import Server.RemoteObjectReference;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
public class RegistryServer extends Thread{
	public HashMap<String, RemoteObjectReference> ror_map;
	private int port = 1099;
	private ServerSocket socket;
	public RegistryServer()
	{
		ror_map = new HashMap<String, RemoteObjectReference> ();
	}
	private class socket_handler extends Thread
	{
		Socket client;
		public socket_handler(Socket client)
		{
			this.client = client;
		}
		@Override
		public void run()
		{
			try {
				ObjectInputStream input =  new ObjectInputStream(client.getInputStream());
				Object obj = input.readObject();
				//System.out.println(obj);
				
				
				Class<?> name = obj.getClass();
				
				if (name.getSimpleName().equals("LookupMessage"))
				{
					//deal with look up
					LookupMessage look = (LookupMessage) obj;
					//System.out.println(look.obj_name);
					//System.out.println(ror_map);
					String obj_name = look.obj_name;
					//System.out.println(obj_name);
					
					
					if (ror_map.containsKey(obj_name))
					{
						RemoteObjectReference ror = ror_map.get(obj_name);
						look.set_ror(ror);
						ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
						output.writeObject(look);
						output.flush();
						output.close();
						System.out.println("Succesfully look up " + obj_name);
					}		
					else
					{
						look.set_ror(null);
						ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
						output.writeObject(look);
						output.flush();
						output.close();
						System.out.println("Not successfully look up" + look.obj_name());
					}
				}
				//message register
				else if (name.getSimpleName().equals("RegisterMessage"))
				{
					RegisterMessage reg = (RegisterMessage) obj;
					ror_map.put(reg.obj_name(), reg.ror);
					System.out.println("successfully registered " + reg.obj_name());
				}
				else
				{
					System.out.println("unknown problem");
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			
		}
	}
	@Override
	public void run()
	{
		try {
			socket = new ServerSocket(this.port);
			System.out.println("Registry Server begins to work");
			while(true)
			{
				Socket client = socket.accept();
				System.out.println("Registry server connected");
				socket_handler handler = new socket_handler(client);
				handler.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
