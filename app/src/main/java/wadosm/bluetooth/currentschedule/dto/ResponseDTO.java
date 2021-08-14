package wadosm.bluetooth.currentschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
// TODO: Add command status (OK/FAIL)
public class ResponseDTO {

    private Integer commandId;

    private Long time;

}
