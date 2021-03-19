package wadosm.bluetooth.dependency;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class MainDependencyProvider {

    public static FragmentFactory fragmentFactory;

    public static ViewModelProviderFactory viewModelProviderFactory;

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

}
