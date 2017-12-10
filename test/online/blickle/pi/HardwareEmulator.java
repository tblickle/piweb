package online.blickle.pi;

import java.util.Collection;

import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortChangeListener;
import online.blickle.pi.PortDescription;
import online.blickle.pi.RaspberryPort;
import online.blickle.pi.RaspberryPortList;

public class HardwareEmulator implements HardwareAccess{

	private RaspberryPortList pL;
	private Collection<PortDescription> portDescritions;
	
	public HardwareEmulator(PortDescriptionList pl) {
		this.portDescritions = pl.portDescriptions;
		pL = new RaspberryPortList();
		boolean portValue = false;
		for (PortDescription port:portDescritions) {
			pL.addPort(new RaspberryPort(port, portValue));
			portValue = !portValue;
		}
	}
	
	
	@Override
	public void setPort(PortDescription port) {
		RaspberryPort pd = getPortSafe(port);
		pd.setValue(true);
		System.out.println("HarwareEmulator.setPort(): "+ port.getId());
	
	}
	
	
	@Override
	public void clearPort(PortDescription port) {
		RaspberryPort pd = getPortSafe(port);
		pd.setValue(false);
		System.out.println("HarwareEmulator.clearPort(): "+ port.getId());
	}

	@Override
	public boolean getPortStatus(PortDescription port) {
		return pL.getPort(port.getId()).isValue();
	}

	
	@Override
	public void addPortChangeListner(PortChangeListener listener, PortDescription port) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removePortChangeListner(PortChangeListener listener,
			PortDescription portDesc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Collection<PortDescription> getAllPortDescriptions() {
		return portDescritions;
	}

	private RaspberryPort getPortSafe(PortDescription port) {
		RaspberryPort pd = pL.getPort(port.getId());
		if (pd != null) {
			return pd;
		} else {
			throw new IllegalArgumentException("Port with Id '"+port.getId()+"' unknown.");
		}
	}


	
}
