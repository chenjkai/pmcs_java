package pmcs.exception.configuration;
/**
 * 如果config.xml中不存在改条配置信息，那么抛出此异常
 * @author steven
 *
 */
public class KeyNotExitException extends Exception {
	
	private static final long serialVersionUID = 8906272913432789037L;
	public static final String MESSAGE_STRING = "不存在该配置信息：";

	public KeyNotExitException(String key) {
		super(MESSAGE_STRING + key);
	}
}
