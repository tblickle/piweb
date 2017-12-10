package online.blickle.pi;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class RaspberryPort {
	

	protected String id;
	protected boolean value;
	protected boolean isOutput;
	
	public RaspberryPort() {}
	
	public RaspberryPort(String id,  boolean isOutput, boolean value) {
		this.id = id;
		this.value = value;
		this.isOutput = isOutput;
	}
	public RaspberryPort(PortDescription desc, boolean value) {
		this(desc.getId(),desc.getIsOutput(),value);
	}
	public String getId() {
		return id;
	}
		
	public void setId(String id) {
		this.id = id;
	}

	public void setIsOutput(boolean isOutput) {
		this.isOutput = isOutput;
	}

	public boolean isValue() {
		return value;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public boolean getIsOutput() {
		return this.isOutput;
	}

	@Override
	public String toString() {
		return "RaspberryPort [id=" + id + ", value=" + value + ", isOutput="
				+ isOutput + "]";
	}
	

}
