package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
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
import wadosm.bluetooth.main.model.NewFragmentVDO;

@AndroidEntryPoint
public class CurrentScheduleFragment extends Fragment {

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    private CurrentScheduleViewModel model;

    public static CurrentScheduleFragment newInstance() {
        return new CurrentScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = buildViews(inflater, container);


        prepareShutdownOptions(currentView);

        Button btnFermenting = currentView.findViewById(R.id.btn_fermenting);
        btnFermenting.setOnClickListener(v -> model.switchFramgment(getActivity(),
                new NewFragmentVDO(
                        FermentingFragment.newInstance(model),
                        true
                )
        ));
        Button btnBrewing = currentView.findViewById(R.id.btn_brewing);
        btnBrewing.setOnClickListener(v -> {

        });

        getActivity().setTitle("Połączono");

        model = buildModel();

        return currentView;
    }

    private void prepareShutdownOptions(View currentView) {
        Spinner dropdown = currentView.findViewById(R.id.sp_poweroff_restart);
        String[] items = new String[]{"---", "Wyłącz", "Uruchom ponownie"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    model.getDeviceConnectivity().getDeviceService().powerOff();

                    model.switchFramgment(getActivity(),
                            new NewFragmentVDO(
                                    model.getFragmentFactory().getMachineryConnectFragment(),
                                    false
                            )
                    );
                } else if (position == 2) {
                    model.getDeviceConnectivity().getDeviceService().restart();

                    model.switchFramgment(getActivity(),
                            new NewFragmentVDO(
                                    model.getFragmentFactory().getMachineryConnectFragment(),
                                    false
                            )
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private View buildViews(LayoutInflater inflater, ViewGroup container) {
        View currentView = inflater.inflate(R.layout.fragment_current_schedule, container, false);


        return currentView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private CurrentScheduleViewModel buildModel() {
        return viewModelProviderFactory.getViewModelProvider(this).get(CurrentScheduleViewModel.class);
    }

}
