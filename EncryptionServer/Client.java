import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args)
	{
		if(args.length != 2)
		{
			System.err.println("Usage: java Client <host name> <userName>");
			System.exit(1);
		}
		String hostName = args[0];
		int portNumber = 16000;
		
		
		try {
			Socket socket = new Socket(hostName, portNumber);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			TestObject fromServer;
			String fromUser;
			
			while((fromUser = stdIn.readLine()) != null)
			{
				System.out.println("Client: " + fromUser);
				// Encrypt the damn thing:
				byte[] encryptedMessage = TeaCryptoManager.Encrypt(fromUser.getBytes());				
				oos.writeObject(new Request(encryptedMessage));
				
				fromServer = (TestObject) objectInputStream.readObject();
				if(fromServer != null)
				{
					System.out.println("Received Object: " + fromServer.getMessage());
				}
			}
		}
		catch (EOFException eof)
		{
			System.out.println("Server closed the connection.");
			System.exit(1);
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
