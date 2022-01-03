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
        connectDevice("33:03:30:09:51:50"); // real device


//        connectDevice("68:54:5A:C7:49:66"); // dell laptop

//        connectDevice("00:1A:7D:DA:71:11"); // orange-pi
    }

    private void connectDevice(String mac) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(x -> {
                    service = null;
                })
                .subscribe(this::onConnected, this::onError);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {
        service = new RealDeviceService(bluetoothManager, connectedDevice);
        service.setOnDisconnected(() -> {
            service = null;
        });
        onConnectedCallback.run();
    }

    private void onError(Throwable error) {
        service = null;
        onErrorCallback.accept(error.getMessage());
    }

}
