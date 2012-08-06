package pmcs.exception.util;
/**
 * 如果域名转换成ip失败，那么抛出此异常
 * @author steven
 *
 */
public class UnCorrectToGetDomainException extends Exception {
	
	private static final long serialVersionUID = 1863280464488326941L;
	public static final String MESSAGE_STRING = "域名转换ip失败";

	public UnCorrectToGetDomainException() {
		super(MESSAGE_STRING);
	}
}
