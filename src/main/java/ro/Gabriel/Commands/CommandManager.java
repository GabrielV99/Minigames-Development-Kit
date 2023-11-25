package ro.Gabriel.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import ro.Gabriel.Class.Validators.CommandGroupValidator;
import ro.Gabriel.Managers.Annotations.ManagerClass;
import ro.Gabriel.Commands.Annotations.CommandClass;
import ro.Gabriel.Storage.DataStorage.DataStorage;
import ro.Gabriel.Messages.MessagesManager;
import ro.Gabriel.Misc.ReflectionUtils;
import ro.Gabriel.Class.ClassManager;
import ro.Gabriel.BuildBattle.Main;

import java.util.*;

@ManagerClass(bungee = true, lobby = true, arena = true, managersBefore = { MessagesManager.class })
public class CommandManager {

    private static CommandManager INSTANCE;

    @SuppressWarnings("unchecked")
    public static void load() {
        DataStorage commandsData = DataStorage.getStorage("commands", true);

        commandsData.getKeys(false).forEach(groupId -> {
            Object aliases = commandsData.get(groupId + ".aliases");

            CommandGroup commandGroup = new CommandGroup(groupId, (aliases instanceof List ? (List<String>) aliases : new ArrayList<>()), commandsData.getString(groupId + ".permission"));

            DataStorage commands = commandsData.getSection(groupId + ".commands");

            ClassManager.searchClasses(CommandGroupValidator.class, groupId).forEach(clazz -> {
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
                    CustomCommand customCommand = (CustomCommand) ReflectionUtils.instantiateObject(clazz, true);

                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "id", commandClass.id());
                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "syntax", syntax);
                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "description", commandClass.defaultDescription());
                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "executorType", executorType);
                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "permission", permission);
                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "group", commandGroup);

                    boolean isClickable = commands.getBoolean(commandClass.id() + ".clickable");
                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "clickable", isClickable);

                    if(isClickable) {
                        MessagesManager.registerHoverText(commandClass.id(), (commandGroup.getUsage() + groupId + " " + syntax), "commands." + groupId + "." + commandClass.id() + ".hover-text", commandGroup.getUsage() + groupId + " " + syntax, !CommandUtils.isParametricCommand(syntax));
                    }

                    ReflectionUtils.setValue(customCommand, true, CustomCommand.class, "args", syntax.replace((commandGroup.getUsage() + groupId + " "), "").split(" "));

                    commandGroup.getCommands().add(customCommand);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            try {
                CommandMap commandMap = (CommandMap) ReflectionUtils.getValue(Bukkit.getServer(), true, ReflectionUtils.getCBClass(ReflectionUtils.PackageType.CRAFTBUKKIT, "CraftServer"), "commandMap");
                commandMap.register(Main.getPluginName(), commandGroup);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static CommandManager getInstance() {
        synchronized (CommandManager.class) {
            if (INSTANCE == null) {
                INSTANCE = new CommandManager();
            }
        }
        return INSTANCE;
    }
}