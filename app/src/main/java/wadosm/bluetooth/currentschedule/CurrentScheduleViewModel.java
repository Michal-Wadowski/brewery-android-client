package wadosm.bluetooth.currentschedule;

import android.content.Context;

import wadosm.bluetooth.common.AbstractViewModel;

public class CurrentScheduleViewModel extends AbstractViewModel {

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
