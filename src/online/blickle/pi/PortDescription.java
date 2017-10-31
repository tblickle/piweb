package online.blickle.pi;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PortDescription {

	private int raspPortNr;
	private String id;
	private String description;
	private boolean isOutput;
	
	public PortDescription(){}
	
	public PortDescription(int raspPortNr, String id, boolean isOutput, String description) {
		this.raspPortNr = raspPortNr;
		this.id = id;
		this.description = description;
		this.isOutput = isOutput;
	}
	
	public int getRaspPortNr() {
		return raspPortNr;
	}

	public void setRaspPortNr(int raspPortNr) {
		this.raspPortNr = raspPortNr;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIsOutput(boolean isOutput) {
		this.isOutput = isOutput;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean getIsOutput() {
		return isOutput;
	}
	
	public String getId() {
		return id;
	}
	
}
