package wadosm.bluetooth.common;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.NewFragmentVDO;
import wadosm.bluetooth.main.PublicMainViewModel;

public abstract class AbstractViewModel extends ViewModel implements CommonViewModelInteraction {

    @Override
    public void updateTitle(Activity activity, int title) {
        PublicMainViewModel mainViewModel = getMainActivityModel(activity);
        mainViewModel.updateTitle(title);
    }

    @Override
    public void switchFramgment(Activity activity, NewFragmentVDO newFragment) {
        PublicMainViewModel mainViewModel = getMainActivityModel(activity);
        mainViewModel.switchFramgment(newFragment);
    }

    @Override
    abstract public void onFragmentInit(Activity activity);

    protected PublicMainViewModel getMainActivityModel(Activity activity) {
        if (activity instanceof MainActivity) {
            return ((MainActivity) activity).getModel();
        } else {
            throw new RuntimeException("MainActivity expected");
        }
    }

}
