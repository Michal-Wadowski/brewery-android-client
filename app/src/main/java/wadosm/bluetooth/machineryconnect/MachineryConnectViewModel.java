package wadosm.bluetooth.machineryconnect;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import wadosm.bluetooth.common.AbstractViewModel;

@HiltViewModel
public class MachineryConnectViewModel extends AbstractViewModel implements PublicMachineryConnect {

    private MachineryConnectLogic machineryConnectLogic;

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

    public void onConnectButton(Activity activity) {
        machineryConnectLogic.onConnectButton(this, activity);
    }

}
