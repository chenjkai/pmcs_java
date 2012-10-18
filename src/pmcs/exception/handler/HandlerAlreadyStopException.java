package pmcs.exception.handler;
/**
 * 在调用handler的stop()函数，如果已经停止，那么抛出该异常
 * @author steven
 *
 */
public class HandlerAlreadyStopException extends Exception {

	private static final long serialVersionUID = 5892659050979396629L;
	private static final String MESSAGE_STRING = "connector已经停止";
	
	public HandlerAlreadyStopException(int num) {
		super("handler" + num + MESSAGE_STRING);
	}

}
