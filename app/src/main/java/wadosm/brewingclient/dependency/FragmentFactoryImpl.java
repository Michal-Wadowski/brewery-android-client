package wadosm.brewingclient.dependency;

import androidx.fragment.app.Fragment;

import wadosm.brewingclient.currentschedule.CurrentScheduleFragment;
import wadosm.brewingclient.machineryconnect.MachineryConnectFragment;

public class FragmentFactoryImpl implements FragmentFactory {

    public Fragment getMachineryConnectFragment() {
        return MachineryConnectFragment.newInstance();
    }

    public Fragment getCurrentScheduleFragment() {
        return CurrentScheduleFragment.newInstance();
    }

}
