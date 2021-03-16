package wadosm.bluetooth.main;

import androidx.lifecycle.MutableLiveData;

public interface PublicMainViewModel {

    MutableLiveData<NewFragmentVDO> getSwitchFramgmentMLD();

    MutableLiveData<Integer> getUpdateTitleMLD();

}
