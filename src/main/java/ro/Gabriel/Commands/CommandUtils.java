package ro.Gabriel.Commands;

import java.util.stream.Collectors;
import java.util.List;

public class CommandUtils {
    public static List<CustomCommand> getCommandsByArgs(String[] typedArgs, CommandGroup commandGroup) {
        List<CustomCommand> commands = commandGroup.getCommands();

        for(int i = 0; i < typedArgs.length; i++) {
            int finalI = i;
            List<CustomCommand> newCommands = commands.stream().filter(command ->
                command.getArgs().length > finalI
                        && (command.getArgs()[finalI].equals(typedArgs[finalI]) || command.getArgs()[finalI].startsWith(typedArgs[finalI])
                        || (command.getArgs()[finalI].startsWith("<") && command.getArgs()[finalI].endsWith(">"))
                )
            ).collect(Collectors.toList());

            if(newCommands.size() > 0) {
                commands = newCommands;
            } else {
                break;
            }
        }
        return commands;
    }

    public static boolean isParametricCommand(String syntax) {
        return syntax.contains("<") && syntax.contains(">");
    }
}