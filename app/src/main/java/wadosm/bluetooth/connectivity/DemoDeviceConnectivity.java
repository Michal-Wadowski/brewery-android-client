package wadosm.bluetooth.connectivity;

import android.os.Handler;
import android.os.Looper;

import java.util.function.Consumer;

public class DemoDeviceConnectivity implements DeviceConnectivity {

    boolean connected = false;

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public DeviceService getDeviceService() {
        return null;
    }

    @Override
    public void connect(Runnable onConnected, Consumer<String> onError) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            connected = true;
            onConnected.run();
        }, 1000);
    }
}
