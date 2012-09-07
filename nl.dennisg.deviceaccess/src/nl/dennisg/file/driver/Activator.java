package nl.dennisg.file.driver;

import java.util.Properties;

import nl.dennisg.specific.driver.VerySpecificFileDriver;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.device.Constants;
import org.osgi.service.device.Driver;
import org.osgi.service.log.LogService;

/**
 * This is the OSGi {@link BundleActivator} for the base driver
 * 
 * This 
 * @author dennisg
 *
 */
public class Activator extends DependencyActivatorBase {

	
	@Override
	public void init(BundleContext context, DependencyManager manager) throws Exception {
		
		@SuppressWarnings("serial")
		Properties p = new Properties() {{
			put(Constants.DRIVER_ID, "nl.dennis.file.driver");
		}};
		
		manager.add(createComponent().setImplementation(FileDriver.class).setInterface(Driver.class.getName(), p)
			.add(createServiceDependency().setService(LogService.class).setRequired(true))
		);
		
	}
	
	@Override
	public void destroy(BundleContext context, DependencyManager manager) throws Exception {}
}
