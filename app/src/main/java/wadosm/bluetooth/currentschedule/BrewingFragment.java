package wadosm.bluetooth.currentschedule;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.bluetooth.R;
import wadosm.bluetooth.common.Consumer;
import wadosm.bluetooth.currentschedule.dto.BrewingStatusResponse;
import wadosm.bluetooth.currentschedule.dto.ResponseDTO;

@AndroidEntryPoint
public class BrewingFragment extends Fragment {

    private CurrentScheduleViewModel model;

    private Handler scheduler = new Handler();

    private Consumer<String> onJsonReceivedCallback;

    private Integer commandId;

    private Gson gson = new Gson();

    private Switch swEnable;
    private TextView tvCurrTemperature;
    private TextView tvHeatingPower;
    private SeekBar sbDstTemperature;
    private EditText etDstTemperature;
    private Switch swMotorEnable;
    private Switch swEnableTemperatureAlarm;
    private EditText edPowerTemperatureCorrelation;
    private EditText edMaxPower;
    private LinearLayout llWaiting;
    private LinearLayout llMain;

    boolean enableChanged;
    boolean dstTemperatureChanged;
    boolean powerTemperatureCorrelationChanged;
    boolean maxPowerChanged;
    boolean motorEnableChanged;
    boolean temperatureAlarmChanged;

    public BrewingFragment(CurrentScheduleViewModel model) {
        this.model = model;
    }

    public static BrewingFragment newInstance(CurrentScheduleViewModel model) {
        return new BrewingFragment(model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = buildViews(inflater, container);

        getActivity().setTitle("Warzenie");

        onJsonReceivedCallback = jsonText -> {
            if (commandId != null) {

                ResponseDTO abstractResponse = gson.fromJson(jsonText, ResponseDTO.class);
                if (abstractResponse.getCommandId().equals(commandId)) {
                    commandId = null;
                    BrewingStatusResponse response = gson.fromJson(jsonText, BrewingStatusResponse.class);

                    mapResponseToView(response);
                }
            }
        };
        model.getDeviceConnectivity().getDeviceService().addResponseListener(onJsonReceivedCallback);

        scheduler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scheduler != null) {
                    commandId = model.getDeviceConnectivity().getDeviceService().getBrewingState();
                    scheduler.postDelayed(this, 1000);
                }
            }
        }, 1000);


        return currentView;
    }

    private void mapResponseToView(BrewingStatusResponse response) {
        llWaiting.setVisibility(View.GONE);
        llMain.setVisibility(View.VISIBLE);

        if (enableChanged == false) {
            swEnable.setChecked(response.getBrewingState().isEnabled());
            enableChanged = true;
        }

        Float currentTemperature = response.getBrewingState().getCurrentTemperature();
        if (currentTemperature != null) {
            tvCurrTemperature.setText(((Math.round(currentTemperature * 100.0)) / 100.0) + "°C");
        } else {
            tvCurrTemperature.setText("B/D");
        }

        Integer heatingPower = response.getBrewingState().getHeatingPower();
        if (heatingPower == null) {
            heatingPower = 0;
        }
        tvHeatingPower.setText(heatingPower + "%");


        if (dstTemperatureChanged == false) {
            Float destinationTemperature = response.getBrewingState().getDestinationTemperature();
            if (destinationTemperature != null) {
                sbDstTemperature.setProgress(destinationTemperature.intValue());
            } else {
                etDstTemperature.setText("");
            }
            dstTemperatureChanged = true;
        }

        if (powerTemperatureCorrelationChanged == false) {
            edPowerTemperatureCorrelation.setText(String.valueOf(response.getBrewingState().getPowerTemperatureCorrelation()));
            powerTemperatureCorrelationChanged = true;
        }

        if (maxPowerChanged == false) {
            edMaxPower.setText(String.valueOf(response.getBrewingState().getMaxPower()));
            maxPowerChanged = true;
        }

        if (motorEnableChanged == false) {
            swMotorEnable.setChecked(response.getBrewingState().isMotorEnabled());
            enableChanged = true;
        }
        if (temperatureAlarmChanged == false) {
            swEnableTemperatureAlarm.setChecked(response.getBrewingState().isTemperatureAlarm());
            temperatureAlarmChanged = true;
        }
    }


    private View buildViews(LayoutInflater inflater, ViewGroup container) {
        View currentView = inflater.inflate(R.layout.fragment_brewing, container, false);

        llWaiting = currentView.findViewById(R.id.llWaiting);
        llMain = currentView.findViewById(R.id.llMain);

        swEnable = currentView.findViewById(R.id.swEnable);
        swEnable.setOnCheckedChangeListener((buttonView, isChecked) -> enable(isChecked));

        tvCurrTemperature = currentView.findViewById(R.id.tvCurrTemperature);
        tvHeatingPower = currentView.findViewById(R.id.tvHeatingPower);
        sbDstTemperature = currentView.findViewById(R.id.sbDstTemperature);
        sbDstTemperature.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Integer value = Integer.valueOf(progress);
                if (!etDstTemperature.getText().toString().equals(value.toString())) {
                    etDstTemperature.setText(value.toString());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        etDstTemperature = currentView.findViewById(R.id.etDstTemperature);
        etDstTemperature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (dstTemperatureChanged) {
                    Float value = null;
                    try {
                        value = Float.valueOf(s.toString());
                    } catch (NumberFormatException nfe) {
                    }
                    if (value != null) {
                        sbDstTemperature.setProgress(value.intValue());
                    }
                    setDstTemperature(value);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etDstTemperature.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            try {
                Float input = Float.parseFloat(dest.toString() + source.toString());
                if (input >= 0 && input <= 100) {
                    return null;
                }
            } catch (NumberFormatException nfe) {
            }
            return "";
        }});

        swMotorEnable = currentView.findViewById(R.id.swMotorEnable);
        swMotorEnable.setOnCheckedChangeListener((buttonView, isChecked) -> motorEnable(isChecked));

        swEnableTemperatureAlarm = currentView.findViewById(R.id.swEnableTemperatureAlarm);
        swEnableTemperatureAlarm.setOnCheckedChangeListener((buttonView, isChecked) -> enableTemperatureAlarm(isChecked));

        edPowerTemperatureCorrelation = currentView.findViewById(R.id.edPowerTemperatureCorrelation);
        edPowerTemperatureCorrelation.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (powerTemperatureCorrelationChanged) {
                    Float value = null;
                    try {
                        value = Float.valueOf(s.toString());
                    } catch (NumberFormatException nfe) {
                    }
                    setPowerTemperatureCorrelation(value);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edPowerTemperatureCorrelation.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            try {
                Float input = Float.parseFloat(dest.toString() + source.toString());
                if (input >= 0 && input <= 100) {
                    return null;
                }
            } catch (NumberFormatException nfe) {
            }
            return "";
        }});

        edMaxPower = currentView.findViewById(R.id.edMaxPower);
        edMaxPower.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (maxPowerChanged) {
                    Integer value = null;
                    try {
                        value = Integer.valueOf(s.toString());
                    } catch (NumberFormatException nfe) {
                    }
                    setMaxPower(value);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edMaxPower.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            try {
                Integer input = Integer.parseInt(dest.toString() + source.toString());
                if (input >= 0 && input <= 100) {
                    return null;
                }
            } catch (NumberFormatException nfe) {
            }
            return "";
        }});

        return currentView;
    }

    private void setDstTemperature(Float value) {
        model.getDeviceConnectivity().getDeviceService().Brewing_setDestinationTemperature(value);
    }

    private void enable(boolean enable) {
        model.getDeviceConnectivity().getDeviceService().Brewing_enable(enable);
    }



    private void motorEnable(boolean enable) {
        model.getDeviceConnectivity().getDeviceService().Brewing_motorEnable(enable);
    }

    private void enableTemperatureAlarm(boolean enable) {
        model.getDeviceConnectivity().getDeviceService().Brewing_enableTemperatureAlarm(enable);
    }

    private void setMaxPower(Integer value) {
        model.getDeviceConnectivity().getDeviceService().Brewing_setMaxPower(value);
    }

    private void setPowerTemperatureCorrelation(Float value) {
        model.getDeviceConnectivity().getDeviceService().Brewing_setPowerTemperatureCorrelation(value);
    }



    @Override
    public void onDetach() {
        scheduler = null;
        model.getDeviceConnectivity().getDeviceService().removeResponseListener(onJsonReceivedCallback);
        super.onDetach();
    }


}
