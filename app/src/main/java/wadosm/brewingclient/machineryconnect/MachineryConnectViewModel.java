package wadosm.brewingclient.machineryconnect;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.brewingclient.common.AbstractViewModel;
import wadosm.brewingclient.machineryconnect.model.MachineryConnectVDO;

@HiltViewModel
public class MachineryConnectViewModel extends AbstractViewModel implements PublicMachineryConnect {

    private final MachineryConnectLogic machineryConnectLogic;

    private final MutableLiveData<MachineryConnectVDO> machineryConnectMLD = new MutableLiveData<>();

    public MutableLiveData<MachineryConnectVDO> getMachineryConnectMLD() {
        return machineryConnectMLD;
    }

    @Inject
    public MachineryConnectViewModel(MachineryConnectLogic machineryConnectLogic) {
        this.machineryConnectLogic = machineryConnectLogic;
    }

    @Override
    public void updateMachineryConnect(MachineryConnectVDO machineryConnectVDO) {
        getMachineryConnectMLD().postValue(machineryConnectVDO);
    }

    @Override
    public void onFragmentInit(Activity activity) {
        machineryConnectLogic.onFragmentInit(this, activity);
    }

    @Override
    public void onFragmentDetach(Activity activity) {
    }

    public void onConnectButton(Activity activity) {

//        if (activity instanceof MainActivity) {
//            MainActivity ma = (MainActivity)activity;
//            ma.sendMessage();
//        }

        machineryConnectLogic.onConnectButton(this, activity);
    }

}
