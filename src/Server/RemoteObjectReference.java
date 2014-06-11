package Server;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
	
	//for downloading stub
	public String Stub_URL;
	public RemoteObjectReference(String ip, int port, String interface_name, 
			String obj_name, String Stub_URL) {
		this.ipaddress = ip;
		this.port = port;
		this.Interface_name = interface_name;
		this.obj_name = obj_name;
		this.Stub_URL = Stub_URL;
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
		URL url = new URL(Stub_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
        
        if (responseCode == HttpURLConnection.HTTP_OK) {
        	BufferedReader input = new BufferedReader( new InputStreamReader( httpConn.getInputStream() ) );

        	Writer writer = new OutputStreamWriter( new FileOutputStream( "./"+ fileName + ".class" ) );

        	int c;

        	while( ( c = input.read() ) != -1 ) {

        	   writer.write( (char)c );
        	}

        	writer.close();

        	input.close();
        	
            }
	}
}
