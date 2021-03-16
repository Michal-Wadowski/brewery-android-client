package wadosm.bluetooth.machineryconnect;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MachineryConnectVDO {

    MessageBoxVDO messageBox;

    boolean connectButtonEnable;
}
