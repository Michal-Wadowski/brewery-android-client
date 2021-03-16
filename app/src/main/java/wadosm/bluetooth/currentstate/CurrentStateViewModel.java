package wadosm.bluetooth.currentstate;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class CurrentStateViewModel extends ViewModel {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

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