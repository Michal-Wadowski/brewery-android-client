package wadosm.bluetooth.currentschedule;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.bluetooth.common.AbstractViewModel;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.connectivity.DeviceService;
import wadosm.bluetooth.connectivity.model.StateElements;

@HiltViewModel
public class CurrentScheduleViewModel extends AbstractViewModel {

    MutableLiveData<Boolean> dataFetchedMLD = new MutableLiveData<>();

    private final DeviceConnectivity deviceConnectivity;

    public MutableLiveData<Boolean> getDataFetchedMLD() {
        return dataFetchedMLD;
    }

    @Inject
    public CurrentScheduleViewModel(DeviceConnectivity deviceConnectivity) {
        this.deviceConnectivity = deviceConnectivity;
    }

    @Override
    public void onFragmentInit(Activity activity) {
        DeviceService service = deviceConnectivity.getDeviceService();
        service.fetchCurrentDeviceState();

        service.addDeviceStateListener(this::onStateReceivedCallback);
    }

    @Override
    public void onFragmentDetach(Activity activity) {
        DeviceService service = deviceConnectivity.getDeviceService();
        service.addDeviceStateListener(this::onStateReceivedCallback);
    }

    public void onStateReceivedCallback(StateElements stateItems) {
        dataFetchedMLD.postValue(true);
    }

}
