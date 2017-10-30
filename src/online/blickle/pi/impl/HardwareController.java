package online.blickle.pi.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import online.blickle.pi.HardwareAccess;
import online.blickle.pi.PortChangeEvent;
import online.blickle.pi.PortChangeListener;
import online.blickle.pi.PortDescription;
import online.blickle.pi.PortDescriptionList;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigital;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinProvider;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class HardwareController implements HardwareAccess{

	private GpioController gpio;
	private Map<PortDescription,GpioPinDigital> port2PinMap = new HashMap<PortDescription,GpioPinDigital>();
	private Map<GpioPinDigital,PortDescription> pin2PortMap = new HashMap<GpioPinDigital,PortDescription>();
	
	public HardwareController(PortDescriptionList pl) {
		Collection<PortDescription> portConfigurations = pl.getPortDescriptions(); 
		this.gpio  = GpioFactory.getInstance();
		for (PortDescription pd:portConfigurations) {
			int portNr = pd.getRaspPortNr();
			boolean isOutput = pd.getIsOutput();
			Pin p=PinProvider.getPinByAddress(portNr);
			GpioPinDigital pin=null;
			if (isOutput) {
				pin= gpio.provisionDigitalOutputPin(p);
			} else {
				pin = gpio.provisionDigitalInputPin(p,PinPullResistance.PULL_UP);
			}
			addMapping(pd, pin);
		}
	}
	
	private void addMapping(PortDescription desc, GpioPinDigital pin) {
		port2PinMap.put(desc,pin);
		pin2PortMap.put(pin, desc);
		
	}
	
	public void setPort(PortDescription portDesc) {
		GpioPinDigitalOutput outPin = getOutputPin(portDesc);
		outPin.setState(true);

	}
	public void clearPort(PortDescription portDesc) {
		GpioPinDigitalOutput outPin = getOutputPin(portDesc);
		outPin.setState(false);
	}
	
	public boolean getPortStatus(PortDescription portDesc) {
		GpioPinDigital pin = getPin(portDesc);
		return pin.getState().isHigh();
	}
	
	public Collection<PortDescription> getAllPortDescriptions() {
		return port2PinMap.keySet();
	}
	
	
	private GpioPinDigitalOutput getOutputPin(PortDescription portDesc) {
		GpioPinDigital pin = getPin(portDesc);
		if (pin instanceof GpioPinDigitalOutput) {
			return (GpioPinDigitalOutput)pin;
		} else {
			throw new IllegalArgumentException("Port "+portDesc.getId()+" is not an output pin.");
		}
	}

	private GpioPinDigital getPin(PortDescription desc) {
		GpioPinDigital pin = port2PinMap.get(desc); 
				
		if (pin == null) {
			throw new IllegalArgumentException("Port "+desc.getId()+" is not a digital pin.");
		}
		return pin;
	}
	
	/**
	 * Listen to a specific outPort
	 * @param listener
	 * @param outPort
	 */
	@Override
	public void addPortChangeListner (PortChangeListener listener, PortDescription portDesc) {
		PortListenerWrapper lw = new PortListenerWrapper(listener);
		GpioPinDigital pin = getPin(portDesc);
		pin.addListener(lw);
	}
	
	
	protected class PortListenerWrapper implements  GpioPinListenerDigital{
		
		private PortChangeListener listener;
		public PortListenerWrapper(PortChangeListener listener) {
			this.listener = listener;
		}
		
		@Override
		public void handleGpioPinDigitalStateChangeEvent(
				GpioPinDigitalStateChangeEvent event) {
			
			
			GpioPinDigital pin =(GpioPinDigital)event.getPin();
			PortDescription port = pin2PortMap.get(pin);
			
			PortChangeEvent pe = new PortChangeEvent(port, event.getState().isHigh());
			listener.handlePortChangeEvent(pe);
		}
	}

	
	
}
