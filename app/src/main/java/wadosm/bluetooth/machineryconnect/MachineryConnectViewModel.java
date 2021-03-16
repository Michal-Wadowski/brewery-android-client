package wadosm.bluetooth.machineryconnect;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.function.Consumer;

import wadosm.bluetooth.FragmentFactory;
import wadosm.bluetooth.R;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.connectivity.DeviceConnectivityFactory;
import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.NewFragmentVDO;
import wadosm.bluetooth.main.PublicMainViewModel;

public class MachineryConnectViewModel extends ViewModel {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    private final MutableLiveData<MachineryConnectVDO> machineryConnectMLD = new MutableLiveData<>();

    public MutableLiveData<MachineryConnectVDO> getMachineryConnectMLD() {
        return machineryConnectMLD;
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MachineryConnectViewModel.dependencyFactory = dependencyFactory;
    }

    public void onFragmentInit(Context context) {
        PublicMainViewModel mainViewModel = getMainActivityModel(context);
        mainViewModel.getUpdateTitleMLD().postValue(R.string.machineryConnect_connectToDevice);

        getMachineryConnectMLD().postValue(new MachineryConnectVDO(
                new MessageBoxVDO(R.string.machineryConnect_deviceNotConnectedYet),
                true
        ));
    }

    private PublicMainViewModel getMainActivityModel(Context context) {
        if (context instanceof MainActivity) {
            return ((MainActivity) context).getModel();
        } else
            throw new RuntimeException("MainActivity expected");
    }

    public void onConnectButton(Context context) {
        getMachineryConnectMLD().postValue(new MachineryConnectVDO(
                new MessageBoxVDO(R.string.machineryConnect_connecting),
                false
        ));

        getDependencyFactory().getDeviceConnectivity().connect(
                getConnectionSuccessCallback(context),
                getConnectionFailCallback()
        );
    }

    private Consumer<String> getConnectionFailCallback() {
        return errorMessage -> getMachineryConnectMLD().postValue(new MachineryConnectVDO(
                new MessageBoxVDO(errorMessage),
                true
        ));
    }

    private Runnable getConnectionSuccessCallback(Context context) {
        return () -> getMainActivityModel(context).getSwitchFramgmentMLD().postValue(
                new NewFragmentVDO(
                        getDependencyFactory().getFragmentFactory().getCurrentScheduleFragment(),
                        false
                )
        );
    }

    public static class DependencyFactory {
        public FragmentFactory getFragmentFactory() {
            return new FragmentFactory();
        }

        public DeviceConnectivity getDeviceConnectivity() {
            return DeviceConnectivityFactory.getInstance();
        }
    }
}
