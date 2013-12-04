import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args)
	{
		if(args.length != 3)
		{
			System.err.println("Usage: java Client <host name> <user name> <password>");
			System.exit(1);
		}
		String hostName = args[0];
		String userName = args[1];
		String password = args[2];
		int portNumber = 16000;
		
		TeaCryptoManager cryptoManager = new TeaCryptoManager(password.getBytes());
		
		try {
			Socket socket = new Socket(hostName, portNumber);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			Response fromServer;
			String fromUser;
			
			System.out.println("Trying to encrypt initial message....");
			byte[] encryptedInitialMessage = cryptoManager.Encrypt(userName.getBytes());
			oos.writeObject(new Request(encryptedInitialMessage));
			
			while((fromServer = (Response) objectInputStream.readObject()) != null)
			{
				// Decrypt all messages from the server
				String decryptedMessage = cryptoManager.DecryptToString(fromServer.getMessage());
				if(decryptedMessage.equals("Exit"))
				{
					break;
				}
				else if(decryptedMessage.equals("OK"))
				{
					System.out.println("Successfully connected.");
					System.out.println("To Get a File: get <filename>");
					System.out.println("To Exit: \"exit \"");
				}
				else if(decryptedMessage.equals("NOTFOUND"))
				{
					System.out.println("File not found.");
				}
				else if(decryptedMessage.equals("UNKNOWNCOMMAND"))
				{
					System.out.println("Unknown command.");
				}
				else if(decryptedMessage.equals("BADARG"))
				{
					System.out.println("Usage: get <filename>");
				}
				else
				{
					// File name should be first
					String[] lines = decryptedMessage.split("\n");
					String filename = lines[0];
					File file = new File(filename);
					PrintWriter writer = new PrintWriter(file);
					for (int i = 1; i < lines.length; i++)
					{
						writer.println(lines[i]);
					}
					writer.close();
					System.out.println("File " + filename + " successfully downloaded.");
				}
				
				fromUser = stdIn.readLine();
				if(fromUser != null)
				{
					// Encrypt the damn thing:
					byte[] encryptedMessage = cryptoManager.Encrypt(fromUser.getBytes());				
					oos.writeObject(new Request(encryptedMessage));
				}
			}
			socket.close();
		}
		catch (EOFException eof)
		{
			System.out.println("Server closed the connection.");
			System.exit(1);
		}
		catch(SocketException e)
		{
			System.out.println(e.getMessage());
		}
		catch(IOException ioe)
		{
			System.out.println(ioe);
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println(cnfe);
		}
	}
}
