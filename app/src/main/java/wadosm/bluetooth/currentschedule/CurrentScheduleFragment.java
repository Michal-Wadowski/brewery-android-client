package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import wadosm.bluetooth.R;
import wadosm.bluetooth.currentstate.CurrentStateFragment;

public class CurrentScheduleFragment extends Fragment {

    private static DependencyFactory dependencyFactory = new DependencyFactory();

    private CurrentScheduleViewModel model;

    private RecyclerView currentScheduleRecyler;

    private RecyclerView.Adapter currentScheduleAdapter;

    private List<ScheduleItem> content = new ArrayList<>();

    public static CurrentScheduleFragment newInstance() {
        return new CurrentScheduleFragment();
    }

    public static DependencyFactory getDependencyFactory() {
        return dependencyFactory;
    }

    public static void setDependencyFactory(DependencyFactory dependencyFactory) {
        CurrentScheduleFragment.dependencyFactory = dependencyFactory;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_current_schedule, container, false);

        content.add(new ScheduleItem());
        content.add(new ScheduleItem());

        currentScheduleRecyler = currentView.findViewById(R.id.currentScheduleRecyler);
        currentScheduleAdapter = new ScheduleAdapter(content);

        currentScheduleRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
        currentScheduleRecyler.setAdapter(currentScheduleAdapter);
        currentScheduleRecyler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        FragmentTransaction manager = getChildFragmentManager().beginTransaction();
        manager.replace(R.id.stateContainer, CurrentStateFragment.newInstance());
        manager.commit();

        model = getDependencyFactory().getModel(this);

        model.onFragmentInit(getContext());

        return currentView;
    }

    public static class DependencyFactory {
        public CurrentScheduleViewModel getModel(CurrentScheduleFragment owner) {
            return new ViewModelProvider(owner).get(CurrentScheduleViewModel.class);
        }
    }
}
