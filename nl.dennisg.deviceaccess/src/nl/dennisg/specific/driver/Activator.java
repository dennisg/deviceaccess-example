package nl.dennisg.specific.driver;

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
		Properties p1 = new Properties() {{
			put(Constants.DRIVER_ID, "nl.dennis.dennisg.file.driver");
		}};
		
		manager.add(createComponent().setImplementation(new VerySpecificFileDriver("dennisg.txt")).setInterface(Driver.class.getName(), p1)
			.add(createServiceDependency().setService(LogService.class).setRequired(true))
		);
		
		@SuppressWarnings("serial")
		Properties p2 = new Properties() {{
			put(Constants.DRIVER_ID, "nl.dennis.andriyf.file.driver");
		}};
		
		manager.add(createComponent().setImplementation(new VerySpecificFileDriver("andriyf.txt")).setInterface(Driver.class.getName(), p2)
			.add(createServiceDependency().setService(LogService.class).setRequired(true))
		);
		
		
	}
	
	@Override
	public void destroy(BundleContext context, DependencyManager manager) throws Exception {}
}
