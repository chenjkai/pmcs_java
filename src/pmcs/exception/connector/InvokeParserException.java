package pmcs.exception.connector;
/**
 * Connect调用解析器失败，那么抛出此异常
 * @author steven
 *
 */
public class InvokeParserException extends Exception {
	
	private static final long serialVersionUID = 7048840669999932918L;
	public static final String MESSAGE_STRING = "解析xml数据异常";

	public InvokeParserException() {
		super(MESSAGE_STRING);
	}
}
