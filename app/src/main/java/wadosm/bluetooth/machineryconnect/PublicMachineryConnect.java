package wadosm.bluetooth.machineryconnect;

import wadosm.bluetooth.common.CommonViewModelInteraction;
import wadosm.bluetooth.machineryconnect.model.MachineryConnectVDO;

public interface PublicMachineryConnect extends CommonViewModelInteraction {

    void updateMachineryConnect(MachineryConnectVDO machineryConnectVDO);

}
