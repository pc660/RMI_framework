package HelloWorld;
import MyRemote.MyRemote;
import java.util.*;
import Message.InvokeMessage; 
import Server.RemoteObjectReference;
import Communcation.Client;
public class HelloWorld_Interface_stub implements HelloWorld_Interface{
	RemoteObjectReference ror;
	@Override
	public String hellowitharg( String name){
		Object []args = { name};
		return (String) invoke("hellowitharg", args);
	}
	@Override
	public String hellowithoutarg(){
		Object []args = {};
		return (String) invoke("hellowithoutarg", args);
	}
	@Override
	public String hellowithreference(LinkedList<Character> list){
		Object []args = { list};
		return (String) invoke("hellowithreference", args);
	}
	@Override
	public String hellowithROR (HelloWorld_Interface a){
		Object []args = { a.getror()};
		return (String) invoke("hellowithROR", args);
	}
	@Override
	public String hellowithtest( String name , LinkedList<Character> list){
		Object []args = { name,list};
		return (String) invoke("hellowithtest", args);
	}
	public Object invoke(String function, Object[] args){
		InvokeMessage message = new InvokeMessage(function, args);
		message.ror = this.ror;
//		System.out.println(ror.ipaddress)m;
		Object return_value = Client.connect_to_server(message);
		return return_value;
	}
	@Override
	public void setror(RemoteObjectReference obj) {
		this.ror = obj;
		this.ror = obj;
	}
	@Override
	public RemoteObjectReference getror() {
		return this.ror;
	}
}