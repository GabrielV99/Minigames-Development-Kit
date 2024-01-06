package ro.Gabriel.Titles;

public interface TitleSender {
    void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

    default void sendTitle(String title) {
        this.sendTitle(title, null, 10, 70, 20);
    }

    default void sendTitle(String title, String subtitle) {
        this.sendTitle(title, subtitle, 10, 70, 20);
    }

    default void sendTitle(String title, int stay) {
        this.sendTitle(title, null, 10, stay, 20);
    }

    default void sendTitle(String title, String subtitle, int stay) {
        this.sendTitle(title, subtitle, 10, stay, 20);
    }
}