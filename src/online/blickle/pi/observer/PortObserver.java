package online.blickle.pi.observer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PortObserver {

	private String observerId;
	private String portId;
	private String observerURL;
	
	public PortObserver() {};
	
	public PortObserver(String portId, String observerURL) {
		super();
		this.portId = portId;
		this.observerURL = observerURL;
	}

	public PortObserver(String observerId, String portId, String observerURL) {
		super();
		this.observerId = observerId;
		this.portId = portId;
		this.observerURL = observerURL;
	}
	
	public String getObserverId() {
		return observerId;
	}

	public void setObserverId(String observerId) {
		this.observerId = observerId;
	}

	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public String getObserverURL() {
		return observerURL;
	}
	public void setObserverURL(String observerURL) {
		this.observerURL = observerURL;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((observerURL == null) ? 0 : observerURL.hashCode());
		result = prime * result + ((portId == null) ? 0 : portId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortObserver other = (PortObserver) obj;
		if (observerURL == null) {
			if (other.observerURL != null)
				return false;
		} else if (!observerURL.equals(other.observerURL))
			return false;
		if (portId == null) {
			if (other.portId != null)
				return false;
		} else if (!portId.equals(other.portId))
			return false;
		return true;
	}
	
	
	
}
