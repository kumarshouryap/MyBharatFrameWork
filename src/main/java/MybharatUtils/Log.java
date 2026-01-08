package MybharatUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	
	public static final Logger logger = LogManager.getLogger(Log.class);
	
	public static void info(String message) {
		
		logger.info(message);
	}
	
	public static void warm(String message) {
		
		logger.warn(message);
		
	}

	public static void error(String message) {
	
		logger.error(message);
	}
}
