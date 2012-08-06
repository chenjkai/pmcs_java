package pmcs.exception.connector;
/**
 * 如果connector初始化失败，那么抛出此异常
 * @author steven
 *
 */
public class ConnetorInitialException extends Exception {
	
	private static final long serialVersionUID = 1941225994860908778L;
	public static final String MESSAGE_STRING = "Connector初始化失败";

	public ConnetorInitialException() {
		super(MESSAGE_STRING);
	}
}
