import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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
		// Pad input into array P, a multiple of 4
		int size = (input.length / 4) + (input.length % 4);
		byte[] p = new byte[size *4];
		ByteBuffer bb = ByteBuffer.wrap(p);
		bb.put(input);

		// Take P, and store it in an Int Array
		int[] buf = new int[size];
		IntBuffer ib = ByteBuffer.wrap(p).asIntBuffer();
		for (int i = 0; i < buf.length; i++)
		{
			buf[i] = ib.get();
		}
		
		// Cycling two Ints in the array, encipher.
		int[] ciphertext = new int[2];
		int[] fullCiphertext = new int[buf.length];
		IntBuffer ib2 = IntBuffer.wrap(fullCiphertext);
		
		for (int i = 0; i < buf.length; i+=2)
		{
			ciphertext[0] = buf[i];
			ciphertext[1] = buf[i+1];
			NativeEncrypt(ciphertext, key);
			ib2.put(ciphertext);
		}
		
		// Return the full ciphertext.
		ByteBuffer bb2 = ByteBuffer.allocate(fullCiphertext.length * 4);
		IntBuffer intBuffer = bb2.asIntBuffer();
		intBuffer.put(ciphertext);
		return bb2.array();
	}

	public byte[] Decrypt(byte[] input)
	{
		return input;
	}

	public native void NativeEncrypt(int[] v, int[] k);
	
	public native void NativeDecrypt(int[] v, int[] k);
}