package wadosm.bluetooth.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import wadosm.bluetooth.FragmentFactory;

public class MainViewModel extends ViewModel implements PublicMainViewModel {

    private final MutableLiveData<NewFragment> switchFramgmentMLD = new MutableLiveData<>();

    private final MutableLiveData<Integer> updateTitleMLD = new MutableLiveData<>();

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    private final FragmentFactory fragmentFactory;

    public MainViewModel() {
        fragmentFactory = dependencyFactory.getFragmentFactory();
    }

    @Override
    public MutableLiveData<NewFragment> getSwitchFramgmentMLD() {
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
                    new NewFragment(
                            fragmentFactory.getMachineryConnectFragment(),
                            false
                    )
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
