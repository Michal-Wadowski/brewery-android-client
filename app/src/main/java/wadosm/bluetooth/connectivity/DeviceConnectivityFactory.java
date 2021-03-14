package wadosm.bluetooth.connectivity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wadosm.bluetooth.BuildConfig;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeviceConnectivityFactory {

    public static DeviceConnectivity getInstance() {
        if (BuildConfig.FLAVOR.equals("demo")) {
            return new DemoDeviceConnectivity();
        }

        return null;
    }

}
