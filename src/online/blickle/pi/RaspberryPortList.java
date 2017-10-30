package online.blickle.pi;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RaspberryPortList {

	
	@XmlElementWrapper(name = "portList")
	@XmlElement(name = "port")
	protected ArrayList<RaspberryPort> portList = new ArrayList<RaspberryPort>();
	
	public RaspberryPortList() {
		
	}
	
	public void addPort(RaspberryPort port) {
		portList.add(port);
	}
	
	public RaspberryPort getPort(String id) {
		for (RaspberryPort rp:portList) {
			if (rp.getId().equals(id)) {
				return rp;
			}
		}
		return null;
	}

}
