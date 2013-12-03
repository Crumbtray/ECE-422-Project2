import java.util.HashMap;

public class FileServer {
	private static HashMap<String, String> Users;
	
	public FileServer()
	{
		Users = new HashMap<String, String>();
		Users.put("chocolate", "banana");
	}
	
	public Response initialize()
	{
		Response returnVal = new Response("Please input User name.");
		return returnVal;
	}
	
	public Response processInput(String input)
	{
		Response returnVal = new Response(input);
		return returnVal;
	}
}
