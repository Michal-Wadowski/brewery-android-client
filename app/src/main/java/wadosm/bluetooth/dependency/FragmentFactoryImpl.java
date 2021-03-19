package wadosm.bluetooth.dependency;

import androidx.fragment.app.Fragment;

import wadosm.bluetooth.currentschedule.CurrentScheduleFragment;
import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;

public class FragmentFactoryImpl implements FragmentFactory {

    public Fragment getMachineryConnectFragment() {
        return MachineryConnectFragment.newInstance();
    }

    public Fragment getCurrentScheduleFragment() {
        return CurrentScheduleFragment.newInstance();
    }

}
