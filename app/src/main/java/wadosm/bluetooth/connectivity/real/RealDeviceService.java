package wadosm.bluetooth.connectivity.real;

import com.google.gson.Gson;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import wadosm.bluetooth.common.Consumer;
import wadosm.bluetooth.connectivity.DeviceService;
import wadosm.bluetooth.connectivity.model.StateElement;
import wadosm.bluetooth.connectivity.model.StateElements;
import wadosm.bluetooth.connectivity.real.dto.CommandDTO;

public class RealDeviceService implements DeviceService {

    private final Set<Consumer<StateElements>> fetchCurrentDeviceStateCallbackQueue = new HashSet<>();

    private final Random random = new Random();

    private int time;

    BluetoothManager bluetoothManager;

    Gson gson = new Gson();

    private SimpleBluetoothDeviceInterface deviceInterface;

    public RealDeviceService(BluetoothManager bluetoothManager, BluetoothSerialDevice connectedDevice) {
        deviceInterface = connectedDevice.toSimpleDeviceInterface();
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
    public void fetchCurrentDeviceState() {
        time = 12 * 60 + 59;

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      callCalbacksOnFetchCurrentDeviceState();
                                  }
                              },
                1000,
                1000);
    }

    @Override
    public void addDeviceStateListener(Consumer<StateElements> onStateReceivedCallback) {
        fetchCurrentDeviceStateCallbackQueue.add(onStateReceivedCallback);
    }

    @Override
    public void removeDeviceStateListener(Consumer<StateElements> onStateReceivedCallback) {
        fetchCurrentDeviceStateCallbackQueue.remove(onStateReceivedCallback);
    }

    @Override
    public void powerEnable(boolean enable) {
        deviceInterface.sendMessage(gson.toJson(CommandDTO.powerEnable(enable)));
    }

    @Override
    public void motorEnable(int motorNumber, boolean enable) {
        deviceInterface.sendMessage(gson.toJson(CommandDTO.motorEnable(motorNumber, enable)));
    }

    @Override
    public void playSound(int progress) {
        int period = (int)(progress * (500/99.999));
        deviceInterface.sendMessage(gson.toJson(CommandDTO.playSound(period)));
    }

    @Override
    public void setMainsPower(int mainsNumber, int progress) {
        int power = (int)(progress * (0xff/99.999));
        deviceInterface.sendMessage(gson.toJson(CommandDTO.setMainsPower(mainsNumber, power)));
    }

    private void callCalbacksOnFetchCurrentDeviceState() {
        double temperature = Math.round((70 + random.nextDouble() - 0.5) * 10) / 10.0;

        int mins = time / 60;
        int secs = time % 60;
        time--;

        StateElements stateItems = new StateElements(
                Arrays.asList(
                        new StateElement("Temp 1", StateElement.Type.TEMPERATURE_SENSOR, temperature + " °C"),
                        new StateElement("Grzałka 1", StateElement.Type.HEATING_ELEMENT, "50%"),
                        new StateElement("Grzałka 2", StateElement.Type.HEATING_ELEMENT, "50%"),
                        new StateElement("Dodać chmiel", StateElement.Type.ALARM, String.format(Locale.getDefault(), "00:%02d:%02d", mins, secs)),
                        new StateElement("Mieszadło", StateElement.Type.OUTPUT, "on")
                )
        );

        for (Consumer<StateElements> onStateReceivedCallback : fetchCurrentDeviceStateCallbackQueue) {
            onStateReceivedCallback.accept(stateItems);
        }
    }
}
