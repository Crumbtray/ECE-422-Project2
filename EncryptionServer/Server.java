import java.net.*;
import java.io.*;

public class Server {
	
	public static void main(String[] args) throws IOException {
		
		int portNumber = 16000;
		boolean listening = true;
		
		try
		{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			while (listening)
			{
				System.out.println("Creating a thread...");
				new ServerThread(serverSocket.accept()).start();
			}
		}
		catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
}
