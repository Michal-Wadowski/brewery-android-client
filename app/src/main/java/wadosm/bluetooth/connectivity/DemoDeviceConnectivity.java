package wadosm.bluetooth.connectivity;

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
        connected = true;
        onConnected.run();
    }
}
