package wadosm.bluetooth.dependency;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import wadosm.bluetooth.BuildConfig;
import wadosm.bluetooth.connectivity.DemoDeviceConnectivity;
import wadosm.bluetooth.connectivity.DeviceConnectivity;

@Module
@InstallIn(SingletonComponent.class)
public class MainDependencyProvider {

    public static FragmentFactory fragmentFactory;

    public static ViewModelProviderFactory viewModelProviderFactory;

    public static DemoDeviceConnectivity demoDeviceConnectivity;

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
        if (demoDeviceConnectivity != null) {
            return demoDeviceConnectivity;
        }

        if (BuildConfig.FLAVOR.equals("demo")) {
            return new DemoDeviceConnectivity();
        } else {
            return null;
        }
    }
}
