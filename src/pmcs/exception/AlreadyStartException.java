package pmcs.exception;
/**
 * 在调用start()函数，如果已经启动，那么抛出该异常
 * @author steven
 *
 */
public class AlreadyStartException extends Exception {
	private static final long serialVersionUID = -5881859710614746761L;
	private static final String MESSAGE_STRING = "已经启动";
	
	public AlreadyStartException(String msg) {
		super(msg+ ":" +MESSAGE_STRING);
	}

}
