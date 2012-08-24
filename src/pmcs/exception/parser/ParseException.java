package pmcs.exception.parser;
/**
 * 解析xml失败，抛出此异常
 * @author steven
 *
 */
public class ParseException extends Exception {
	private static final long serialVersionUID = 7170595857687855289L;
	public static final String MESSAGE_STRING = "解析xml失败：";
	
	public ParseException() {
		super(MESSAGE_STRING);
	}
}
