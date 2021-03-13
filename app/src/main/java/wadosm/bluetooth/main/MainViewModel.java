package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

public interface MainViewModel {

    MutableLiveData<Fragment> getSwitchFramgmentMLD();

    MutableLiveData<String> getUpdateTitleMLD();
}
