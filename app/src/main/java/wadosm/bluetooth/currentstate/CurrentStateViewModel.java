package wadosm.bluetooth.currentstate;

import android.app.Activity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.bluetooth.common.AbstractViewModel;

@HiltViewModel
public class CurrentStateViewModel extends AbstractViewModel {

    @Inject
    public CurrentStateViewModel() {
    }

    @Override
    public void onFragmentInit(Activity activity) {
    }

}
