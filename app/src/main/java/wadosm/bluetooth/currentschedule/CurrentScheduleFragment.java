package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.currentstate.CurrentStateFragment;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;

@AndroidEntryPoint
public class CurrentScheduleFragment extends Fragment {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private RecyclerView currentScheduleRecyler;

    private RecyclerView.Adapter currentScheduleAdapter;

    private List<ScheduleItem> content = new ArrayList<>();

    public static CurrentScheduleFragment newInstance() {
        return new CurrentScheduleFragment();
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

        CurrentScheduleViewModel model = buildModel();

        model.onFragmentInit(getActivity());

        return currentView;
    }

    private CurrentScheduleViewModel buildModel() {
        return viewModelProviderFactory.getViewModelProvider(this).get(CurrentScheduleViewModel.class);
    }

}
