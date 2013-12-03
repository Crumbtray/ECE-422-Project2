import java.net.*;
import java.util.*;
import java.io.*;


public class ServerThread extends Thread {
	private Socket socket = null;
	private HashMap Users = null;
	
	public ServerThread(Socket socket, HashMap users)
	{
		super("ServerThread");
		this.socket = socket;
		this.Users = users;
		System.out.println("Thread created.");
	}
	
	public void run() {
		try {
			System.out.println("Starting thread....");
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader in = new BufferedReader(inputStreamReader);
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
			System.out.println("Done initializing streams.");
			
			String inputLine;
			TestObject outputObject;
			FileServer fileServer = new FileServer();
			System.out.println("Done initializing FileServer.");
			
			while((inputLine = in.readLine()) != null)
			{
				System.out.println("Processing input...");
				if(inputLine.equals("Finish"))
				{
					break;
				}
				
				outputObject = fileServer.processInput(inputLine);
				oos.writeObject(outputObject);
			}
						
			oos.close();			
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
