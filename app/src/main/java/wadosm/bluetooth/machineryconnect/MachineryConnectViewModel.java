package wadosm.bluetooth.machineryconnect;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import wadosm.bluetooth.common.AbstractViewModel;

public class MachineryConnectViewModel extends AbstractViewModel implements PublicMachineryConnect {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    private final MutableLiveData<MachineryConnectVDO> machineryConnectMLD = new MutableLiveData<>();

    public MutableLiveData<MachineryConnectVDO> getMachineryConnectMLD() {
        return machineryConnectMLD;
    }

    private MachineryConnectLogic machineryConnectLogic;

    public MachineryConnectViewModel() {
        machineryConnectLogic = getDependencyFactory().getMachineryConnectLogic(this);
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MachineryConnectViewModel.dependencyFactory = dependencyFactory;
    }

    @Override
    public void updateMachineryConnect(MachineryConnectVDO machineryConnectVDO) {
        getMachineryConnectMLD().postValue(machineryConnectVDO);
    }

    @Override
    public void onFragmentInit(Context context) {
        machineryConnectLogic.onFragmentInit(context);
    }

    public void onConnectButton(Context context) {
        machineryConnectLogic.onConnectButton(context);
    }

    public static class DependencyFactory {
        public MachineryConnectLogic getMachineryConnectLogic(PublicMachineryConnect machineryConnect) {
            return new MachineryConnectLogic(machineryConnect);
        }
    }
}
