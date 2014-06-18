package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import org.omg.CORBA.portable.InputStream;

import HelloWorld.*;
import MyRemote.MyRemote;
public class RemoteObjectReference implements Serializable{
	public String ipaddress;
	public int port;
	
	public String Interface_name;
	
	//name for registing 
	public String obj_name;
	public int download_port;
	//for downloading stub
	public String Stub_URL;
	public RemoteObjectReference(String ip, int port, String interface_name, 
			String obj_name, String Stub_URL, int download_port) {
		this.ipaddress = ip.substring(1);
		this.port = port;
		this.Interface_name = interface_name;
		this.obj_name = obj_name;
		this.Stub_URL = Stub_URL;
		this.download_port = download_port;
	}
	public RemoteObjectReference()
	{
		this.ipaddress = "127.0.0.1";
		this.port = 8001;
		this.obj_name = "test";
		this.Interface_name = "HelloWorld.HelloWorld_Interface";
	}
	
	public String get_obj_name()
	{
		return obj_name;
	}
	public MyRemote localize()
	{
		Class<?> name;
		try {
			name = Class.forName(this.Interface_name + "_stub");
			Object obj = name.newInstance();
			return (MyRemote)obj;	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				download(this.Interface_name + "_stub");
				System.out.println("Finish downloading file");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	public void download(String fileName) throws IOException
	{
		Socket socket = new Socket (ipaddress, download_port);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		out.write(fileName);;
		out.flush();
		File file = new File("./bin/" + fileName +".class");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		String response = "";
		try {
			while(( response = in.readLine() ) != null)	
			{
				//System.out.println(response);
				bw.write(response +"\n");
					
			}
			bw.close();
		} catch (IOException e) {
			bw.close();
			e.printStackTrace();
		}
	}
}
