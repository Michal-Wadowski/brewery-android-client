package wadosm.brewingclient.machineryconnect;

import android.app.Activity;

import javax.inject.Inject;

import wadosm.brewingclient.R;
import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.connectivity.DeviceConnectivity;
import wadosm.brewingclient.dependency.FragmentFactory;
import wadosm.brewingclient.machineryconnect.model.MachineryConnectVDO;
import wadosm.brewingclient.machineryconnect.model.MessageBoxVDO;
import wadosm.brewingclient.main.model.NewFragmentVDO;

public class MachineryConnectLogic {

    private FragmentFactory fragmentFactory;

    private DeviceConnectivity deviceConnectivity;

    @Inject
    public MachineryConnectLogic(FragmentFactory fragmentFactory, DeviceConnectivity deviceConnectivity) {
        this.fragmentFactory = fragmentFactory;
        this.deviceConnectivity = deviceConnectivity;
    }

    public void onFragmentInit(PublicMachineryConnect machineryConnect, Activity activity) {
        machineryConnect.updateTitle(activity, R.string.machineryConnect_deviceConnecting);

        machineryConnect.updateMachineryConnect(new MachineryConnectVDO(
                new MessageBoxVDO(R.string.machineryConnect_deviceNotConnectedYet),
                true
        ));
    }

    public void onConnectButton(PublicMachineryConnect machineryConnect, Activity activity) {
        machineryConnect.updateMachineryConnect(new MachineryConnectVDO(
                new MessageBoxVDO(R.string.machineryConnect_connecting),
                false
        ));

        deviceConnectivity.connect(
                getConnectionSuccessCallback(machineryConnect, activity),
                getConnectionFailCallback(machineryConnect)
        );
    }

    private Consumer<String> getConnectionFailCallback(PublicMachineryConnect machineryConnect) {
        return errorMessage -> machineryConnect.updateMachineryConnect(new MachineryConnectVDO(
                new MessageBoxVDO(errorMessage),
                true
        ));
    }

    private Runnable getConnectionSuccessCallback(PublicMachineryConnect machineryConnect, Activity activity) {
        // TODO: Attach onDisconnected callack to go back to MachineryConnectFragment
        // TODO: Write tests for that

        return () -> machineryConnect.switchFramgment(activity,
                new NewFragmentVDO(
                        fragmentFactory.getCurrentScheduleFragment(),
                        false
                )
        );
    }

}
