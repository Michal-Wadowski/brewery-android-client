package wadosm.bluetooth.currentschedule;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class CurrentScheduleViewModel extends ViewModel {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    public CurrentScheduleViewModel() {

    }

    public void onFragmentInit(Context context) {

    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        CurrentScheduleViewModel.dependencyFactory = dependencyFactory;
    }

    public static class DependencyFactory {
    }
}
