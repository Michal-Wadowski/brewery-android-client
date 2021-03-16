package wadosm.bluetooth.common;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import wadosm.bluetooth.main.MainActivity;
import wadosm.bluetooth.main.NewFragmentVDO;
import wadosm.bluetooth.main.PublicMainViewModel;

public abstract class AbstractViewModel extends ViewModel implements CommonViewModelInteraction {

    @Override
    public void updateTitle(Context context, int title) {
        PublicMainViewModel mainViewModel = getMainActivityModel(context);
        mainViewModel.getUpdateTitleMLD().postValue(title);
    }

    @Override
    public void switchFramgment(Context context, NewFragmentVDO newFragment) {
        PublicMainViewModel mainViewModel = getMainActivityModel(context);
        mainViewModel.getSwitchFramgmentMLD().postValue(newFragment);
    }

    @Override
    abstract public void onFragmentInit(Context context);

    protected PublicMainViewModel getMainActivityModel(Context context) {
        if (context instanceof MainActivity) {
            return ((MainActivity) context).getModel();
        } else
            throw new RuntimeException("MainActivity expected");
    }

}
