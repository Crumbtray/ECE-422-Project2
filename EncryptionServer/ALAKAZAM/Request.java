import java.io.Serializable;


public class Request implements Serializable{

	/**
	 * Serializable Crap
	 */
	private static final long serialVersionUID = 1L;
	public byte[] buffer;
	
	public Request(byte[] input)
	{
		this.buffer = input;
	}

}
