package wadosm.bluetooth;

import androidx.fragment.app.Fragment;

import wadosm.bluetooth.currentschedule.CurrentScheduleFragment;
import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;

public class FragmentFactory {

    public Fragment getMachineryConnectFragment() {
        return MachineryConnectFragment.newInstance();
    }

    public Fragment getCurrentScheduleFragment() {
        return CurrentScheduleFragment.newInstance();
    }

}
