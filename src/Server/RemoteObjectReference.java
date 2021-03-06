package Server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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


/*
 * Remote Object reference
 * 
 * */
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
		this.ipaddress = ip.substring(0);
		this.port = port;
		this.Interface_name = interface_name;
		this.obj_name = obj_name;
		this.Stub_URL = Stub_URL;
		this.download_port = download_port;
		//to avoid situation that it is a local address
		if (ip.contains("0.0.0.0"))
		{
			this.ipaddress = "127.0.0.1";
		}
		
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
	
	/*
	 * localize ror
	 * if there is not a local copy, then download from the server 
	 * */
	public MyRemote localize()
	{
		Class<?> name;
		try {
			name = Class.forName(this.Interface_name + "_stub");
			Object obj = name.newInstance();
			return (MyRemote)obj;	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			Object obj= null;
			try {
				System.out.println("Start downloading file:" + this.Interface_name + "_stub");
				download(Stub_URL);
				System.out.println("Finish downloading file");
				name = Class.forName(this.Interface_name + "_stub");
				obj = name.newInstance();
				return (MyRemote)obj;	
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return (MyRemote) obj;
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	/*
	 * Download ror from the server through socket
	 * Here I hard code for the current directory
	 * Can add configurations later
	 * 
	 * */
	public void download(String fileName) throws IOException
	{
		Socket socket = new Socket (ipaddress, download_port);
		DataInputStream in = new DataInputStream(socket.getInputStream());
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		out.write(fileName + "\n");
		out.flush();
		String []args = fileName.split(".");
		String directory = "";
		
		File file = new File("./bin/" + fileName + ".class" );
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream output = new FileOutputStream(file);
		
		//FileWriter fw = new FileWriter(file.getAbsoluteFile());
		//BufferedWriter bw = new BufferedWriter(fw);

		String response = "";
		 byte[] buff = new byte[1024];
		 byte[] resultBuff = new byte[0];

		    
		 int k = -1;
		try {
			 
			 while((k = socket.getInputStream().read(buff, 0, buff.length)) > -1) {
			        byte[] tbuff = new byte[resultBuff.length + k]; // temp buffer size = bytes already read + bytes last read
			        System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length); // copy previous bytes
			        System.arraycopy(buff, 0, tbuff, resultBuff.length, k);  // copy current lot
			        resultBuff = tbuff; // call the temp buffer as your result buff
			    }
			 System.out.println(resultBuff.length + " bytes read.");
			 output.write(resultBuff);
			 output.close();
		} catch (IOException e) {
			output.close();
			e.printStackTrace();
		}
	}
}
