package RMIRegistry;
import Message.LookupMessage;
import Message.RegisterMessage;
import Server.RemoteObjectReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	public String log_file = "ror_map.obj";
	private class checkpoint extends Thread
	{
		@Override
		public void run()
		{
			while(true)
			{
				FileOutputStream out= null;
				try {
					out = new FileOutputStream(log_file);
					ObjectOutputStream outObj = new ObjectOutputStream(out);
					outObj.writeObject(ror_map);
					outObj.flush();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}	
	}
	private void read_ror_map ()
	{
		try {
			FileInputStream in = new FileInputStream(log_file);
			ObjectInputStream input = new ObjectInputStream (in);
			ror_map = (HashMap<String, RemoteObjectReference>  )input.readObject();
			
		} catch (FileNotFoundException e) {
			ror_map = new HashMap<String, RemoteObjectReference> ();
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ror_map = new HashMap<String, RemoteObjectReference> ();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			//			e.printStackTrace();
			ror_map = new HashMap<String, RemoteObjectReference> ();
		}
		
	}
	
	public RegistryServer()
	{
		read_ror_map();
		try {
			socket = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkpoint check = new checkpoint();
		check.start();
	}
	@Override
	public void run()
	{		
			System.out.println("Registry Server begins to work");
			while(true)
			{
				
				try {
					Socket client = socket.accept();
					System.out.println("Registry server connected " + client.toString());
					socket_handler handler = new socket_handler(client);
					handler.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}	
			}
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
				Class<?> name = obj.getClass();
				
				if (name.getSimpleName().equals("LookupMessage"))
				{
					//deal with look up
					LookupMessage look = (LookupMessage) obj;
					String obj_name = look.obj_name;
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
					System.out.println("Registry: register message got");
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

	
}
