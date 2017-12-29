package online.blickle.pi.observer;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortDescription;
import online.blickle.pi.PortDescriptionList;

public class ObserverManager {

	private int observerIdCnt=0;
	private Map<PortObserver,String> observer2IdMap = new HashMap<>();
	private Map<String,PortObserverChangeListener> Id2ObserverMap = new HashMap<>();
	private PortDescriptionList allPorts;
	private HardwareAccess hardware;
	public static final String KEY = "online.blickle.pi.observer.ObserverManager";
	
	public ObserverManager(PortDescriptionList allPorts, HardwareAccess hardware) {
		this.allPorts = allPorts;
		this.hardware = hardware;
	}
	
	public String addObserver(PortObserver observer) {
		String id = observer2IdMap.get(observer);
		if (id == null ) {
			id = "ID"+observerIdCnt++;
			observer.setObserverId(id);
			PortDescription pd = getPortDescription(observer);
			PortObserverChangeListener pol = new PortObserverChangeListener(observer);
			observer2IdMap.put(observer, id);
			Id2ObserverMap.put(id, pol);
			hardware.addPortChangeListner(pol,pd);
		}
		return id;
	}
	

	private PortDescription getPortDescription(PortObserver po) {
		String port = po.getPortId();
		PortDescription pd= allPorts.getPortDecriptionById(port);
		return pd;
	}
	
	private PortDescription getPortDescription(String observerId) {
		PortObserver po = getObserver(observerId);
		if (po != null) {
			String portid = po.getPortId();
			return allPorts.getPortDecriptionById(portid);
		}
		return null;
	}
	
	public PortObserver getObserver(String observerId) {
		PortObserverChangeListener pocl = Id2ObserverMap.get(observerId);
		if (pocl != null) {
			return pocl.getPortObserver();
		} else {
			return null;
		}
	}
	
	public void removeObserver(String observerId) {
		PortObserverChangeListener pocl = Id2ObserverMap.get(observerId);
		hardware.removePortChangeListner(pocl, getPortDescription(observerId));
		observer2IdMap.remove(pocl.getPortObserver());
		Id2ObserverMap.remove(observerId);
	}
	
	public Collection<PortObserver> getAllObservers() {
		
		return observer2IdMap.keySet();
	}
	
	
}
