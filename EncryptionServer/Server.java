import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	
	public static HashMap<String, String> Users;
	
	public static void main(String[] args) throws IOException {
		
		initializeServer();
		
		int portNumber = 16000;
		boolean listening = true;
		
		try
		{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (listening)
			{
				new ServerThread(serverSocket.accept(), Users).start();
			}
			serverSocket.close();
		}
		catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
	
	private static void initializeServer()
	{
		Users = new HashMap<String, String>();
		Users = new HashMap<String, String>();
		Users.put("chocolate", "banana");
		Users.put("strawberry", "cream");
	}
}
