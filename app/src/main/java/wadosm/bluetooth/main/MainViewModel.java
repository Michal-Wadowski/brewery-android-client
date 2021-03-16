package wadosm.bluetooth.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import wadosm.bluetooth.FragmentFactory;

public class MainViewModel extends ViewModel implements PublicMainViewModel {

    private final MutableLiveData<NewFragmentVDO> switchFramgmentMLD = new MutableLiveData<>();

    private final MutableLiveData<Integer> updateTitleMLD = new MutableLiveData<>();

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    private boolean screenInitialized = false;

    @Override
    public MutableLiveData<NewFragmentVDO> getSwitchFramgmentMLD() {
        return switchFramgmentMLD;
    }

    @Override
    public MutableLiveData<Integer> getUpdateTitleMLD() {
        return updateTitleMLD;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MainViewModel.dependencyFactory = dependencyFactory;
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public void onActivityStart() {
        if (!screenInitialized) {
            screenInitialized = true;

            getSwitchFramgmentMLD().postValue(
                    new NewFragmentVDO(
                            getDependencyFactory().getFragmentFactory().getMachineryConnectFragment(),
                            false
                    )
            );
        }
    }

    public static class DependencyFactory {
        public FragmentFactory getFragmentFactory() {
            return new FragmentFactory();
        }
    }
}
