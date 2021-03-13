package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModelImpl extends ViewModel implements MainViewModel {

    private MutableLiveData<Fragment> switchFramgmentMLD = new MutableLiveData<>();

    private MutableLiveData<String> updateTitleMLD = new MutableLiveData<>();

    @Override
    public MutableLiveData<Fragment> getSwitchFramgmentMLD() {
        return switchFramgmentMLD;
    }

    @Override
    public MutableLiveData<String> getUpdateTitleMLD() {
        return updateTitleMLD;
    }
}
