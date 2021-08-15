package wadosm.brewingclient.dependency;

import androidx.fragment.app.Fragment;

public interface FragmentFactory {

    Fragment getMachineryConnectFragment();

    Fragment getCurrentScheduleFragment();

}
