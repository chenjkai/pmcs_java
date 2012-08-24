package pmcs.exception.oraop;
/**
 * oraop类设置sql语句参数个数不符合表定义，抛出此异常
 * @author steven
 *
 */
public class OraopParamsCountException extends Exception {
	private static final long serialVersionUID = -4263359958079624914L;
	public static final String MESSAGE_STRING = "oraop类设置sql语句参数个数不符合表定义： packetType = ";
	
	public OraopParamsCountException(int packetType) {
		super(MESSAGE_STRING + packetType);
	}
}
