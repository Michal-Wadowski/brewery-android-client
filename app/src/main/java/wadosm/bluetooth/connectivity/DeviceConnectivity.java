package wadosm.bluetooth.connectivity;

import wadosm.bluetooth.common.Consumer;

public interface DeviceConnectivity {

    boolean isConnected();

    DeviceService getDeviceService();

    void connect(Runnable onConnected, Consumer<String> onError);

}
