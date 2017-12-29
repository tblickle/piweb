package online.blickle.pi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortChangeListener;
import online.blickle.pi.PortDescription;
import online.blickle.pi.RaspberryPort;
import online.blickle.pi.RaspberryPortList;

public class HardwareEmulator implements HardwareAccess{

	//private RaspberryPortList pL;
	private Collection<PortDescription> portDescritions;
	private HashMap<String, ObservableRaspberryPort> obMap = new HashMap<>();
	
	public HardwareEmulator(PortDescriptionList pl) {
		this.portDescritions = pl.portDescriptions;
		boolean portValue = false;
		for (PortDescription port:portDescritions) {
			obMap.put(port.getId(), new ObservableRaspberryPort(port, portValue));
			portValue = !portValue;
		}
	}
	
	
	@Override
	public void setPort(PortDescription port) {
		ObservableRaspberryPort pd = getPortSafe(port);
		pd.setValue(true);
		System.out.println("HarwareEmulator.setPort(): "+ port.getId());
	
	}
	
	
	@Override
	public void clearPort(PortDescription port) {
		ObservableRaspberryPort pd = getPortSafe(port);
		pd.setValue(false);
		System.out.println("HarwareEmulator.clearPort(): "+ port.getId());
	}

	@Override
	public boolean getPortStatus(PortDescription port) {
		ObservableRaspberryPort pd = getPortSafe(port);
		return pd.getStatus();
		
	}

	
	@Override
	public void addPortChangeListner(PortChangeListener listener, PortDescription port) {
		ObservableRaspberryPort op = getPortSafe(port);
		op.addObserver(new ObserverWrapper(listener));
		
	}
	@Override
	public void removePortChangeListner(PortChangeListener listener,
			PortDescription portDesc) {
		
		
	}


	@Override
	public Collection<PortDescription> getAllPortDescriptions() {
		return portDescritions;
	}

	private ObservableRaspberryPort getPortSafe(PortDescription port) {
		ObservableRaspberryPort pd = obMap.get(port.getId());
		if (pd != null) {
			return pd;
		} else {
			throw new IllegalArgumentException("Port with Id '"+port.getId()+"' unknown.");
		}
	}


	public static class ObservableRaspberryPort extends Observable {
		private PortDescription portDescription;
		private boolean status;
		
		public ObservableRaspberryPort (PortDescription p, boolean status ) {
			this.portDescription = p;
			this.status = status;
		}


		public PortDescription getPortDescription() {
			return portDescription;
		}
		
		public boolean getStatus () {
			return status;
		}
		public void setValue(boolean newValue) {
			if (newValue != status) {
				status = newValue;
				setChanged();
				notifyObservers(newValue);
			}
		}
		
		
	}
	
	public static class ObserverWrapper implements Observer {

		private PortChangeListener pc;
		public ObserverWrapper(PortChangeListener pc) {
			this.pc = pc;
			
		}
		@Override
		public void update(Observable o, Object arg) {
			ObservableRaspberryPort op = (ObservableRaspberryPort)o;
			Boolean b = (Boolean)arg;
			pc.handlePortChangeEvent(new PortChangeEvent(op.getPortDescription(), b));
			
		}
		
	}
	
}
