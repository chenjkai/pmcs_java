package pmcs.exception.configuration;
/**
 * 试图传入空的key检索配置信息,那么抛出此异常
 * 
 * @author steven
 * 
 */

public class CanNotPassEmptyKey extends Exception {
	private static final long serialVersionUID = 3591068958484457631L;
	public static final String MESSAGE_STRING = "不能传入空key";

	public CanNotPassEmptyKey() {
		super(MESSAGE_STRING);
	}
}
