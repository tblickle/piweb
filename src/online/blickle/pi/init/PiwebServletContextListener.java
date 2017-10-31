package online.blickle.pi.init;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortDescriptionList;

import com.owlike.genson.Genson;

public class PiwebServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Call on destruction
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		// Call to initialize servlet
		ServletContext servletContext = event.getServletContext();
		
		// Store port configuration in servlet context
		PortDescriptionList p = readPortConfigFromWebXML(servletContext);
		servletContext.setAttribute(PortDescriptionList.KEY, p);
		Logger.getLogger (PiwebServletContextListener.class.getName()).log(Level.INFO,"Port configuration imported: "+p);
		
		// Store Hardware access in servlet context
		HardwareAccess hw = createHWAccessFromWebXML(servletContext, p);
		servletContext.setAttribute(HardwareAccess.KEY, hw);
		Logger.getLogger (PiwebServletContextListener.class.getName()).log(Level.INFO,"Hardware access implementation: "+hw.getClass().getName());
		
	}

	private PortDescriptionList readPortConfigFromWebXML(ServletContext servletContext) {
		String sConfig = servletContext.getInitParameter(PortDescriptionList.KEY);
		Genson genson = new Genson();
		PortDescriptionList p = genson.deserialize(sConfig, PortDescriptionList.class);
		return p;
	}
	
	private HardwareAccess createHWAccessFromWebXML(ServletContext servletContext,PortDescriptionList pl)  {
		String sHWAccessClassName = servletContext.getInitParameter(HardwareAccess.KEY);
		try {
			Class<?> clazz = Class.forName(sHWAccessClassName);
			Constructor<?> ctor = clazz.getConstructor(PortDescriptionList.class);
			Object object = ctor.newInstance(new Object[] { pl });
			HardwareAccess hw = (HardwareAccess)object;
			return hw;
		} catch (ReflectiveOperationException e) {
			throw new IllegalArgumentException(e);
		}
	}
}