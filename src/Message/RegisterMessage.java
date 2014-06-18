package Message;

import Server.RemoteObjectReference;

public class RegisterMessage extends Message{
	public String obj_name;
	public RemoteObjectReference ror;
	public RegisterMessage(String obj_name, RemoteObjectReference ror) {
		this.obj_name = obj_name;
		this.ror = ror;
	}
	
	public String obj_name()
	{
		return ror.get_obj_name();
	}
	
	public RemoteObjectReference get_ror(){
		return ror;
	}
}
