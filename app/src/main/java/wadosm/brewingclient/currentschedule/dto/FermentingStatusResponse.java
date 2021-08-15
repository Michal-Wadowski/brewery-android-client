package wadosm.brewingclient.currentschedule.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FermentingStatusResponse extends ResponseDTO {

    private final FermentingState fermentingState;

    public FermentingStatusResponse(Integer commandId, Long time, FermentingState fermentingState) {
        super(commandId, time);
        this.fermentingState = fermentingState;
    }
}