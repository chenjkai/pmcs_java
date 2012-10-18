package pmcs.exception.connector;
/**
 * 
 * @author steven
 *
 */
public class XmlListOverFlowException extends Exception {
	private static final long serialVersionUID = 6739190255581017248L;
	private static final String MESSAGE_STRING = "在存储xml字节流的容器溢出时，抛出此异常。";
	
	public XmlListOverFlowException() {
		super(MESSAGE_STRING);
	}

}
