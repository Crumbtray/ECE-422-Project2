import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args)
	{
		if(args.length != 1)
		{
			System.err.println("Usage: java Client <host name>");
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
			Response fromServer;
			String fromUser;
			
			while((fromServer = (Response) objectInputStream.readObject()) != null)
			{
				System.out.println("Received Object: " + fromServer.getMessage());
							
				fromUser = stdIn.readLine();
				if(fromUser != null)
				{
					// Encrypt the damn thing:
					byte[] encryptedMessage = TeaCryptoManager.Encrypt(fromUser.getBytes());				
					oos.writeObject(new Request(encryptedMessage));
					System.out.println("Client: " + fromUser);
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
