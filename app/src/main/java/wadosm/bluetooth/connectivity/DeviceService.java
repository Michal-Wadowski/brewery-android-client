package wadosm.bluetooth.connectivity;

public interface DeviceService {

    void disconnect(Runnable onSuccess, Runnable onFail);

    void setOnDisconnected(Runnable onDiscronnected);
}
