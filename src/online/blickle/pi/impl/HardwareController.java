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
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class HardwareController implements HardwareAccess{

	private GpioController gpio;
	private Map<PortDescription,GpioPinDigital> port2PinMap = new HashMap<PortDescription,GpioPinDigital>();
	private Map<GpioPinDigital,PortDescription> pin2PortMap = new HashMap<GpioPinDigital,PortDescription>();
	
	public HardwareController(PortDescriptionList pl) {
		Collection<PortDescription> portConfigurations = pl.getPortDescriptions(); 
		Map<Integer, Pin> pinMap = getPinMap();
		this.gpio  = GpioFactory.getInstance();
		for (PortDescription pd:portConfigurations) {
			int portNr = pd.getRaspPortNr();
			boolean isOutput = pd.getIsOutput();
			Pin p=pinMap.get(new Integer(portNr));
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
	
	/*
	 * Unfortunalty com.pi4j.io.gpio.PinProvider.getPinByAddress() doesn't seem to work.
	 */
	private Map<Integer, Pin> getPinMap() {
		Map<Integer, Pin> portNumberToPinMap = new HashMap<Integer,Pin>();
		portNumberToPinMap.put(new Integer(0),RaspiPin.GPIO_00);
		portNumberToPinMap.put(new Integer(1),RaspiPin.GPIO_01);
		portNumberToPinMap.put(new Integer(2),RaspiPin.GPIO_02);
		portNumberToPinMap.put(new Integer(3),RaspiPin.GPIO_03);
		portNumberToPinMap.put(new Integer(4),RaspiPin.GPIO_04);
		portNumberToPinMap.put(new Integer(5),RaspiPin.GPIO_05);
		portNumberToPinMap.put(new Integer(6),RaspiPin.GPIO_06);
		portNumberToPinMap.put(new Integer(7),RaspiPin.GPIO_07);
		portNumberToPinMap.put(new Integer(8),RaspiPin.GPIO_08);
		portNumberToPinMap.put(new Integer(9),RaspiPin.GPIO_09);
		portNumberToPinMap.put(new Integer(10),RaspiPin.GPIO_10);
		portNumberToPinMap.put(new Integer(11),RaspiPin.GPIO_11);
		portNumberToPinMap.put(new Integer(12),RaspiPin.GPIO_12);
		portNumberToPinMap.put(new Integer(13),RaspiPin.GPIO_13);
		portNumberToPinMap.put(new Integer(14),RaspiPin.GPIO_14);
		portNumberToPinMap.put(new Integer(15),RaspiPin.GPIO_15);
		portNumberToPinMap.put(new Integer(16),RaspiPin.GPIO_16);
		portNumberToPinMap.put(new Integer(17),RaspiPin.GPIO_17);
		portNumberToPinMap.put(new Integer(18),RaspiPin.GPIO_18);
		portNumberToPinMap.put(new Integer(19),RaspiPin.GPIO_19);
		portNumberToPinMap.put(new Integer(20),RaspiPin.GPIO_20);
		portNumberToPinMap.put(new Integer(21),RaspiPin.GPIO_21);
		portNumberToPinMap.put(new Integer(22),RaspiPin.GPIO_22);
		portNumberToPinMap.put(new Integer(23),RaspiPin.GPIO_23);
		portNumberToPinMap.put(new Integer(24),RaspiPin.GPIO_24);
		portNumberToPinMap.put(new Integer(25),RaspiPin.GPIO_25);
		portNumberToPinMap.put(new Integer(26),RaspiPin.GPIO_26);
		portNumberToPinMap.put(new Integer(27),RaspiPin.GPIO_27);
		portNumberToPinMap.put(new Integer(28),RaspiPin.GPIO_28);
		portNumberToPinMap.put(new Integer(29),RaspiPin.GPIO_29);
		
		
		return portNumberToPinMap;
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
