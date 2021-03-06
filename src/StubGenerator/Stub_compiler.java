package StubGenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.lang.*;

import Communcation.Client;
import HelloWorld.HelloWorld_Interface;
import Message.InvokeMessage;
import Server.RemoteObjectReference;
//input a class file and return a stub
public class Stub_compiler {

	public String fileName;
	public ArrayList<String> interface_code;
	public ArrayList<String> import_code;
	public String package_code;
	public File output;
	public ArrayList<String> return_type;
	public ArrayList<String> interface_name;
	public Stub_compiler (String name)
	{
		fileName = name;
		interface_code = new ArrayList<String> ();
		import_code = new ArrayList<String> ();
		package_code = "";
		return_type = new ArrayList<String> ();
		interface_name = 	new ArrayList<String> ();
	}

	public void generate_stub ()
	{
		try {
			readFile (this.fileName);
			String name = this.fileName;
			name = name.substring(0, name.length() - 5);
			output = new File (name + "_stub.java");
			FileWriter fw = new FileWriter(output.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			generatePackage(bw);
			generateImport(bw);
			generateInterface(bw);
			generateInvoke(bw);
			generateROR ( bw);
			bw.write("}");
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private String removeString2(String s)
	{
		String str = "";
		int count = 0;
		for (int i = 0; i< s.length() ; i++)
		{
			char c = s.charAt(i);
			if (c!= '(' && c!=')'  && c != ',' && count == 0)
			{
				str = str + c;
			}
			else if (c == ',')
			{
				str = str + ' ';
			}
			else if (c == '(')
			{
				count = 1;
			}
			else if (c == ')')
			{
				count = 0;
				str = str + " ";
			}
		}
		str = str.trim();
		return str ;
	}
	private String removeString (String s)
	{
		String str = "";
		int count = 0;
		for (int i = 0; i< s.length() ; i++)
		{
			char c = s.charAt(i);
			if (c!= '<' && c!='>'  && c != ',' && count == 0)
			{
				str = str + c;
			}
			else if (c == ',')
			{
				str = str + ' ';
			}
			else if (c == '<')
			{
				count = 1;
			}
			else if (c == '>')
			{
				count = 0;
				str = str + " ";
			}
		}
		str = str.trim();
		return str ;
	}
	public ArrayList<String> parseInterface(String s)
	{

		int pos = s.indexOf("(");
		String str = s.substring(pos + 1, s.length() - 1);
		String name = ""; 
		int count = 0;
		String ss = removeString (str);
		ArrayList<String> parameter_name = parseString (ss);
		return parameter_name;
	}
	public void generatePackage(BufferedWriter bw) throws IOException
	{
		bw.write(package_code);
		bw.write(";\n");
	}
	public void generateImport (BufferedWriter bw) throws IOException
	{
		for (String s : import_code){
			bw.write(s);
			bw.write(";\n");
		}
		bw.write("import Message.InvokeMessage; \n");
		bw.write("import Server.RemoteObjectReference;\n");
		bw.write("import Communcation.Client;\n");
	}
	public void generateInvoke (BufferedWriter bw) throws IOException
	{
		bw.write("	public Object invoke(String function, Object[] args){\n");
		bw.write("		InvokeMessage message = new InvokeMessage(function, args);\n");
		bw.write("		message.ror = this.ror;\n");
		bw.write("		Object return_value = Client.connect_to_server(message);\n");
		bw.write("		return return_value;\n");
		bw.write("	}\n");

	}

	public void generateROR (BufferedWriter bw) throws IOException
	{
		bw.write("	@Override\n");
		bw.write("	public void setror(RemoteObjectReference obj) {\n");
		bw.write("		this.ror = obj;\n");
		bw.write("		this.ror = obj;\n");
		bw.write("	}\n");
		bw.write("	@Override\n");
		bw.write("	public RemoteObjectReference getror() {\n");
		bw.write("		return this.ror;\n");
		bw.write("	}\n");
	}

	public void generateInterface (BufferedWriter bw) throws IOException
	{
		String [] args = fileName.split("/");
		String name = args[args.length -1 ];
		name = name.substring(0, name.length() -5 );
		bw.write("public class " + name + "_stub" + " implements "  + name + "{\n");
		bw.write("	RemoteObjectReference ror;\n");
		int count = 0;
		for (String s :interface_code)
		{
			bw.write("	@Override\n");
			s = s.trim();
			bw.write("	" + s + "{\n");
			ArrayList<String> parameter_name  = parseInterface (s);
			String parameters = "{ ";
			String new_package_code = package_code.substring(7);
			new_package_code = new_package_code.trim();
			for (int i = 1 ; i < parameter_name.size() ; i = i+2)
			{
				try {

					String str = Class.forName(new_package_code + "."+parameter_name.get(i-1)).getInterfaces()[0].getSimpleName();
					if (str.equals("MyRemote"))
					{
						parameters = parameters +  parameter_name.get(i) + ".getror()," ;
					}
					else
					{
						parameters = parameters + parameter_name.get(i);
						parameters += ",";
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					parameters = parameters + parameter_name.get(i);
					parameters += ",";

				} 


			}
			parameters = parameters.substring(0, parameters.length()-1);
			parameters += "}";
			bw.write("		Object []args = " + parameters + ";\n");
			bw.write("		return (" + return_type.get(count) + ") invoke(\"" + interface_name.get(count) + "\", args);\n");
			count ++ ;
			bw.write("	}\n");
		}
	}
	private ArrayList<String>  parseString (String line)
	{
		ArrayList<String> results = new ArrayList<String> ();
		int i = 0;
		String s = "";
		int count = 0;
		line = line.trim();
		while (i < line.length())
		{
			if (line.charAt(i) != ' ' && line.charAt(i) != '	'  )
			{
				s = s + line.charAt(i);
				i++;
			}
			else
			{
				results.add(s);
				//	System.out.println(s);
				s = "";

				while(i < line.length())
				{
					if (line.charAt(i) == ' ' || line.charAt(i) == '	')
					{
						i++;
					}
					else{
						//						System.out.println(line.charAt(i));
						break;

					}
				}

			}
		}
		results.add(s);
		return results;
	}
	private void readFile( String file ) throws IOException {
		BufferedReader reader = new BufferedReader( new FileReader (file));
		String         line = null;
		StringBuilder  stringBuilder = new StringBuilder();
		while( ( line = reader.readLine() ) != null ) {
			stringBuilder.append(line);
		}
		String contents = stringBuilder.toString();
		String []args = contents.split(";");
		for (String s : args)
		{
			s = s.trim();
			ArrayList<String> results  = parseString(s);

			for (String ss:results)
			{

				//System.out.println(ss);
				if (ss.equals("package")){
					package_code = s;
					break;
				}
				else if (ss.equals("import"))
				{
					import_code.add(s);
					break;
				}
				else if (ss.equals("interface"))
				{
					int pos = s.indexOf("{");
					String str = s.substring(pos+1);
					interface_code.add(str);
					str = removeString2(str);
					ArrayList<String> res = parseString (str);
					return_type.add(res.get(1));
					interface_name.add(res.get(2));

					break;
				}

				else if (ss.equals("public")  && !s.contains("{"))
				{
					interface_code.add(s);
					s = removeString2(s);

					ArrayList<String> res = parseString (s);

					return_type.add(res.get(1));
					interface_name.add(res.get(2));
					break;
				}
			}
		}

	}
}
