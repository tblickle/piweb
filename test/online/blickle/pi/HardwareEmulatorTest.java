package online.blickle.pi;

public class HardwareEmulatorTest {

	public static HardwareAccess getHardwareEmulator() {
		return new HardwareEmulator(PortDescriptionTest.getTestPortDescriptionList());
	}
}
