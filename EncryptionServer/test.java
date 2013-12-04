import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.*;
import java.util.Arrays;

public class test {
	public static void main(String[] args)
	{
		String key = "Blagg";
		TeaCryptoManager tm = new TeaCryptoManager(key.getBytes());
		byte[] returnVal = tm.Encrypt("Hi".getBytes());
		
		for(int i = 0; i < returnVal.length; i++)
		{
			System.out.println(returnVal[i]);
		}
		
	}
}
