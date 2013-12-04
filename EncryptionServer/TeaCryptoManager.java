import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.*;

public class TeaCryptoManager {
	private int[] key;
	
	public TeaCryptoManager(byte[] key)
	{
		System.loadLibrary("TeaCryptoManager");
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
			if((i+1) < buf.length)
			{
				ciphertext[1] = buf[i+1];
			}
			else
			{
				ciphertext[1] = 0;
			}
			NativeEncrypt(ciphertext, key);
			ib2.put(ciphertext);
		}
		
		// Return the full ciphertext.
		System.out.println("Full cipher text length: " + fullCiphertext.length);
		ByteBuffer bb2 = ByteBuffer.allocate(fullCiphertext.length * 4);
		IntBuffer intBuffer = bb2.asIntBuffer();
		System.out.println("Character Buffer length: " + bb2.capacity());
		intBuffer.put(fullCiphertext);
		return bb2.array();
	}

	public byte[] Decrypt(byte[] input)
	{
		//Convert the byte array into an int array.
		IntBuffer intBuf = ByteBuffer.wrap(input).asIntBuffer();
		int[] cipherInt = new int[intBuf.remaining()];
		intBuf.get(cipherInt);
		
		int[] plainTextFrags = new int[2];
		int[] plainText = new int[cipherInt.length];
		IntBuffer ib = IntBuffer.wrap(plainText);
		// For every two integers in the array, we decrypt them.
		// Then we place it into a giant plaintext int array.
		for(int i = 0; i < cipherInt.length; i+=2)
		{
			plainTextFrags[0] = cipherInt[i];
			plainTextFrags[1] = cipherInt[i+1];
			NativeDecrypt(plainTextFrags, key);
			ib.put(plainTextFrags);
		}
		
		// Convert the plain text back into a character array and return.
		ByteBuffer bb = ByteBuffer.allocate(plainText.length * 4);
		IntBuffer ib2 = bb.asIntBuffer();
		ib2.put(plainText);
		return bb.array();
		
	}

	public native void NativeEncrypt(int[] v, int[] k);
	
	public native void NativeDecrypt(int[] v, int[] k);
}
