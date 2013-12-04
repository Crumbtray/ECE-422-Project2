import java.nio.ByteBuffer;
import java.security.*;

public class TeaCryptoManager {
	private int[] key;
	
	public TeaCryptoManager(byte[] key)
	{
		// Convert the key into a 128-bit integer key.
		this.key = new int[4];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			ByteBuffer bb = ByteBuffer.wrap(md.digest(key));
			for (int i = 0; i < this.key.length; i++)
			{
				this.key[i] = bb.getInt();
			}
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println(e.getLocalizedMessage());
		}	
	}
	
	public byte[] Encrypt(byte[] input)
	{
		return input;
	}

	public byte[] Decrypt(byte[] input)
	{
		return input;
	}

	public native void NativeEncrypt(int[] v, int[] k);
	
	public native void NativeDecrypt(int[] v, int[] k);
}