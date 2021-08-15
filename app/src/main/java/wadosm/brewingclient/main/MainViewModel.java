package wadosm.brewingclient.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.brewingclient.dependency.FragmentFactory;
import wadosm.brewingclient.main.model.NewFragmentVDO;

@HiltViewModel
public class MainViewModel extends ViewModel implements PublicMainViewModel {

    private FragmentFactory fragmentFactory;

    private final MutableLiveData<NewFragmentVDO> switchFramgmentMLD = new MutableLiveData<>();

    private final MutableLiveData<Integer> updateTitleMLD = new MutableLiveData<>();

    private boolean screenInitialized = false;

    public MutableLiveData<NewFragmentVDO> getSwitchFramgmentMLD() {
        return switchFramgmentMLD;
    }

    public MutableLiveData<Integer> getUpdateTitleMLD() {
        return updateTitleMLD;
    }

    @Inject
    MainViewModel(FragmentFactory fragmentFactory) {
        this.fragmentFactory = fragmentFactory;
    }

    @Override
    public void updateTitle(int title) {
        getUpdateTitleMLD().postValue(title);
    }

    @Override
    public void switchFramgment(NewFragmentVDO newFragment) {
        getSwitchFramgmentMLD().postValue(newFragment);
    }

    public void onActivityInit() {
        if (!screenInitialized) {
            screenInitialized = true;

            switchFramgment(
                    new NewFragmentVDO(
                            fragmentFactory.getMachineryConnectFragment(),
                            false
                    )
            );
        }
    }
}
