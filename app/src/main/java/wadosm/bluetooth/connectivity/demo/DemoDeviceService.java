package wadosm.bluetooth.connectivity.demo;

import android.os.Handler;

import com.google.gson.Gson;

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
import wadosm.bluetooth.currentschedule.dto.FermentingState;
import wadosm.bluetooth.currentschedule.dto.FermentingStatusResponse;

public class DemoDeviceService implements DeviceService {

    private final Set<Consumer<String>> fetchCurrentDeviceStateCallbackQueue = new HashSet<>();

    private int commandId = 0;

    private Handler scheduler = new Handler();

    private Gson gson = new Gson();

    private Random random = new Random();

    private FermentingState fermentingState = new FermentingState(false, 25.0f, null, false);

    @Override
    public void disconnect() {
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

    }

    @Override
    public void restart() {

    }

    @Override
    public int getFermentingState() {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                fermentingState.setCurrentTemperature(random.nextFloat() - 0.5f + fermentingState.getCurrentTemperature());
                listener.accept(gson.toJson(new FermentingStatusResponse(pendingCommandId, null, fermentingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int Fermenting_setDestinationTemperature(Integer value) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                Float floatValue = null;
                if (value != null) {
                    floatValue = (float)value;
                }
                fermentingState.setDestinationTemperature(floatValue);
                listener.accept(gson.toJson(new FermentingStatusResponse(pendingCommandId, null, fermentingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int Fermenting_enable(boolean enable) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                fermentingState.setEnabled(enable);
                listener.accept(gson.toJson(new FermentingStatusResponse(pendingCommandId, null, fermentingState)));
            }
        }, 100);

        return pendingCommandId;
    }

}
