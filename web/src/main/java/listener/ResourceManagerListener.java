package listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.InvalidPropertiesFormatException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import protocol.Config;

/**
 * Application Lifecycle Listener implementation class ResourceManagerListener
 * 
 */
public class ResourceManagerListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ResourceManagerListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		FileInputStream fis;
		String path;
		try {
			path = ResourceManagerListener.class.getClassLoader()
					.getResource("").toURI().getPath();
			fis = new FileInputStream(path + "config.xml");
			Config.prop.loadFromXML(fis);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
	}
}
