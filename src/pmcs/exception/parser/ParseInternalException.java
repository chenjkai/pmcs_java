package pmcs.exception.parser;
/**
 * 解析Internal失败，抛出此异常
 * @author steven
 *
 */
public class ParseInternalException extends Exception {
	private static final long serialVersionUID = 6560586738934606540L;
	public static final String MESSAGE_STRING = "解析Internal失败：";
	
	public ParseInternalException(String str) {
		super(MESSAGE_STRING + str);
	}
}
