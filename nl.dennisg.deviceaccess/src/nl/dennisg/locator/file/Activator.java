package nl.dennisg.locator.file;

import java.util.Properties;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.device.DriverLocator;
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
			put("description", "'a driver locator'");
		}};
		
		manager.add(createComponent().setImplementation(FilebasedDriverLocator.class).setInterface(DriverLocator.class.getName(), p)
			.add(createServiceDependency().setService(LogService.class).setRequired(true))
		);
		
	}
	
	@Override
	public void destroy(BundleContext context, DependencyManager manager) throws Exception {}
}
