package wadosm.bluetooth.machineryconnect;

import android.content.Context;

import java.util.function.Consumer;

import wadosm.bluetooth.R;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.connectivity.DeviceConnectivityFactory;
import wadosm.bluetooth.dependency.FragmentFactoryImpl;
import wadosm.bluetooth.main.NewFragmentVDO;

public class MachineryConnectLogic {

    PublicMachineryConnect machineryConnect;

    public MachineryConnectLogic(PublicMachineryConnect machineryConnect) {
        this.machineryConnect = machineryConnect;
    }

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        MachineryConnectLogic.dependencyFactory = dependencyFactory;
    }

    public void onFragmentInit(Context context) {
        machineryConnect.updateTitle(context, R.string.machineryConnect_connectToDevice);

        machineryConnect.updateMachineryConnect(new MachineryConnectVDO(
                new MessageBoxVDO(R.string.machineryConnect_deviceNotConnectedYet),
                true
        ));
    }

    public void onConnectButton(Context context) {
        machineryConnect.updateMachineryConnect(new MachineryConnectVDO(
                new MessageBoxVDO(R.string.machineryConnect_connecting),
                false
        ));

        getDependencyFactory().getDeviceConnectivity().connect(
                getConnectionSuccessCallback(context),
                getConnectionFailCallback()
        );
    }

    private Consumer<String> getConnectionFailCallback() {

        return errorMessage -> machineryConnect.updateMachineryConnect(new MachineryConnectVDO(
                new MessageBoxVDO(errorMessage),
                true
        ));
    }

    private Runnable getConnectionSuccessCallback(Context context) {
        return () -> machineryConnect.switchFramgment(context,
                new NewFragmentVDO(
                        getDependencyFactory().getFragmentFactory().getCurrentScheduleFragment(),
                        false
                )
        );
    }

    public static class DependencyFactory {
        public FragmentFactoryImpl getFragmentFactory() {
            return new FragmentFactoryImpl();
        }

        public DeviceConnectivity getDeviceConnectivity() {
            return DeviceConnectivityFactory.getInstance();
        }
    }

}
