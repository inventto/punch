package invent.to.punch;

import to.invent.usb.CommandIn;
import to.invent.usb.MSP430Controller;
import to.invent.usb.MSPEvent;
import android.app.Activity;
import android.hardware.usb.UsbDevice;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.hoho.android.usbserial.util.PermissionReceiver;

public class MainActivity extends Activity {

	private MSP430Controller msp430;
	private TextView counterText;
	protected int punchCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		counterText = (TextView)findViewById(R.id.counterText);
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
				int convertedEvent = event.getMessage()[0] - 10;
				if (CommandIn.INPUT_DIGITAL.is(event.getCommand())) {
					String msg = "Entrada Digital : Porta " + (event.getPort() - 0x30) + " Pin: " + (event.getPin() - 0x30) + " Value: " + convertedEvent;
					System.out.println(msg);
					if (1 == convertedEvent)
						punchCount++;
				} else if (CommandIn.INPUT_ANALOG.is(event.getCommand())) {
					String msg = "Entrada Analógica : Porta " + (event.getPort() - 0x30) + " Pin: " + (event.getPin() - 0x30) + " Value: " + convertedEvent;
					System.out.println(msg);
				}
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						counterText.setText(String.valueOf(punchCount));
					}
				});
			}
		});
	}
}
