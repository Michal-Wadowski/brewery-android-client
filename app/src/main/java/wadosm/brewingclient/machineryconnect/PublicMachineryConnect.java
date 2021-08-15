package wadosm.brewingclient.machineryconnect;

import wadosm.brewingclient.common.CommonViewModelInteraction;
import wadosm.brewingclient.machineryconnect.model.MachineryConnectVDO;

public interface PublicMachineryConnect extends CommonViewModelInteraction {

    void updateMachineryConnect(MachineryConnectVDO machineryConnectVDO);

}
