package wadosm.bluetooth.main;

import androidx.lifecycle.MutableLiveData;

public interface PublicMainViewModel {

    MutableLiveData<NewFragment> getSwitchFramgmentMLD();

    MutableLiveData<Integer> getUpdateTitleMLD();

}
