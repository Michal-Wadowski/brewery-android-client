package wadosm.bluetooth.currentschedule;

import android.app.Activity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.bluetooth.common.AbstractViewModel;

@HiltViewModel
public class CurrentScheduleViewModel extends AbstractViewModel {

    @Inject
    public CurrentScheduleViewModel() {
    }

    @Override
    public void onFragmentInit(Activity activity) {

    }

}
