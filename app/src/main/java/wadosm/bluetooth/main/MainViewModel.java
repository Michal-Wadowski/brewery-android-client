package wadosm.bluetooth.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.bluetooth.dependency.FragmentFactory;

@HiltViewModel
// TODO: should implements CommonViewModelInteraction
public class MainViewModel extends ViewModel implements PublicMainViewModel {

    private FragmentFactory fragmentFactory;

    private final MutableLiveData<NewFragmentVDO> switchFramgmentMLD = new MutableLiveData<>();

    private final MutableLiveData<Integer> updateTitleMLD = new MutableLiveData<>();

    private boolean screenInitialized = false;

    @Inject
    MainViewModel(FragmentFactory fragmentFactory) {
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    public MutableLiveData<NewFragmentVDO> getSwitchFramgmentMLD() {
        return switchFramgmentMLD;
    }

    @Override
    public MutableLiveData<Integer> getUpdateTitleMLD() {
        return updateTitleMLD;
    }

    public void onActivityStart() {
        if (!screenInitialized) {
            screenInitialized = true;

            getSwitchFramgmentMLD().postValue(
                    new NewFragmentVDO(
                            fragmentFactory.getMachineryConnectFragment(),
                            false
                    )
            );
        }
    }
}
