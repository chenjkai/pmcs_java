package pmcs.exception.parser;
/**
 * 解析ParsedDataElement失败，抛出此异常
 * @author steven
 *
 */
public class ParseParsedDataElementException extends Exception {
	private static final long serialVersionUID = -9085050918092747071L;
	public static final String MESSAGE_STRING = "解析ParsedDataElement数据失败：";
	
	public ParseParsedDataElementException(String str) {
		super(MESSAGE_STRING + str);
	}
}
