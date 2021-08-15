package wadosm.brewingclient.connectivity.real;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.connectivity.DeviceConnectivity;
import wadosm.brewingclient.connectivity.DeviceService;

public class RealDeviceConnectivity implements DeviceConnectivity {

    private DeviceService service;

    private SimpleBluetoothDeviceInterface deviceInterface;

    private BluetoothManager bluetoothManager;

    private Runnable onConnectedCallback;

    private Consumer<String> onErrorCallback;

    public RealDeviceConnectivity() {
        bluetoothManager = BluetoothManager.getInstance();
    }

    @Override
    public boolean isConnected() {
        return service != null;
    }

    @Override
    public DeviceService getDeviceService() {
        if (isConnected()) {
            return service;
        } else {
            return null;
        }
    }

    @Override
    public void connect(Runnable onConnected, Consumer<String> onError) {
        this.onConnectedCallback = onConnected;
        this.onErrorCallback = onError;
        connectDevice("33:03:30:09:51:50");
//        new Handler(Looper.getMainLooper()).postDelayed(() -> {
//            service = new DemoDeviceService();
//            onConnected.run();
//        }, 1000);
    }




//        bluetoothManager = BluetoothManager.getInstance();
//        if (bluetoothManager == null) {
//            // Bluetooth unavailable on this device :( tell the user
//            Toast.makeText(this, "Bluetooth not available.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
//            finish();
//        }
//
//        Collection<BluetoothDevice> pairedDevices = bluetoothManager.getPairedDevicesList();
//        for (BluetoothDevice device : pairedDevices) {
//            Log.d("My Bluetooth App", "Device name: " + device.getName());
//            Log.d("My Bluetooth App", "Device MAC Address: " + device.getAddress());
//        }
//
//        connectDevice("33:03:30:09:51:50");

//    public void sendMessage() {
//        deviceInterface.sendMessage("Hello world!");
//    }


    private void connectDevice(String mac) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {
        service = new RealDeviceService(bluetoothManager, connectedDevice);
        onConnectedCallback.run();
//        // You are now connected to this device!
//        // Here you may want to retain an instance to your device:
//        deviceInterface = connectedDevice.toSimpleDeviceInterface();
//
//        // Listen to bluetooth events
//        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
//
//        // Let's send a message:
////        deviceInterface.sendMessage("Hello world!");
    }

//    private void onMessageSent(String message) {
//        // We sent a message! Handle it here.
//        Toast.makeText(this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
//    }
//
//    private void onMessageReceived(String message) {
//        // We received a message! Handle it here.
//        Toast.makeText(this, "Received a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
//    }

    private void onError(Throwable error) {
        onErrorCallback.accept(error.getMessage());
        // Handle the error
    }

}
