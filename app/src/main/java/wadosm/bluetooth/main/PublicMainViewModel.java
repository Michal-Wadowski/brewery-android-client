package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

public interface PublicMainViewModel {

    MutableLiveData<Fragment> getSwitchFramgmentMLD();

    MutableLiveData<String> getUpdateTitleMLD();

}
