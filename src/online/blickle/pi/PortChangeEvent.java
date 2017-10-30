package online.blickle.pi;

public class PortChangeEvent {

	private PortDescription port; 
	private boolean posTrigger;
	
	public PortChangeEvent(PortDescription port, boolean posTrigger) {
		this.port = port;
		this.posTrigger = posTrigger;
	}

	public PortDescription getPort() {
		return port;
	}

	public boolean isPosTrigger() {
		return posTrigger;
	}
	
}
