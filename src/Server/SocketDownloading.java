package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDownloading extends Thread{
	public ServerSocket server;
	int port;
	
	public SocketDownloading( int port)
	{
		this.port = port; 
		try {
			server = new ServerSocket (port);
		} catch (IOException e) {
			System.out.println("Cannot initilize socket");
			e.printStackTrace();
		}
	}
	@Override
	public void run()
	{
		while(true)
		{
			try {
				Socket client = server.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				String response = in.readLine();
				String fileName = "./bin/"+ response + ".class";
				BufferedReader br = new BufferedReader(new FileReader(fileName));
				
				while(( response = br.readLine() ) != null)	
				{
					out.write(response + "\n");
					out.flush();
				}
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
}
