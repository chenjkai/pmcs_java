package pmcs.exception;

/**
 * 空的对象。抛出此异常
 * 
 * @author steven
 * 
 */
public class PmcsNullPointerException extends Exception {
	private static final long serialVersionUID = -7866449350143462810L;

	public PmcsNullPointerException(String key) {
		super(key);
	}
}
