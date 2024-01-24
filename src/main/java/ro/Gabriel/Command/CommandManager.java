package ro.Gabriel.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import ro.Gabriel.Class.Validators.CommandGroupValidator;
import ro.Gabriel.Command.Annotations.CommandClass;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Main.Minigame;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    public CommandManager(Minigame plugin) {
        DataStorage commandsData = DataStorage.getStorage(plugin, "commands", true);

        commandsData.getKeys(false).forEach(groupId -> {
            Object aliases = commandsData.get(groupId + ".aliases");

            CommandGroup commandGroup = new CommandGroup(plugin, groupId, (aliases instanceof List ? (List<String>) aliases : new ArrayList<>()), commandsData.getString(groupId + ".permission"));

            DataStorage commands = commandsData.getSection(groupId + ".commands");

            plugin.searchClasses(CommandGroupValidator.class, groupId).forEach(clazz -> {
                CommandClass commandClass = clazz.getAnnotation(CommandClass.class);

                String syntax, permission;
                CommandExecutorType executorType;

                if((syntax = (String) commands.get(commandClass.id() + ".syntax")) == null) {
                    syntax = commandClass.syntax();
                }

                if((permission = (String) commands.get(commandClass.id() + ".permission")) == null) {
                    permission = commandClass.permission();
                }

                if((executorType = CommandExecutorType.getExecutor((String) commands.get(commandClass.id() + ".executor"))) == null) {
                    executorType = commandClass.executorType();
                }

                try {
                    CustomCommand customCommand = (CustomCommand) ReflectionUtils.instantiateObject(clazz, true, null);

                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "id", commandClass.id());
                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "syntax", syntax);
                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "description", commandClass.defaultDescription());
                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "executorType", executorType);
                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "permission", permission);
                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "group", commandGroup);

                    boolean isClickable = commands.getBoolean(commandClass.id() + ".clickable");
                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "clickable", isClickable);

                    if(isClickable) {
                        plugin.getLanguageManager().getMessageManager().addHoverText(commandClass.id(), (commandGroup.getUsage() + groupId + " " + syntax), "commands." + groupId + "." + commandClass.id() + ".hover-text", commandGroup.getUsage() + groupId + " " + syntax, !CommandUtils.isParametricCommand(syntax));
                    }

                    ReflectionUtils.setValue(customCommand, CustomCommand.class, true, "args", syntax.replace((commandGroup.getUsage() + groupId + " "), "").split(" "));

                    commandGroup.getCommands().add(customCommand);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            try {
                CommandMap commandMap = (CommandMap) ReflectionUtils.getValue(Bukkit.getServer(), ReflectionUtils.PackageType.CRAFTBUKKIT.getClass("CraftServer"), true, "commandMap");
                commandMap.register(plugin.getName(), commandGroup);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}