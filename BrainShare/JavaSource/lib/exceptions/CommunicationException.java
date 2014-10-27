package lib.exceptions;

public class CommunicationException extends Exception implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommunicationException(String s) {
        super(s);
    }
}
