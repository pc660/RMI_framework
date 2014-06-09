package Message;

import java.io.Serializable;

import Server.RemoteObjectReference;

public class Message implements Serializable{
	public RemoteObjectReference ror;
	public String obj_name()
	{
		return ror.get_obj_name();
	}
	public void set_ror (RemoteObjectReference ror)
	{
		this.ror = ror;
	}
}
