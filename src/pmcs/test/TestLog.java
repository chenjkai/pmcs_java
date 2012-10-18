package pmcs.test;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import pmcs.exception.connector.ConnetorInitialException;

public class TestLog {
	private static Logger logger = Logger.getLogger(TestLog.class); 
	private static String filePath = System.getProperty("user.dir") + File.separator
			+ "conf" + File.separator + "log4j.properties";
	
	public static void main(String[] args) {
		PropertyConfigurator.configure(filePath);
		logger.info("test info");
		logger.debug("test debug");
		try {
			throw new ConnetorInitialException();
		} catch (Exception e) {
			logger.warn("test", e);
		}
	}

}
