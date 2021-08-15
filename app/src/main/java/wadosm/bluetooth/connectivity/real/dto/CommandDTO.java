package wadosm.bluetooth.connectivity.real.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
public class CommandDTO {

    private Integer commandId;

    private Command command;

    private Boolean enable;

    private Integer number;

    private Integer intValue;

    private Float floatValue;

    public static CommandDTO Fermenting_setDestinationTemperature(int commandId, Float value) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Fermenting_setDestinationTemperature;
        commandDTO.commandId = commandId;
        commandDTO.floatValue = value;
        return commandDTO;
    }

    public static CommandDTO Fermenting_enable(int commandId, boolean enable) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Fermenting_enable;
        commandDTO.commandId = commandId;
        commandDTO.enable = enable;
        return commandDTO;
    }

    public static CommandDTO Fermenting_getFermentingState(int commandId) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Fermenting_getFermentingState;
        commandDTO.commandId = commandId;
        return commandDTO;
    }

    public static CommandDTO Power_powerOff() {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Power_powerOff;
        return commandDTO;
    }

    public static CommandDTO Power_restart() {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Power_restart;
        return commandDTO;
    }

    public static CommandDTO Brewing_setDestinationTemperature(int commandId, Float value) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_setDestinationTemperature;
        commandDTO.commandId = commandId;
        commandDTO.floatValue = value;
        return commandDTO;
    }

    public static CommandDTO Brewing_enable(int commandId, boolean enable) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_enable;
        commandDTO.commandId = commandId;
        commandDTO.enable = enable;
        return commandDTO;
    }

    public static CommandDTO Brewing_getBrewingState(int commandId) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_getBrewingState;
        commandDTO.commandId = commandId;
        return commandDTO;
    }

    public static Object Brewing_motorEnable(int commandId, boolean enable) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_motorEnable;
        commandDTO.commandId = commandId;
        commandDTO.enable = enable;
        return commandDTO;
    }

    public static Object Brewing_enableTemperatureAlarm(int commandId, boolean enable) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_enableTemperatureAlarm;
        commandDTO.commandId = commandId;
        commandDTO.enable = enable;
        return commandDTO;
    }

    public static Object Brewing_setMaxPower(int commandId, Integer value) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_setMaxPower;
        commandDTO.commandId = commandId;
        commandDTO.intValue = value;
        return commandDTO;
    }

    public static Object Brewing_setPowerTemperatureCorrelation(int commandId, Float value) {
        CommandDTO commandDTO = new CommandDTO();
        commandDTO.command = Command.Brewing_setMaxPower;
        commandDTO.commandId = commandId;
        commandDTO.floatValue = value;
        return commandDTO;
    }

    @Getter
    @ToString
    public enum Command {
        @SerializedName("Power.powerOff")
        Power_powerOff,
        @SerializedName("Power.restart")
        Power_restart,

        @SerializedName("Fermenting.getFermentingState")
        Fermenting_getFermentingState,
        @SerializedName("Fermenting.setDestinationTemperature")
        Fermenting_setDestinationTemperature,
        @SerializedName("Fermenting.enable")
        Fermenting_enable,

        @SerializedName("Brewing.getBrewingState")
        Brewing_getBrewingState,
        @SerializedName("Brewing.setDestinationTemperature")
        Brewing_setDestinationTemperature,
        @SerializedName("Brewing.setMaxPower")
        Brewing_setMaxPower,
        @SerializedName("Brewing.setPowerTemperatureCorrelation")
        Brewing_setPowerTemperatureCorrelation,
        @SerializedName("Brewing.enableTemperatureAlarm")
        Brewing_enableTemperatureAlarm,
        @SerializedName("Brewing.enable")
        Brewing_enable,
        @SerializedName("Brewing.setTimer")
        Brewing_setTimer,
        @SerializedName("Brewing.removeTimer")
        Brewing_removeTimer,
        @SerializedName("Brewing.motorEnable")
        Brewing_motorEnable
    }
}
