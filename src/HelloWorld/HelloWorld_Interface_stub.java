package HelloWorld;
import java.util.LinkedList;

import Message.InvokeMessage;
import Server.RemoteObjectReference;
import Communcation.Client;
public class HelloWorld_Interface_stub implements HelloWorld_Interface{
	RemoteObjectReference ror;
	@Override
	public String hellowitharg(String name) {
		Object[] args = {name };
		
		return (String) invoke ("hellowitharg", args);
		
	}
	@Override
	public String hellowithreference(LinkedList<Character> list)
	{
		Object[] args = { list };
		return (String) invoke ("hellowithreference", args);
		
	}
	@Override
	public String hellowithoutarg() {
		// TODO Auto-generated method stub
		return null;
	}
	public Object invoke(String function, Object[] args)
	{
		InvokeMessage message = new InvokeMessage(function, args);
		System.out.println(this.ror.ipaddress);
		System.out.println(this.ror.port);
		message.ror = this.ror;
		//message.obj = obj;
		//Object return_value = null;
		//return return_value;
		Object return_value = Client.connect_to_server(message);
		return return_value;
	}

	


	@Override
	public void setror(RemoteObjectReference obj) {
		this.ror = obj;
		
	}

	@Override
	public RemoteObjectReference getror() {
		// TODO Auto-generated method stub
		return this.ror;
	}
	@Override
	public String hellowithROR(HelloWorld_Interface a) {
		Object[] args = {a.getror()};

		return (String) invoke ("hellowithROR", args);
	}
	
}
