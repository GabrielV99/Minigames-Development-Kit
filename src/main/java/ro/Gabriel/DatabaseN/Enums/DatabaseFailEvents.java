package ro.Gabriel.DatabaseN.Enums;

import ro.Gabriel.DatabaseN.Database;

public enum DatabaseFailEvents {
    CONNECT_TO_DEFAULT_STOP, CONNECT_TO_DEFAULT_DISABLE, STOP, DISABLE, CONTINUE;

    public static DatabaseFailEvents fromInt(int id) {
        return id == 0 ? DatabaseFailEvents.CONNECT_TO_DEFAULT_STOP
                : id == 1 ? DatabaseFailEvents.CONNECT_TO_DEFAULT_DISABLE
                : id == 2 ? DatabaseFailEvents.STOP
                : id == 3 ? DatabaseFailEvents.DISABLE : CONTINUE;
    }

    public static DatabaseFailEvents get(Object event) {
        if(event instanceof String) {
            DatabaseFailEvents[] events = DatabaseFailEvents.values();
            String e = ((String)event).toUpperCase();
            for(DatabaseFailEvents failEvent : events) {
                if(failEvent.name().toUpperCase().equals(e)) {
                    return failEvent;
                }
            }
        } else if(event instanceof Integer) {
            return fromInt((int) event);
        }

        return CONTINUE;
    }
}
