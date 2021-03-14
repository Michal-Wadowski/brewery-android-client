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
import wadosm.bluetooth.main.NewFragment;
import wadosm.bluetooth.main.PublicMainViewModel;

public class MachineryConnectViewModel extends ViewModel {

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    private final FragmentFactory fragmentFactory;

    private final MutableLiveData<MessageBoxContent> messagesBoxMLD = new MutableLiveData<>();

    private final MutableLiveData<Boolean> connectButtonEnableMLD = new MutableLiveData<>();

    public MachineryConnectViewModel() {
        fragmentFactory = dependencyFactory.getFragmentFactory();
    }

    public MutableLiveData<MessageBoxContent> getMessagesBoxMLD() {
        return messagesBoxMLD;
    }

    public MutableLiveData<Boolean> getConnectButtonEnableMLD() {
        return connectButtonEnableMLD;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MachineryConnectViewModel.dependencyFactory = dependencyFactory;
    }

    public void onFragmentInit(Context context) {
        PublicMainViewModel mainViewModel = getMainActivityModel(context);
        mainViewModel.getUpdateTitleMLD().postValue(R.string.machineryConnect_connectToDevice);
        getMessagesBoxMLD().postValue(new MessageBoxContent(R.string.machineryConnect_deviceNotConnectedYet));
        getConnectButtonEnableMLD().postValue(true);
    }

    private PublicMainViewModel getMainActivityModel(Context context) {
        if (context instanceof MainActivity) {
            return ((MainActivity) context).getModel();
        } else
            return null;
    }

    public void onConnectButton(Context context) {
        getMessagesBoxMLD().postValue(new MessageBoxContent(R.string.machineryConnect_connecting));
        getConnectButtonEnableMLD().postValue(false);
        dependencyFactory.getDeviceConnectivity().connect(
                getConnectionSuccessCallback(context),
                getConnectionFailCallback()
        );
    }

    private Consumer<String> getConnectionFailCallback() {
        return errorMessage -> {
            getConnectButtonEnableMLD().postValue(true);
            getMessagesBoxMLD().postValue(new MessageBoxContent(errorMessage));
        };
    }

    private Runnable getConnectionSuccessCallback(Context context) {
        return () -> getMainActivityModel(context).getSwitchFramgmentMLD().postValue(
                new NewFragment(
                        fragmentFactory.getCurrentScheduleFragment(),
                        false
                )
        );
    }

    public interface DependencyFactory {
        FragmentFactory getFragmentFactory();

        DeviceConnectivity getDeviceConnectivity();
    }

    public static class DefaulDependencyFactory implements DependencyFactory {

        @Override
        public FragmentFactory getFragmentFactory() {
            return new FragmentFactory();
        }

        @Override
        public DeviceConnectivity getDeviceConnectivity() {
            return DeviceConnectivityFactory.getInstance();
        }
    }
}
