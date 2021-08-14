package wadosm.bluetooth.connectivity.real;

import android.util.Log;

import com.google.gson.Gson;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.HashSet;
import java.util.Set;

import wadosm.bluetooth.common.Consumer;
import wadosm.bluetooth.connectivity.DeviceService;
import wadosm.bluetooth.connectivity.real.dto.CommandDTO;

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
    public int Fermenting_setDestinationTemperature(Integer value) {
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

}
