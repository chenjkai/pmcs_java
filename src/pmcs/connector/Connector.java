package pmcs.connector;

import pmcs.configuration.Configuration;
import pmcs.exception.configuration.CanNotPassEmptyKey;
import pmcs.exception.configuration.ConfigureFileNotExitException;
import pmcs.exception.configuration.KeyNotExitException;
import pmcs.exception.connector.ConnetorInitialException;
import pmcs.exception.util.UnCorrectToGetDomainException;
import pmcs.lifecycle.Lifecycle;
import pmcs.util.Util;

/**
 * 连接远程ip或者域名(domain)
 * 
 * @author steven
 * 
 */
public class Connector implements Lifecycle {
	private static String ADDRESS = null;
	private static int PORT = 0;
	private Configuration cfg = null;

	/**
	 * 初始化connector
	 * 
	 * @throws UnCorrectToGetDomainException
	 * @throws ConnetorInitialException
	 */
	private void initial() throws UnCorrectToGetDomainException,
			ConnetorInitialException {
		try {
			cfg = new Configuration();
			ADDRESS = cfg.getProperty("domain");
			PORT = Integer.parseInt(cfg.getProperty("port"));
			if (Util.isDomain(ADDRESS)) {
				ADDRESS = Util.getIpByDomain(ADDRESS);
				if (ADDRESS == null) {
					throw new UnCorrectToGetDomainException();
				}
			} else if (Util.isIp(ADDRESS)) {
				// something to do
			} else {
				throw new ConnetorInitialException();
			}

		} catch (CanNotPassEmptyKey e) {
			e.printStackTrace();
		} catch (KeyNotExitException e) {
			e.printStackTrace();
		} catch (ConfigureFileNotExitException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造函数
	 */
	public Connector() {
		super();

	}

	@Override
	public void start() {
		try {
			initial();
		} catch (UnCorrectToGetDomainException e) {
			e.printStackTrace();
		} catch (ConnetorInitialException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void stop() {

	}

	@Override
	public void tryToRecover() {

	}

}
