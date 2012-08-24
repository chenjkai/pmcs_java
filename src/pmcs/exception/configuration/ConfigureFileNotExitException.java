package pmcs.exception.configuration;
/**
 * 如果con目录下config.xml配置文件不存在
 * 那么抛出此异常
 * 
 * @author steven
 *
 */
public class ConfigureFileNotExitException extends Exception {
	private static final long serialVersionUID = -7432007242932552159L;
	public static final String MESSAGE_STRING = "不存在改配置文件：";

	public ConfigureFileNotExitException(String filePath) {
		super(MESSAGE_STRING + filePath);
	}
}
