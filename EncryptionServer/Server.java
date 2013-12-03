import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
	
	private static HashMap<String, String> Users;
	
	
	public static void main(String[] args) throws IOException {
		
		int portNumber = 16000;
		boolean listening = true;
		
		initializeServer();
		
		try
		{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (listening)
			{
				System.out.println("Creating a thread...");
				new ServerThread(serverSocket.accept(), Users).start();
			}
		}
		catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
	
	private static void initializeServer()
	{
		Users = new HashMap<String, String>();
		Users.put("chocolate", "banana");
	}
}
