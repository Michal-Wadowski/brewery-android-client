package wadosm.bluetooth.connectivity;

import java.util.function.Consumer;

public interface DeviceConnectivity {

    boolean isConnected();

    DeviceService getDeviceService();

    void connect(Runnable onConnected, Consumer<String> onError);

}
