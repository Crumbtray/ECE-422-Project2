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
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
			System.out.println("Done initializing streams.");
			
			Request inputRequest;
			Response outputObject;
			FileServer fileServer = new FileServer();
			System.out.println("Done initializing FileServer.");
			outputObject = fileServer.initialize();
			oos.writeObject(outputObject);
			
			while((inputRequest = (Request) ois.readObject()) != null)
			{
				System.out.println("Processing input...");
				String decryptedInput = new String(TeaCryptoManager.Decrypt(inputRequest.buffer));
				
				if(decryptedInput.equals("Finish"))
				{
					break;
				}
				
				outputObject = fileServer.processInput(decryptedInput);
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
