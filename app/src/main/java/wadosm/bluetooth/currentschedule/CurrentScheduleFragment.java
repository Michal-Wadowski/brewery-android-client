package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.currentstate.CurrentStateFragment;
import wadosm.bluetooth.dependency.ViewModelProviderFactory;

@AndroidEntryPoint
public class CurrentScheduleFragment extends Fragment {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private CurrentScheduleViewModel model;

//    private RecyclerView currentScheduleRecyler;
//
//    private RecyclerView.Adapter currentScheduleAdapter;

//    private List<ScheduleItem> content = new ArrayList<>();

    public static CurrentScheduleFragment newInstance() {
        return new CurrentScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_current_schedule, container, false);

//        content.add(new ScheduleItem());
//        content.add(new ScheduleItem());
//
//        currentScheduleRecyler = currentView.findViewById(R.id.currentScheduleRecyler);
//        currentScheduleAdapter = new ScheduleAdapter(content);
//
//        currentScheduleRecyler.setLayoutManager(new LinearLayoutManager(getContext()));
//        currentScheduleRecyler.setAdapter(currentScheduleAdapter);
//        currentScheduleRecyler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        initChildFragments();

        model = buildModel();

        model.onFragmentInit(getActivity());

        model.getDataFetchedMLD().observe(getViewLifecycleOwner(), this::updateDataFetched);

        return currentView;
    }

    @Override
    public void onDetach() {
        model.onFragmentDetach(getActivity());

        super.onDetach();
    }

    private void updateDataFetched(boolean fetched) {
        TextView dataFetching = getView().findViewById(R.id.dataFetching);
        dataFetching.setVisibility(fetched ? View.GONE : View.VISIBLE);
    }

    private void initChildFragments() {
        FragmentTransaction manager = getChildFragmentManager().beginTransaction();
        manager.replace(R.id.stateContainer, CurrentStateFragment.newInstance());
        manager.commit();
    }

    private CurrentScheduleViewModel buildModel() {
        return viewModelProviderFactory.getViewModelProvider(this).get(CurrentScheduleViewModel.class);
    }

}
