package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import wadosm.bluetooth.R;

public class CurrentScheduleFragment extends Fragment {

    static DependencyFactory dependencyFactory = new DefaulDependencyFactory();

    CurrentScheduleViewModel model;

    public static CurrentScheduleFragment newInstance() {
        return new CurrentScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_current_schedule, container, false);

        model = dependencyFactory.getModel(this);

        model.onFragmentInit(getContext());

        return currentView;
    }

    public interface DependencyFactory {
        CurrentScheduleViewModel getModel(CurrentScheduleFragment owner);
    }

    public static class DefaulDependencyFactory implements DependencyFactory {
        @Override
        public CurrentScheduleViewModel getModel(CurrentScheduleFragment owner) {
            return new ViewModelProvider(owner).get(CurrentScheduleViewModel.class);
        }
    }
}
