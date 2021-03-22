package wadosm.bluetooth.connectivity;


import wadosm.bluetooth.common.Consumer;
import wadosm.bluetooth.connectivity.model.StateElements;

public interface DeviceService {

    void disconnect();

    void setOnDisconnected(Runnable onDiscronnected);

    void fetchCurrentDeviceState();

    void addDeviceStateListener(Consumer<StateElements> onStateReceivedCallback);

    void removeDeviceStateListener(Consumer<StateElements> onStateReceivedCallback);
}
