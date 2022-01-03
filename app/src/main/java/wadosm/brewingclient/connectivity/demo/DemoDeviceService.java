package wadosm.brewingclient.connectivity.demo;

import android.os.Handler;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.connectivity.DeviceService;
import wadosm.brewingclient.currentschedule.dto.BrewingState;
import wadosm.brewingclient.currentschedule.dto.BrewingStatusResponse;
import wadosm.brewingclient.currentschedule.dto.FermentingState;
import wadosm.brewingclient.currentschedule.dto.FermentingStatusResponse;

public class DemoDeviceService implements DeviceService {

    private final Set<Consumer<String>> fetchCurrentDeviceStateCallbackQueue = new HashSet<>();

    private int commandId = 0;

    private Handler scheduler = new Handler();

    private Gson gson = new Gson();

    private Random random = new Random();

    private FermentingState fermentingState = new FermentingState(false, 25.0f, null, false);

    private BrewingState brewingState = new BrewingState(false, 60.0f, null, null, null, null, false, false, 70);

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
    public void addErrorListener(Consumer<Throwable> onErrorCallback) {

    }

    @Override
    public void removeErrorListener(Consumer<Throwable> onErrorCallback) {

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
    public int Fermenting_setDestinationTemperature(Float value) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                fermentingState.setDestinationTemperature(value);
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

    @Override
    public int Brewing_setDestinationTemperature(Float value) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setDestinationTemperature(value);
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int Brewing_enable(boolean enable) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setEnabled(enable);
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int getBrewingState() {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setCurrentTemperature(random.nextFloat() - 0.5f + brewingState.getCurrentTemperature());
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int Brewing_motorEnable(boolean enable) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setMotorEnabled(enable);
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int Brewing_enableTemperatureAlarm(boolean enable) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setTemperatureAlarm(enable);
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;

    }

    @Override
    public int Brewing_setMaxPower(Integer value) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setMaxPower(value);
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;
    }

    @Override
    public int Brewing_setPowerTemperatureCorrelation(Float value) {
        int pendingCommandId = ++commandId;

        scheduler.postDelayed(() -> {
            for (Consumer<String> listener: fetchCurrentDeviceStateCallbackQueue) {
                brewingState.setPowerTemperatureCorrelation(value);
                listener.accept(gson.toJson(new BrewingStatusResponse(pendingCommandId, null, brewingState)));
            }
        }, 100);

        return pendingCommandId;
    }

}
