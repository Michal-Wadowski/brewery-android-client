package wadosm.bluetooth.connectivity.real.dto;

import lombok.Getter;

@Getter
public class CommandDTO {

    private String command;

    private Boolean enable;

    private Integer number;

    private Integer value;

    public static CommandDTO powerEnable(boolean enable) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = "powerEnable";
        commandDTO.enable = enable;
        return commandDTO;
    }

    public static CommandDTO motorEnable(int motorNumber, boolean enable) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = "motorEnable";
        commandDTO.enable = enable;
        commandDTO.number = motorNumber;
        return commandDTO;
    }

    public static CommandDTO playSound(int period) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = "playSound";
        commandDTO.value = period;
        return commandDTO;
    }

    public static CommandDTO setMainsPower(int mainsNumber, int power) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = "setMainsPower";
        commandDTO.value = power;
        commandDTO.number = mainsNumber;
        return commandDTO;
    }
}
