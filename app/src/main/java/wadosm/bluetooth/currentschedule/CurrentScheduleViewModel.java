package wadosm.bluetooth.currentschedule;

import android.app.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.bluetooth.common.AbstractViewModel;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.connectivity.DeviceService;
import wadosm.bluetooth.connectivity.model.StateElements;
import wadosm.bluetooth.dependency.FragmentFactory;

@HiltViewModel
public class CurrentScheduleViewModel extends AbstractViewModel {

    MutableLiveData<Boolean> dataFetchedMLD = new MutableLiveData<>();

    private final DeviceConnectivity deviceConnectivity;

    private final FragmentFactory fragmentFactory;

    public DeviceConnectivity getDeviceConnectivity() {
        return deviceConnectivity;
    }

    public FragmentFactory getFragmentFactory() {
        return fragmentFactory;
    }

    public MutableLiveData<Boolean> getDataFetchedMLD() {
        return dataFetchedMLD;
    }

    @Inject
    public CurrentScheduleViewModel(DeviceConnectivity deviceConnectivity, FragmentFactory fragmentFactory) {
        this.deviceConnectivity = deviceConnectivity;
        this.fragmentFactory = fragmentFactory;
    }


    @Override
    public void onFragmentInit(Activity activity) {
//        DeviceService service = deviceConnectivity.getDeviceService();
//        service.fetchCurrentDeviceState();
//
//        service.addDeviceStateListener(this::onStateReceivedCallback);
    }

    @Override
    public void onFragmentDetach(Activity activity) {
//        DeviceService service = deviceConnectivity.getDeviceService();
//        service.addDeviceStateListener(this::onStateReceivedCallback);
    }

//    public void onStateReceivedCallback(StateElements stateItems) {
//        dataFetchedMLD.postValue(true);
//    }

}
