package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelImpl extends ViewModel implements MainViewModel {

    private MutableLiveData<Fragment> switchFramgmentMLD = new MutableLiveData<>();

    private MutableLiveData<String> updateTitleMLD = new MutableLiveData<>();

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    FragmentFactory fragmentFactory;

    public MainViewModelImpl() {
        fragmentFactory = dependencyFactory.getFragmentFactory();
    }

    @Override
    public MutableLiveData<Fragment> getSwitchFramgmentMLD() {
        return switchFramgmentMLD;
    }

    @Override
    public MutableLiveData<String> getUpdateTitleMLD() {
        return updateTitleMLD;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MainViewModelImpl.dependencyFactory = dependencyFactory;
    }

    @Override
    public void onActivityStart() {
        getSwitchFramgmentMLD().postValue(
                fragmentFactory.getMachineryConnectFragment()
        );
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
