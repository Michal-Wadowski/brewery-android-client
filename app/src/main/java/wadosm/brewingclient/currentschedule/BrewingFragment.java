package wadosm.brewingclient.currentschedule;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.brewingclient.R;
import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.currentschedule.dto.BrewingStatusResponse;

@AndroidEntryPoint
public class BrewingFragment extends Fragment {

    private CurrentScheduleViewModel model;

    private Handler scheduler = new Handler();

    private Consumer<String> onJsonReceivedCallback;

    private Consumer<Throwable> onErrorCallback;

    private Integer commandId;

    private Gson gson = new Gson();

    private Switch swEnable;
    private TextView tvCurrTemperature;
    private TextView tvHeatingPower;
    private EditText etDstTemperature;
    private Switch swMotorEnable;
    private Switch swEnableTemperatureAlarm;
    private EditText edPowerTemperatureCorrelation;
    private EditText edMaxPower;
    private LinearLayout llWaiting;
    private LinearLayout llMain;

    private Switch swEnableCalibration;
    private LinearLayout llCalibration;
    private RadioButton rbCalibrationTop;
    private RadioButton rbCalibrationBottom;
    private LinearLayout llCalibrationValue;
    private EditText edCurrCalibration;

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
            try {
                BrewingStatusResponse response = gson.fromJson(jsonText, BrewingStatusResponse.class);
                mapResponseToView(response);
            } catch (Exception ignored) {}
        };
        model.getDeviceConnectivity().getDeviceService().addResponseListener(onJsonReceivedCallback);

        scheduler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scheduler != null) {
                    if (model.getDeviceConnectivity().isConnected()) {
                        commandId = model.getDeviceConnectivity().getDeviceService().getBrewingState();
                        scheduler.postDelayed(this, 200);
                    }
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
            etDstTemperature.setText(String.valueOf(destinationTemperature));
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
            motorEnableChanged = true;
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

        llCalibration = currentView.findViewById(R.id.llCalibration);
        swEnableCalibration = currentView.findViewById(R.id.swEnableCalibration);
        swEnableCalibration.setOnCheckedChangeListener((buttonView, isChecked) -> llCalibration.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        llCalibrationValue = currentView.findViewById(R.id.llCalibrationValue);
        edCurrCalibration = currentView.findViewById(R.id.edCurrCalibration);

        rbCalibrationTop = currentView.findViewById(R.id.rbCalibrationTop);
        rbCalibrationTop.setOnCheckedChangeListener((buttonView, isChecked) -> {
            llCalibrationValue.setVisibility(View.VISIBLE);
            edCurrCalibration.setText("");
        });
        rbCalibrationBottom = currentView.findViewById(R.id.rbCalibrationBottom);
        rbCalibrationBottom.setOnCheckedChangeListener((buttonView, isChecked) -> {
            llCalibrationValue.setVisibility(View.VISIBLE);
            edCurrCalibration.setText("");
        });








        edCurrCalibration.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (maxPowerChanged) {
                    Float value = null;
                    try {
                        value = Float.valueOf(s.toString());
                    } catch (NumberFormatException nfe) {
                    }
                    Integer side = null;
                    if (rbCalibrationTop.isChecked()) {
                        side = 1;
                    }
                    if (rbCalibrationBottom.isChecked()) {
                        side = 0;
                    }
                    if (side != null) {
                        setCalibrationValue(side, value);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edCurrCalibration.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
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

    private void setCalibrationValue(Integer side, Float value) {
        model.getDeviceConnectivity().getDeviceService().Brewing_setCalibrationValue(side, value);
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
