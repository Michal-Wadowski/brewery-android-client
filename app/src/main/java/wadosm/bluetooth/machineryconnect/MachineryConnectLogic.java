package wadosm.bluetooth.machineryconnect;

import android.app.Activity;

import java.util.function.Consumer;

import javax.inject.Inject;

import wadosm.bluetooth.R;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.dependency.FragmentFactory;
import wadosm.bluetooth.main.NewFragmentVDO;

public class MachineryConnectLogic {

    private FragmentFactory fragmentFactory;

    private DeviceConnectivity deviceConnectivity;

    @Inject
    public MachineryConnectLogic(FragmentFactory fragmentFactory, DeviceConnectivity deviceConnectivity) {
        this.fragmentFactory = fragmentFactory;
        this.deviceConnectivity = deviceConnectivity;
    }

    public void onFragmentInit(PublicMachineryConnect machineryConnect, Activity activity) {
        machineryConnect.updateTitle(activity, R.string.machineryConnect_connectToDevice);

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
        return () -> machineryConnect.switchFramgment(activity,
                new NewFragmentVDO(
                        fragmentFactory.getCurrentScheduleFragment(),
                        false
                )
        );
    }

}
