import java.nio.ByteBuffer;
import java.security.*;

public class test {
	public static void main(String[] args)
	{
		String key = "Blah";
		try {
			int[] buf = new int[4];
			MessageDigest md = MessageDigest.getInstance("MD5");
			ByteBuffer bb = ByteBuffer.wrap(md.digest(key.getBytes()));
			for (int i = 0; i < buf.length; i++)
			{
				buf[i] = bb.getInt();
			}
			
			for (int i = 0; i < buf.length; i++)
			{
				System.out.println(buf[i]);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
