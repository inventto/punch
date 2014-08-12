package invent.to.punch;

import to.invent.usb.CommandIn;
import to.invent.usb.MSP430Controller;
import to.invent.usb.MSPEvent;
import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;

import com.hoho.android.usbserial.util.PermissionReceiver;

public class MainActivity extends Activity {

	private MSP430Controller msp430;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		msp430 = new MSP430Controller(this, new PermissionReceiver.IDeviceFoundedListener() {

			@Override
			public void onDeviceFounded(UsbDevice d) {
				// TODO: Aqui configura os pinos
				// try {
				// msp430.configurePin(Pin.P1_6, PinType.OUTPUT);
				// msp430.onPin(Pin.P1_0);
				// msp430.configurePin(Pin.P1_3, PinType.BORDER_UP_DOWN);
				// msp430.onPin(Pin.P1_3);
				// msp430.configurePin(Pin.P1_0, PinType.OUTPUT);
				// msp430.onPin(Pin.P1_6);
				// } catch (IOException e) {
				// e.printStackTrace();
				// }

			}
		}, new MSP430Controller.IOnReceiveListener() {
			@Override
			public void onReceive(MSPEvent event) {
				System.out.println("Opa");
				if (CommandIn.INPUT_DIGITAL.is(event.getCommand())) {
					String msg = "Entrada Digital : Porta " + (event.getPort() - 0x30) + " Pin: " + (event.getPin() - 0x30) + " Value: " + (event.getMessage()[0] - 10);
					System.out.println(msg);
				} else  if(CommandIn.INPUT_DIGITAL.is(event.getCommand())){
					String msg = "Entrada Analógica : Porta " + (event.getPort() - 0x30) + " Pin: " + (event.getPin() - 0x30) + " Value: " + (event.getMessage()[0] - 10);
					System.out.println(msg);
				}
			}
		});
	}
}
