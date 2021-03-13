package wadosm.bluetooth.main;

import androidx.fragment.app.Fragment;

import wadosm.bluetooth.machineryconnect.MachineryConnectFragment;

public class FragmentFactory {

    public Fragment getMachineryConnectFragment() {
        return MachineryConnectFragment.newInstance();
    }

}
