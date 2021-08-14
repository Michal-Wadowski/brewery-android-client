package wadosm.bluetooth.connectivity;


import wadosm.bluetooth.common.Consumer;
import wadosm.bluetooth.connectivity.model.StateElements;

public interface DeviceService {

    void disconnect();

    void setOnDisconnected(Runnable onDiscronnected);

    void addResponseListener(Consumer<String> onJsonReceivedCallback);

    void removeResponseListener(Consumer<String> onJsonReceivedCallback);

    void powerOff();

    void restart();

    int getFermentingState();

    int Fermenting_setDestinationTemperature(Integer value);

    int Fermenting_enable(boolean enable);
}
