import java.io.Serializable;


public class Response implements Serializable{
	/**
	 * Serializable Crap
	 */
	private static final long serialVersionUID = 1L;
	private byte[] message;
	public Response(byte[] message)
	{
		this.message = message;
	}
	
	public byte[] getMessage()
	{
		return this.message;
	}
}
