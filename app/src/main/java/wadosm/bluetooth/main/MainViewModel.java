package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements PublicMainViewModel {

    private final MutableLiveData<Fragment> switchFramgmentMLD = new MutableLiveData<>();

    private final MutableLiveData<Integer> updateTitleMLD = new MutableLiveData<>();

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    private final FragmentFactory fragmentFactory;

    public MainViewModel() {
        fragmentFactory = dependencyFactory.getFragmentFactory();
    }

    @Override
    public MutableLiveData<Fragment> getSwitchFramgmentMLD() {
        return switchFramgmentMLD;
    }

    @Override
    public MutableLiveData<Integer> getUpdateTitleMLD() {
        return updateTitleMLD;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MainViewModel.dependencyFactory = dependencyFactory;
    }

    private boolean screenInitialized = false;

    public void onActivityStart() {
        if (!screenInitialized) {
            screenInitialized = true;

            getSwitchFramgmentMLD().postValue(
                    fragmentFactory.getMachineryConnectFragment()
            );
        }
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
