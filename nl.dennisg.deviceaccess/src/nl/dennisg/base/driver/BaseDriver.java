package nl.dennisg.base.driver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.felix.dm.Component;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.dm.ServiceDependency;
import org.osgi.framework.ServiceReference;
import org.osgi.service.device.Constants;
import org.osgi.service.log.LogService;

public class BaseDriver implements Runnable {

	
	private volatile DependencyManager m_manager;
	private volatile LogService m_log;
	private volatile ServiceReference m_ref;
	
	private ScheduledExecutorService m_executor;
	
	private File m_root = new File("devices");
	private Map<File, Component> m_files;
	
	private int m_lastCount;
	
	@SuppressWarnings("unused")
	private void start() {
		m_lastCount = -1;
		m_files = new HashMap<File, Component>();
		m_executor = Executors.newSingleThreadScheduledExecutor();
		m_executor.scheduleAtFixedRate(this, 1, 5, TimeUnit.SECONDS);
		m_log.log(LogService.LOG_INFO, String.format("Base driver has started: it is checking: %s", m_root));
		m_root.mkdirs();
	}
	
	@SuppressWarnings("unused")
	private void stop() {
		m_executor.shutdownNow();
	}
	
	private void safeCall() {
		File[] files = m_root.listFiles();
		if (m_lastCount < files.length) {
			m_log.log(LogService.LOG_DEBUG, String.format("Found %d files", files.length));
			m_lastCount = files.length;
		}
		
		for (File file : files) {
			if (m_files.keySet().contains(file)) continue;
			registerDevice(file);
		}
		checkMissing();
	}
	
	@Override
	public void run() {
		try { safeCall(); } catch (Exception e) {}
	}
	
	private void registerDevice(final File f) {
		
		@SuppressWarnings("serial")
		Properties p = new Properties() {{
			put(Constants.DEVICE_CATEGORY, "file");
			put(Constants.DEVICE_DESCRIPTION, "'a filesystem file'");
			put(Constants.DEVICE_SERIAL, f.getName());
		}};

		Component cmp = createComponent()
			.setImplementation(f).setInterface(File.class.getName(), p)
			//this ensures that when the base driver is unregistered, all devices that are registered
			//by it are also removed (unregistered)
			.add(createServiceDependency().setService(Object.class, m_ref).setRequired(true))
			.add(createServiceDependency().setService(LogService.class).setRequired(false));
		add(cmp);
		
		m_files.put(f, cmp);
		m_log.log(LogService.LOG_DEBUG, String.format("registered a device service for: %s", f));
	}
	
	
	private void checkMissing() {
		for (File f : m_files.keySet()) {
			if (!f.exists()) {
				//unregister the device; this will tear down all depending services
				Component cmp = m_files.remove(f);
				remove(cmp);
				m_log.log(LogService.LOG_DEBUG, String.format("file was lost: %s, removing the device that belongs to it; the rest will follow!"));
			}
		}
	}
	
	private void add(Component cmp) {
		m_manager.add(cmp);
	}
	
	private void remove(Component cmp) {
		m_manager.remove(cmp);
	}
	
	private Component createComponent() {
		return m_manager.createComponent();
	}
	
	private ServiceDependency createServiceDependency() {
		return m_manager.createServiceDependency();
	}
}
