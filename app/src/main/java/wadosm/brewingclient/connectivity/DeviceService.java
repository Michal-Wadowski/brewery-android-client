package wadosm.brewingclient.connectivity;


import wadosm.brewingclient.common.Consumer;

public interface DeviceService {

    void disconnect();

    void setOnDisconnected(Runnable onDiscronnected);

    void addResponseListener(Consumer<String> onJsonReceivedCallback);

    void removeResponseListener(Consumer<String> onJsonReceivedCallback);

    void addErrorListener(Consumer<Throwable> onErrorCallback);

    void removeErrorListener(Consumer<Throwable> onErrorCallback);

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

    int Brewing_setCalibrationValue(Integer side, Float value);
}
