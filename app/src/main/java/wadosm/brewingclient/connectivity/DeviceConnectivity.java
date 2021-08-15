package wadosm.brewingclient.connectivity;

import wadosm.brewingclient.common.Consumer;

public interface DeviceConnectivity {

    boolean isConnected();

    DeviceService getDeviceService();

    void connect(Runnable onConnected, Consumer<String> onError);

}
