public class test {
	public static void main(String[] args)
	{
		String key = "cream";

		TeaCryptoManager tm = new TeaCryptoManager(key.getBytes());
		byte[] cipherText = tm.Encrypt("strawberry".getBytes());
		
		for(int i = 0; i < cipherText.length; i++)
		{
			System.out.println(cipherText[i]);
		}
		
		String newString = tm.DecryptToString(cipherText);
		System.out.println(newString);
		System.out.println("Are they equal? " + newString.equals("strawberry"));
		System.out.println("Decrypted Size: " + newString.length());
		System.out.println("Original Size: " + "strawberry".length());
	}
}
