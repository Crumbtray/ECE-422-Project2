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
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
			TestObject to = (TestObject) objectInputStream.readObject();
			if (to != null)
			{
				System.out.println(to.getMessage());
			}
			inputStream.close();
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
