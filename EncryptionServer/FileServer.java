import java.io.*;

public class FileServer {
	
	private TeaCryptoManager CryptoManager;
	
	public FileServer(TeaCryptoManager cryptoManager)
	{
		this.CryptoManager = cryptoManager;
	}
	
	public Response processInput(String input)
	{
		String returnString = input;
		
		String[] commands = input.split(" ");
		System.out.println("FileServer sees:");
		for (int i = 0; i < commands.length; i++)
		{
			System.out.println(commands[i]);
		}
		if(commands[0].toLowerCase().equals("exit"))
		{
			returnString = "Exit";
		}
		else if(commands[0].toLowerCase().equals("get"))
		{
			if(commands.length != 2)
			{
				returnString = "BADARG";
			}
			else
			{
				return getFile(commands[1]);
			}	
		}
		else
		{
			returnString = "UNKNOWNCOMMAND";
		}
		
		Response returnVal = new Response(CryptoManager.Encrypt(returnString.getBytes()));
		return returnVal;
	}
	
	public Response getFile(String fileName)
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			sb.append(fileName);
			sb.append("\n");
			String line = br.readLine();
			while(line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			Response returnVal = new Response(CryptoManager.Encrypt(sb.toString().getBytes()));
			return returnVal;			
		}
		catch(IOException e)
		{
			String responseString = "NOTFOUND";
			Response returnVal = new Response(CryptoManager.Encrypt(responseString.getBytes()));
			return returnVal;
		}
	}
}
