package Server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * Download Service for service 
 * implemented through socket
 * */
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
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				String response = in.readLine();
				String fileName = "./bin/"+ response + ".class";
				FileInputStream br = new FileInputStream(fileName);
				int count = br.available();
		        System.out.println(count);
		         // create buffer
		        byte[] bs = new byte[count];
		        br.read(bs);
		        out.write(bs); 
				
				out.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
