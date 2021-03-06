package wadosm.brewingclient.currentstate;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.brewingclient.common.AbstractViewModel;
import wadosm.brewingclient.connectivity.DeviceConnectivity;
import wadosm.brewingclient.connectivity.model.StateElements;
import wadosm.brewingclient.currentstate.model.StateElementsVDO;

@HiltViewModel
public class CurrentStateViewModel extends AbstractViewModel {

    static final int MAX_STATE_WIDTH = 46;

    MutableLiveData<StateElementsVDO> stateItemsMLD = new MutableLiveData<>();

    private final DeviceConnectivity deviceConnectivity;

    public MutableLiveData<StateElementsVDO> getStateItemsMLD() {
        return stateItemsMLD;
    }

    @Inject
    public CurrentStateViewModel(DeviceConnectivity deviceConnectivity) {
        this.deviceConnectivity = deviceConnectivity;
    }

    @Override
    public void onFragmentInit(Activity activity) {
//        DeviceService service = deviceConnectivity.getDeviceService();
//        service.addDeviceStateListener(this::onStateReceivedCallback);
//        updateTitle(activity, R.string.currentSchedule_title);
    }

    @Override
    public void onFragmentDetach(Activity activity) {
//        DeviceService service = deviceConnectivity.getDeviceService();
//        service.addDeviceStateListener(this::onStateReceivedCallback);
    }

    public void onStateReceivedCallback(StateElements stateItems) {
        stateItemsMLD.postValue(new StateElementsVDO(MAX_STATE_WIDTH, stateItems));
    }
}
