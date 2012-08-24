package pmcs.exception.connector;
/**
 * 在调用connector的start()函数，如果已经启动，那么抛出该异常
 * @author steven
 *
 */
public class ConnectorAlreadyStartException extends Exception {
	private static final long serialVersionUID = -5881859710614746761L;
	private static final String MESSAGE_STRING = "connector已经启动";
	
	public ConnectorAlreadyStartException() {
		super(MESSAGE_STRING);
	}

}
