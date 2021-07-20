package wadosm.bluetooth.connectivity.demo;

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

public class DemoDeviceService implements DeviceService {

    private final Set<Consumer<StateElements>> fetchCurrentDeviceStateCallbackQueue = new HashSet<>();

    private final Random random = new Random();

    private int time;

    @Override
    public void disconnect() {
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
