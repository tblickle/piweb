package online.blickle.pi.observer;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import online.blickle.pi.PortDescriptionList;

public class ObserverManager {

	private int observerIdCnt=0;
	private Map<PortObserver,String> observer2IdMap = new HashMap<>();
	private Map<String,PortObserver> Id2ObserverMap = new HashMap<>();
	private PortDescriptionList allPorts;
	public static final String KEY = "online.blickle.pi.observer.ObserverManager";
	
	public ObserverManager(PortDescriptionList allPorts) {
		this.allPorts = allPorts;
	}
	
	public String addObserver(PortObserver observer) {
		String port = observer.getPortId();
			allPorts.getPortDecriptionById(port);
		String id = observer2IdMap.get(observer);
		if (id == null ) {
			id = "ID"+observerIdCnt++;
			observer.setObserverId(id);
			observer2IdMap.put(observer, id);
			Id2ObserverMap.put(id, observer);
		}
		return id;
		
	}
	
	public PortObserver getObserver(String id) {
		return Id2ObserverMap.get(id);
	}
	
	public void removeObserver(String id) {
		PortObserver observer = Id2ObserverMap.get(id);
		observer2IdMap.remove(observer);
		Id2ObserverMap.remove(id);
	}
	
	public Collection<PortObserver> getAllObservers() {
		return observer2IdMap.keySet();
	}
	
}
