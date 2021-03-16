package wadosm.bluetooth.common;

import android.content.Context;

import wadosm.bluetooth.main.NewFragmentVDO;

public interface CommonViewModelInteraction {
    void updateTitle(Context context, int title);

    void switchFramgment(Context context, NewFragmentVDO newFragment);

    void onFragmentInit(Context context);
}
