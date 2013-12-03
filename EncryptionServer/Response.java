import java.io.Serializable;


public class Response implements Serializable{
	/**
	 * Serializable Crap
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	public Response(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return this.message;
	}
}
