package wadosm.bluetooth.machineryconnect;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class MessageBoxContent {

    private Integer stringId;

    private String customText;

    public MessageBoxContent(Integer stringId) {
        this.stringId = stringId;
    }

    public MessageBoxContent(String customText) {
        this.customText = customText;
    }
}
