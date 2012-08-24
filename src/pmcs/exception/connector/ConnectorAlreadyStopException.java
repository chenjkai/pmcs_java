package pmcs.exception.connector;
/**
 * 在调用connector的stop()函数，如果已经停止，那么抛出该异常
 * @author steven
 *
 */
public class ConnectorAlreadyStopException extends Exception {
	private static final long serialVersionUID = -8418136945841075930L;
	private static final String MESSAGE_STRING = "connector已经停止";
	
	public ConnectorAlreadyStopException() {
		super(MESSAGE_STRING);
	}

}
