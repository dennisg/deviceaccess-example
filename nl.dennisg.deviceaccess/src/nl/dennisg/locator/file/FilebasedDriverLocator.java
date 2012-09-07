package nl.dennisg.locator.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;

import org.osgi.service.device.DriverLocator;
import org.osgi.service.log.LogService;

public class FilebasedDriverLocator implements DriverLocator {

	private final File m_root = new File("generated");
	private volatile LogService m_log;
	
	private Map<String, File> m_jars;
	
	@SuppressWarnings("unused")
	private void start() {
		m_root.mkdirs();
		m_jars = new HashMap<String, File>();
		m_log.log(LogService.LOG_DEBUG, "DriverLocator started");
		
		for (File jar : m_root.listFiles()) {
			check(jar);
		}
	}
	
	
	private void check(File f) {
		try {
			JarFile jf = new JarFile(f);
			String ids = jf.getManifest().getMainAttributes().getValue("Driver-Ids");
			if (ids == null) return;
			for (String id : ids.split(",")) {
				m_jars.put(id.trim(), f);
				System.out.println(id + " : " + f);
			}
		} catch (IOException e) {
			//ignore
		}
	}
	
	@Override
	public String[] findDrivers(Dictionary props) {
		return m_jars.keySet().toArray(new String[0]);
	}
	
	@Override
	public InputStream loadDriver(String id) throws IOException {
		m_log.log(LogService.LOG_DEBUG, "Loading driver jar for: " + id);
		return new FileInputStream(m_jars.get(id));
	}
	
}
