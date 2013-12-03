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
		String userName = args[1];
		int portNumber = 16000;
		
		try {
			Socket socket = new Socket(hostName, portNumber);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			TestObject fromServer;
			String fromUser;
			
			while((fromUser = stdIn.readLine()) != null)
			{
				System.out.println("Client: " + fromUser);
				out.println(fromUser);
				out.flush();
				
				fromServer = (TestObject) objectInputStream.readObject();
				if(fromServer != null)
				{
					System.out.println("Received Object: " + fromServer.getMessage());
				}
			}
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
