package pmcs.exception.parser;
/**
 * 从socket读取字节流失败，抛出此异常
 * @author steven
 *
 */
public class ReadXmlBytesException extends Exception {
	private static final long serialVersionUID = 2520260950729955134L;
	public static final String MESSAGE_STRING = "读取Xml字节流失败:";
	
	public ReadXmlBytesException(String str) {
		super(MESSAGE_STRING + str);
	}
}
