package wadosm.bluetooth.currentstate;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import wadosm.bluetooth.common.AbstractViewModel;

public class CurrentStateViewModel extends AbstractViewModel {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    @Override
    public void onFragmentInit(Context context) {
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        CurrentStateViewModel.dependencyFactory = dependencyFactory;
    }

    public static class DependencyFactory {
    }
}
