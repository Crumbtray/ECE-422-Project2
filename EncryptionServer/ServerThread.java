import java.net.*;
import java.io.*;


public class ServerThread extends Thread {
	private Socket socket = null;
	
	public ServerThread(Socket socket)
	{
		super("ServerThread");
		this.socket = socket;
	}
	
	public void run() {
		try {
			OutputStream outputStream = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);
			TestObject to = new TestObject("You're a fag");
			oos.writeObject(to);
			oos.close();
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}
