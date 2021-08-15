package wadosm.brewingclient.connectivity.real;

import com.google.gson.Gson;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.HashSet;
import java.util.Set;

import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.connectivity.DeviceService;
import wadosm.brewingclient.connectivity.real.dto.CommandDTO;

public class RealDeviceService implements DeviceService {

    private final Set<Consumer<String>> fetchCurrentDeviceStateCallbackQueue = new HashSet<>();

    private BluetoothManager bluetoothManager;

    private Gson gson = new Gson();

    private SimpleBluetoothDeviceInterface deviceInterface;

    private int commandId = 0;

    public RealDeviceService(BluetoothManager bluetoothManager, BluetoothSerialDevice connectedDevice) {
        deviceInterface = connectedDevice.toSimpleDeviceInterface();

        deviceInterface.setMessageReceivedListener(response -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                listener.accept(response);
            }
        });

        this.bluetoothManager = bluetoothManager;
    }

    @Override
    public void disconnect() {
        bluetoothManager.closeDevice(deviceInterface);
    }

    @Override
    public void setOnDisconnected(Runnable onDiscronnected) {
        // demo has no implementation
    }

    @Override
    public void addResponseListener(Consumer<String> onJsonReceivedCallback) {
        fetchCurrentDeviceStateCallbackQueue.add(onJsonReceivedCallback);
    }

    @Override
    public void removeResponseListener(Consumer<String> onJsonReceivedCallback) {
        fetchCurrentDeviceStateCallbackQueue.remove(onJsonReceivedCallback);
    }

    @Override
    public void powerOff() {
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Power_powerOff()));
    }

    @Override
    public void restart() {
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Power_restart()));
    }

    @Override
    public int getFermentingState() {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Fermenting_getFermentingState(commandId)));
        return commandId;
    }

    @Override
    public int Fermenting_setDestinationTemperature(Float value) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Fermenting_setDestinationTemperature(commandId, value)));
        return commandId;
    }

    @Override
    public int Fermenting_enable(boolean enable) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Fermenting_enable(commandId, enable)));
        return commandId;
    }

    @Override
    public int Brewing_setDestinationTemperature(Float value) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_setDestinationTemperature(commandId, value)));
        return commandId;
    }

    @Override
    public int Brewing_enable(boolean enable) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_enable(commandId, enable)));
        return commandId;
    }

    @Override
    public int getBrewingState() {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_getBrewingState(commandId)));
        return commandId;
    }

    @Override
    public int Brewing_motorEnable(boolean enable) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_motorEnable(commandId, enable)));
        return commandId;
    }

    @Override
    public int Brewing_enableTemperatureAlarm(boolean enable) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_enableTemperatureAlarm(commandId, enable)));
        return commandId;
    }

    @Override
    public int Brewing_setMaxPower(Integer value) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_setMaxPower(commandId, value)));
        return commandId;
    }

    @Override
    public int Brewing_setPowerTemperatureCorrelation(Float value) {
        commandId++;
        deviceInterface.sendMessage(gson.toJson(CommandDTO.Brewing_setPowerTemperatureCorrelation(commandId, value)));
        return commandId;
    }

}
