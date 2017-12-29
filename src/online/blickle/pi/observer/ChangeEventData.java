package online.blickle.pi.observer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChangeEventData {

	private String portId;
	private boolean newState;
	
	public ChangeEventData() {}
	
	public ChangeEventData(String portId, boolean newState) {
		super();
		this.portId = portId;
		this.newState = newState;
	}
	public String getPortId() {
		return portId;
	}
	public void setPortId(String portId) {
		this.portId = portId;
	}
	public boolean isNewState() {
		return newState;
	}
	public void setNewState(boolean newState) {
		this.newState = newState;
	}
	
}
