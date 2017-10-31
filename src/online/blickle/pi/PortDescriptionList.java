package online.blickle.pi;

import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;

import com.owlike.genson.Genson;

@XmlRootElement
public class PortDescriptionList {
	
	protected Collection<PortDescription> portDescriptions;
	public static final String KEY = "online.blickle.pi.PortConfiguration";
	
	public PortDescriptionList(){}
	
	public PortDescriptionList(Collection<PortDescription> portDescriptions) {
		this.portDescriptions = portDescriptions;
	}

	public PortDescription getPortDecriptionById(String id) {
		for (PortDescription desc:this.portDescriptions) {
			if (desc.getId().equals(id)) {
				return desc;
			}
		}
		throw new IllegalArgumentException("Port with Id '"+id+"' unknown.");
	}

	public Collection<PortDescription> getPortDescriptions() {
		return portDescriptions;
	}

	public void setPortDescriptions(Collection<PortDescription> portDescriptions) {
		this.portDescriptions = portDescriptions;
	}
	
	public String toString() {
		Genson genson = new Genson();
		return genson.serialize(this);
	}
}
