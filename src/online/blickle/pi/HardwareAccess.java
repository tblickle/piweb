package online.blickle.pi;

import java.util.Collection;


public interface HardwareAccess {

	public static final String KEY = "online.blickle.pi.HardwareAccess";
	
	public  void setPort(PortDescription descNr);
	public  void clearPort(PortDescription descNr);
	public  boolean getPortStatus(PortDescription descNr);
	public  void addPortChangeListner (PortChangeListener listener, PortDescription portDesc);
	public Collection<PortDescription> getAllPortDescriptions();
	
}
