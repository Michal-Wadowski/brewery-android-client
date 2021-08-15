package wadosm.brewingclient.common;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import wadosm.brewingclient.main.MainActivity;
import wadosm.brewingclient.main.PublicMainViewModel;
import wadosm.brewingclient.main.model.NewFragmentVDO;

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

    @Override
    abstract public void onFragmentDetach(Activity activity);

    protected PublicMainViewModel getMainActivityModel(Activity activity) {
        if (activity instanceof MainActivity) {
            return ((MainActivity) activity).getModel();
        } else {
            throw new RuntimeException("MainActivity expected");
        }
    }

}
