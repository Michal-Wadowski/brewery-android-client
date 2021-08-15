package wadosm.brewingclient.common;

import android.app.Activity;

import wadosm.brewingclient.main.model.NewFragmentVDO;

public interface CommonViewModelInteraction {
    void updateTitle(Activity activity, int title);

    void switchFramgment(Activity activity, NewFragmentVDO newFragment);

    void onFragmentInit(Activity activity);

    void onFragmentDetach(Activity activity);
}
