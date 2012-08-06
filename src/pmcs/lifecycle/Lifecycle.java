package pmcs.lifecycle;

public interface Lifecycle {
	public void start();
	public void stop();
	public void tryToRecover();
}
