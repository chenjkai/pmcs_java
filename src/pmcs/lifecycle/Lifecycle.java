package pmcs.lifecycle;
/**
 * 生命周期管理
 * @author steven
 *
 */
public interface Lifecycle {
	public void start() throws Exception;
	public void stop() throws Exception;
	public void tryToRecover() throws Exception;
}
