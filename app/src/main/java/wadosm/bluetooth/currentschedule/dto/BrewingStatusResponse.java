package wadosm.bluetooth.currentschedule.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrewingStatusResponse extends ResponseDTO {

    private final BrewingState brewingState;

    public BrewingStatusResponse(Integer commandId, Long time, BrewingState brewingState) {
        super(commandId, time);
        this.brewingState = brewingState;
    }
}