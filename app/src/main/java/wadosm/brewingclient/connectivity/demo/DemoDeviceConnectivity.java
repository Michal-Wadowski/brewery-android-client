package wadosm.brewingclient.connectivity.demo;

import android.os.Handler;
import android.os.Looper;

import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.connectivity.DeviceConnectivity;
import wadosm.brewingclient.connectivity.DeviceService;

public class DemoDeviceConnectivity implements DeviceConnectivity {

    private DeviceService service;

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
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            service = new DemoDeviceService();
            onConnected.run();
        }, 1000);
    }
}
