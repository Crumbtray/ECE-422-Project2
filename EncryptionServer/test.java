public class test {
	public static void main(String[] args)
	{
		String key = "chocolate";

		TeaCryptoManager tm = new TeaCryptoManager(key.getBytes());
		byte[] cipherText = tm.Encrypt("banana".getBytes());
		
		for(int i = 0; i < cipherText.length; i++)
		{
			System.out.println(cipherText[i]);
		}
		
		byte[] original = tm.Decrypt(cipherText);
		System.out.println(new String(original));
	}
}
