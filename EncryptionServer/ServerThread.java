import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread {
	private enum ServerState {
		AUTH, SERVE
	}
	
	private Socket socket = null;
	private HashMap<String, String> Users = null;
	private ServerState state;
	private TeaCryptoManager cryptoManager;
	private String User;
	
	public ServerThread(Socket socket, HashMap<String, String> users)
	{
		super("ServerThread");
		this.Users = users;
		this.socket = socket;
		this.state = ServerState.AUTH;
	}
	
	public void run() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			
			Request inputRequest;
			Response outputObject;
			
			while((inputRequest = (Request) ois.readObject()) != null)
			{
				if(this.state == ServerState.AUTH)
				{
					if(tryAuthorize(inputRequest.buffer))
					{
						this.state = ServerState.SERVE;
						String response = "OK";
						outputObject = new Response(cryptoManager.Encrypt(response.getBytes()));
						oos.writeObject(outputObject);
						oos.flush();
					}
					else
					{
						break;
					}
				}
				else
				{
					FileServer fileServer = new FileServer(cryptoManager);
					String decryptedInput = new String(cryptoManager.Decrypt(inputRequest.buffer));
					outputObject = fileServer.processInput(decryptedInput);
					oos.writeObject(outputObject);
					oos.flush();
				}	
			}
						
			oos.close();			
			socket.close();
		}
		catch(EOFException eof)
		{		
			System.out.println("Connection terminated.");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	public boolean tryAuthorize(byte[] buffer)
	{
		for(String value : this.Users.values())
		{
			TeaCryptoManager decryptor = new TeaCryptoManager(value.getBytes());
			String username = decryptor.DecryptToString(buffer);
			String possiblePassword = this.Users.get(username);
			if(possiblePassword != null)
			{
				this.User = username;
				this.cryptoManager = decryptor;
				return true;
			}
		}
		
		return false;
	}
}
