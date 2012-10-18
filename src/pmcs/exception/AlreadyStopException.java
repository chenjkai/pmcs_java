package pmcs.exception;
/**
 * 在调用的stop()函数，如果已经停止，那么抛出该异常
 * @author steven
 *
 */
public class AlreadyStopException extends Exception {
	private static final long serialVersionUID = -8418136945841075930L;
	private static final String MESSAGE_STRING = "connector已经停止";
	
	public AlreadyStopException(String msg) {
		super(msg + ":" + MESSAGE_STRING);
	}

}
