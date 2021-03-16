package wadosm.bluetooth.machineryconnect;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MessageBoxVDO {

    private Integer stringId;

    private String customText;

    public MessageBoxVDO(Integer stringId) {
        this.stringId = stringId;
    }

    public MessageBoxVDO(String customText) {
        this.customText = customText;
    }
}
