package pmcs.exception.handler;
/**
 * 在调用handler的start()函数，如果已经启动，那么抛出该异常
 * @author steven
 *
 */
public class HandlerAlreadyStartException extends Exception {

	private static final long serialVersionUID = 6629301360138467608L;
	private static final String MESSAGE_STRING = "已经启动";
	
	public HandlerAlreadyStartException(int num) {
		super("handler" + num +MESSAGE_STRING);
	}

}
