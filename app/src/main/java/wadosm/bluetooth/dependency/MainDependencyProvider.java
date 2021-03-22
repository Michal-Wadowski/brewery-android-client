package wadosm.bluetooth.dependency;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import wadosm.bluetooth.BuildConfig;
import wadosm.bluetooth.connectivity.DeviceConnectivity;
import wadosm.bluetooth.connectivity.demo.DemoDeviceConnectivity;

@Module
@InstallIn(SingletonComponent.class)
public class MainDependencyProvider {

    public static FragmentFactory fragmentFactory;

    public static ViewModelProviderFactory viewModelProviderFactory;

    public static DeviceConnectivity deviceConnectivity;

    @Singleton
    @Provides
    FragmentFactory bindFragmentFactory() {
        if (fragmentFactory != null) {
            return fragmentFactory;
        }

        return new FragmentFactoryImpl();
    }

    @Singleton
    @Provides
    ViewModelProviderFactory bindViewModelProviderFactory() {
        if (viewModelProviderFactory != null) {
            return viewModelProviderFactory;
        }

        return new ViewModelProviderFactoryImpl();
    }

    @Singleton
    @Provides
    DeviceConnectivity bindDeviceConnectivity() {
        if (deviceConnectivity != null) {
            return deviceConnectivity;
        }

        if (BuildConfig.FLAVOR.equals("demo")) {
            return new DemoDeviceConnectivity();
        } else {
            return null;
        }
    }
}
