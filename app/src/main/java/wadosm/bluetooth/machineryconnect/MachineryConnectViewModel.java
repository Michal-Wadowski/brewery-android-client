package wadosm.bluetooth.machineryconnect;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import wadosm.bluetooth.R;
import wadosm.bluetooth.main.FragmentFactory;
import wadosm.bluetooth.main.PublicMainViewModel;

public class MachineryConnectViewModel extends ViewModel {

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    private final FragmentFactory fragmentFactory;

    private final MutableLiveData<Integer> messagesBoxMLD = new MutableLiveData<>();

    public MachineryConnectViewModel() {
        fragmentFactory = dependencyFactory.getFragmentFactory();
    }

    public MutableLiveData<Integer> getMessagesBoxMLD() {
        return messagesBoxMLD;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MachineryConnectViewModel.dependencyFactory = dependencyFactory;
    }

    public void onFragmentInit(PublicMainViewModel context) {
        context.getUpdateTitleMLD().postValue(R.string.machineryConnect_connectToDevice);
    }

    public void onConnectButton() {
        messagesBoxMLD.postValue(R.string.machineryConnect_connecting);
    }

    public interface DependencyFactory {
        FragmentFactory getFragmentFactory();
    }

    public static class DefaulDependencyFactory implements DependencyFactory {
        @Override
        public FragmentFactory getFragmentFactory() {
            return new FragmentFactory();
        }
    }
}
