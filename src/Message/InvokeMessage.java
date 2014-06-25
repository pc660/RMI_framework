package Message;

import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class InvokeMessage extends Message {
	public String method_name;
	public Object[] args;
	public Class[] type;
	public Object return_value;
	
	
	public InvokeMessage()
	{
		super();
	}
	
	public InvokeMessage ( String function, Object[] args)
	{
		this.method_name = function;
		this.args = args;
		if (args != null)
		{
			this.type = new Class[args.length];
			for (int i = 0; i< args.length ; i++)
			{
				this.type[i] = args[i].getClass();
			}
		}
	}
	
	
	public InvokeMessage invoke( Object caller)
	{
		Method method = null;
		try {
			method = caller.getClass().getMethod(method_name, type);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.return_value = method.invoke(caller, this.args);
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		InvokeMessage reply = new InvokeMessage();
		reply.return_value = this.return_value;
		return reply;
	}
}
