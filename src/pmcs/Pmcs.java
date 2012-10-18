package pmcs;

import org.apache.log4j.Logger;

import pmcs.connector.Connector;
import pmcs.util.Util;

public class Pmcs {
	private static Logger logger = Util.getLogger(Pmcs.class);
	public static void main(String[] args) {
		Connector connector = new Connector();
		try {
			logger.info("尝试启动程序");
			connector.start();
//			Thread.sleep(30000);
//			connector.stop();
		} catch (Exception e) {
			logger.warn("程序启动出现异常，启动失败。",e);
			e.printStackTrace();
		}
	
	}

}
