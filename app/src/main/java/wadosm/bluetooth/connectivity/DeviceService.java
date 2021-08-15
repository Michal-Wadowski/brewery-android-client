package wadosm.bluetooth.connectivity;


import wadosm.bluetooth.common.Consumer;

public interface DeviceService {

    void disconnect();

    void setOnDisconnected(Runnable onDiscronnected);

    void addResponseListener(Consumer<String> onJsonReceivedCallback);

    void removeResponseListener(Consumer<String> onJsonReceivedCallback);

    void powerOff();

    void restart();

    int getFermentingState();

    int Fermenting_setDestinationTemperature(Float value);

    int Fermenting_enable(boolean enable);

    int Brewing_setDestinationTemperature(Float value);

    int Brewing_enable(boolean enable);

    int getBrewingState();

    int Brewing_motorEnable(boolean enable);

    int Brewing_enableTemperatureAlarm(boolean enable);

    int Brewing_setMaxPower(Integer value);

    int Brewing_setPowerTemperatureCorrelation(Float value);
}
