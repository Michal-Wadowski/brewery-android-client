package wadosm.brewingclient.currentschedule;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import dagger.hilt.android.AndroidEntryPoint;
import wadosm.brewingclient.R;
import wadosm.brewingclient.common.Consumer;
import wadosm.brewingclient.currentschedule.dto.FermentingStatusResponse;
import wadosm.brewingclient.currentschedule.dto.ResponseDTO;

@AndroidEntryPoint
public class FermentingFragment extends Fragment {

    private CurrentScheduleViewModel model;

    private Handler scheduler = new Handler();

    private Consumer<String> onJsonReceivedCallback;

    private Integer commandId;

    private Gson gson = new Gson();

    private Switch swEnable;
    private TextView tvCurrTemperature;
    private EditText etDstTemperature;
    private ImageView ivHeating;
    private LinearLayout llWaiting;
    private LinearLayout llMain;

    boolean enableChanged;
    boolean dstTemperatureChanged;

    public FermentingFragment(CurrentScheduleViewModel model) {
        this.model = model;
    }

    public static FermentingFragment newInstance(CurrentScheduleViewModel model) {
        return new FermentingFragment(model);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = buildViews(inflater, container);

        getActivity().setTitle("Fermentacja");

        onJsonReceivedCallback = jsonText -> {
            if (commandId != null) {

                ResponseDTO abstractResponse = gson.fromJson(jsonText, ResponseDTO.class);
                if (abstractResponse.getCommandId().equals(commandId)) {
                    commandId = null;
                    FermentingStatusResponse response = gson.fromJson(jsonText, FermentingStatusResponse.class);

                    mapResponseToView(response);
                }
            }
        };
        model.getDeviceConnectivity().getDeviceService().addResponseListener(onJsonReceivedCallback);

        scheduler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (scheduler != null) {
                    commandId = model.getDeviceConnectivity().getDeviceService().getFermentingState();
                    scheduler.postDelayed(this, 1000);
                }
            }
        }, 1000);


        return currentView;
    }

    private void mapResponseToView(FermentingStatusResponse response) {
        llWaiting.setVisibility(View.GONE);
        llMain.setVisibility(View.VISIBLE);

        if (enableChanged == false) {
            swEnable.setChecked(response.getFermentingState().isEnabled());
            enableChanged = true;
        }

        Float currentTemperature = response.getFermentingState().getCurrentTemperature();
        if (currentTemperature != null) {
            tvCurrTemperature.setText(((Math.round(currentTemperature * 100.0)) / 100.0) + "°C");
        } else {
            tvCurrTemperature.setText("B/D");
        }


        if (dstTemperatureChanged == false) {
            Float destinationTemperature = response.getFermentingState().getDestinationTemperature();
            etDstTemperature.setText(String.valueOf(destinationTemperature));
            dstTemperatureChanged = true;
        }

        Drawable drawable;
        if (response.getFermentingState().isHeating()) {
            drawable = getResources().getDrawable(android.R.drawable.presence_online);
        } else {
            drawable = getResources().getDrawable(android.R.drawable.presence_invisible);
        }
        ivHeating.setImageDrawable(drawable);
    }


    private View buildViews(LayoutInflater inflater, ViewGroup container) {
        View currentView = inflater.inflate(R.layout.fragment_fermenting, container, false);

        llWaiting = currentView.findViewById(R.id.llWaiting);
        llMain = currentView.findViewById(R.id.llMain);

        swEnable = currentView.findViewById(R.id.swEnable);
        swEnable.setOnCheckedChangeListener((buttonView, isChecked) -> setEnabled(isChecked));

        tvCurrTemperature = currentView.findViewById(R.id.tvCurrTemperature);

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
        ivHeating = currentView.findViewById(R.id.ivHeating);


        return currentView;
    }

    private void setDstTemperature(Float value) {
        model.getDeviceConnectivity().getDeviceService().Fermenting_setDestinationTemperature(value);
    }

    private void setEnabled(boolean enable) {
        model.getDeviceConnectivity().getDeviceService().Fermenting_enable(enable);
    }

    @Override
    public void onDetach() {
        scheduler = null;
        model.getDeviceConnectivity().getDeviceService().removeResponseListener(onJsonReceivedCallback);
        super.onDetach();
    }


}
