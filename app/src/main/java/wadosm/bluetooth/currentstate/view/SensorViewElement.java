package wadosm.bluetooth.currentstate.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import wadosm.bluetooth.R;
import wadosm.bluetooth.currentstate.model.StateElementVDO;

public class SensorViewElement extends LinearLayout {

    TextView sensorName;
    TextView sensorValue;

    public SensorViewElement(Context context, StateElementVDO elementVDO) {
        super(context, null);

        initControlView(context);

        setupControl(elementVDO);
    }

    private void setupControl(StateElementVDO elementVDO) {
        sensorName.setText(elementVDO.getName() + ":");
        sensorValue.setText(elementVDO.getValue());
    }

    private void initControlView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.sensor_view_element, this, true);

        sensorName = this.findViewById(R.id.sensorName);
        sensorValue = this.findViewById(R.id.sensorValue);
    }
}
