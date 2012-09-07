package nl.dennisg.file.driver;

import org.osgi.framework.ServiceReference;
import org.osgi.service.device.Constants;
import org.osgi.service.device.Device;
import org.osgi.service.device.Driver;
import org.osgi.service.log.LogService;

public class FileDriver implements Driver {

	private final static int MATCH = 10;
	
	private volatile LogService m_log;
	
	public int match(ServiceReference reference) throws Exception {
		//is required to be there...
		if (!"file".equals(reference.getProperty(Constants.DEVICE_CATEGORY))) {
			return Device.MATCH_NONE;
		}
	
		m_log.log(LogService.LOG_DEBUG, String.format("FileDriver returning match=%s", MATCH));
		
		return MATCH;
	};
	
	@Override
	public String attach(ServiceReference reference) throws Exception {
		m_log.log(LogService.LOG_DEBUG, "FileDriver.attach() called");
		
		//here you should register a service that does something with the file....
		return null;
	}
	
}
