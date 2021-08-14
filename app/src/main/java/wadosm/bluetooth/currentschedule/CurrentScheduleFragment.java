package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.switchmaterial.SwitchMaterial;

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

    SwitchMaterial swPower;
    SwitchMaterial swMotor1;
    SwitchMaterial swMotor2;
    SwitchMaterial swMotor3;
    SeekBar sbSound;
    SeekBar sbMains1;
    SeekBar sbMains2;


    public static CurrentScheduleFragment newInstance() {
        return new CurrentScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = buildViews(inflater, container);

        initChildFragments();

        model = buildModel();

        model.onFragmentInit(getActivity());

        setListeners(model);

        return currentView;
    }

    private void setListeners(CurrentScheduleViewModel model) {
        model.getDataFetchedMLD().observe(getViewLifecycleOwner(), this::updateDataFetched);

        swPower.setOnCheckedChangeListener((buttonView, isChecked) -> model.onSwitchPower(getActivity(), isChecked));
        swMotor1.setOnCheckedChangeListener((buttonView, isChecked) -> model.onSwitchMotor(getActivity(), 1, isChecked));
        swMotor2.setOnCheckedChangeListener((buttonView, isChecked) -> model.onSwitchMotor(getActivity(), 2, isChecked));
        swMotor3.setOnCheckedChangeListener((buttonView, isChecked) -> model.onSwitchMotor(getActivity(), 3, isChecked));

        sbSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                model.onSeekBarSound(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbMains1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                model.onSeekBarMains(1, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sbMains2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                model.onSeekBarMains(2, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private View buildViews(LayoutInflater inflater, ViewGroup container) {
        View currentView = inflater.inflate(R.layout.fragment_current_schedule, container, false);

        swPower = currentView.findViewById(R.id.swPower);
        swMotor1 = currentView.findViewById(R.id.swMotor1);
        swMotor2 = currentView.findViewById(R.id.swMotor2);
        swMotor3 = currentView.findViewById(R.id.swMotor3);

        sbSound = currentView.findViewById(R.id.sbSound);
        sbMains1 = currentView.findViewById(R.id.sbMains1);
        sbMains2 = currentView.findViewById(R.id.sbMains2);

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
