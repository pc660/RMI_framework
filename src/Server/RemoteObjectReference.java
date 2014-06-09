package Server;
import java.io.Serializable;
import HelloWorld.*;
import MyRemote.MyRemote;
public class RemoteObjectReference implements Serializable{
	public String ipaddress;
	public int port;
	
	public String Interface_name;
	
	//name for registing 
	public String obj_name;
	
	//for downloading stub
//	public String Stub_URL;
	public RemoteObjectReference(String ip, int port, String interface_name, 
			String obj_name, String Stub_URL) {
		this.ipaddress = ip;
		this.port = port;
		this.Interface_name = interface_name;
		this.obj_name = obj_name;
	//	this.Stub_URL = Stub_URL;
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
}
