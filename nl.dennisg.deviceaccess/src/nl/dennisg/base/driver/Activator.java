package nl.dennisg.base.driver;

import java.util.Properties;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
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
			put("description", "base driver for dectecting files");
		}};
		
		manager.add(createComponent().setImplementation(BaseDriver.class).setInterface(Object.class.getName(), p)
			.add(createServiceDependency().setService(LogService.class).setRequired(true))
		);
		
	}
	
	@Override
	public void destroy(BundleContext context, DependencyManager manager) throws Exception {}
}
