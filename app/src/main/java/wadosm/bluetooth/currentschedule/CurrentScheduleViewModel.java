package wadosm.bluetooth.currentschedule;

import android.content.Context;

import androidx.lifecycle.ViewModel;

public class CurrentScheduleViewModel extends ViewModel {

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    public CurrentScheduleViewModel() {

    }

    public void onFragmentInit(Context context) {

    }


    public interface DependencyFactory {
    }

    public static class DefaulDependencyFactory implements DependencyFactory {
    }
}
