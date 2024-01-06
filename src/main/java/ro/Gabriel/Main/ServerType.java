package ro.Gabriel.Main;

import ro.Gabriel.Managers.ManagerClass;

public enum ServerType {
    LOBBY(true, false),
    ARENA(false, true),
    MULTI_ARENA(true, true);

    private final boolean lobby, arena;

    ServerType(boolean lobby, boolean arena) {
        this.lobby = lobby;
        this.arena = arena;
    }

    public boolean isLobby() {
        return this.lobby;
    }

    public boolean isArena() {
        return arena;
    }

    public boolean match(Minigame minigame, ManagerClass classTag) {
        return match(minigame, classTag.bungee(), classTag.lobby(), classTag.arena());
    }

    /*public boolean match(PlaceholderClass classTag) {
        return match(classTag.bungee(), classTag.lobby(), classTag.arena());
    }*/

    public boolean match(Minigame minigame, boolean bungee, boolean lobby, boolean arena) {
        return (!bungee && this == MULTI_ARENA)
                || (bungee == minigame.isBungeeCord() && lobby && arena)
                || (minigame.isBungeeCord() == bungee && this.isLobby() == lobby && this.isArena() == arena)
                ||(bungee && lobby && arena);
    }

    public static ServerType of(Object value) {
        try {
            return value instanceof String ? ServerType.valueOf((String)value) : ServerType.MULTI_ARENA;
        } catch (Exception e) {
            return ServerType.MULTI_ARENA;
        }
    }
}