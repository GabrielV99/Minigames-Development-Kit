package ro.Gabriel.Titles;

import ro.Gabriel.Language.LanguagePath;

public interface TitleSender {
    void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut);

    /*default void sendTitle(String title) {
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
    }*/


    default void sendTitle(String title) {
        this.sendTitle(title, 70);
    }
    default void sendSubtitle(String subtitle) {
        this.sendSubtitle(subtitle, 70);
    }
    default void sendTitle(String title, String subtitle) {
        this.sendTitle(title, subtitle, 70);
    }

    default void sendTitle(String title, int stay) {
        this.sendTitle(title, null, 10, stay, 20);
    }
    default void sendSubtitle(String subtitle, int stay) {
        this.sendTitle(null, subtitle, 10, stay, 20);
    }
    default void sendTitle(String title, String subtitle, int stay) {
        this.sendTitle(title, subtitle, 10, stay, 20);
    }

    default void sendTitle(LanguagePath title) {
        this.sendTitle(title, 70);
    }
    default  void sendSubtitle(LanguagePath subtitle) {
        this.sendSubtitle(subtitle, 70);
    }
    default void sendTitle(LanguagePath title, LanguagePath subtitle) {
        this.sendTitle(title, subtitle, 70);
    }

    default void sendTitle(LanguagePath title, int stay) {
        this.sendTitle(title, null, stay);
    }
    default void sendSubtitle(LanguagePath subtitle, int stay) {
        this.sendSubtitle(subtitle, null, stay);
    }
    default void sendTitle(LanguagePath title, LanguagePath subtitle, int stay) {
        this.sendTitle(title, subtitle, null, stay);
    }

    default void sendTitle(LanguagePath title, Object replacement) {
        this.sendTitle(title, replacement, 70);
    }
    default void sendSubtitle(LanguagePath subtitle, Object replacement) {
        this.sendSubtitle(subtitle, replacement, 70);
    }
    default void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement) {
        this.sendTitle(title, subtitle, replacement, 70);
    }

    default void sendTitle(LanguagePath title, Object replacement, int stay) {
        this.sendTitle(title, null, replacement, stay);
    }
    default void sendSubtitle(LanguagePath subtitle, Object replacement, int stay) {
        this.sendTitle(null, subtitle, replacement, stay);
    }
    void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement, int stay);



    /*

    void sendTitle(String title);
    void sendSubtitle(String subtitle);
    void sendTitle(String title, String subtitle);

    void sendTitle(String title, int stay);
    void sendSubtitle(String subtitle, int stay);
    void sendTitle(String title, String subtitle, int stay);

    void sendTitle(LanguagePath title);
    void sendSubtitle(LanguagePath subtitle);
    void sendTitle(LanguagePath title, LanguagePath subtitle);

    void sendTitle(LanguagePath title, int stay);
    void sendSubtitle(LanguagePath subtitle, int stay);
    void sendTitle(LanguagePath title, LanguagePath subtitle, int stay);

    void sendTitle(LanguagePath title, Object replacement);
    void sendSubtitle(LanguagePath subtitle, Object replacement);
    void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement);

    void sendTitle(LanguagePath title, Object replacement, int stay);
    void sendSubtitle(LanguagePath subtitle, Object replacement, int stay);
    void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement, int stay);




















    void sendTitle(String title);
    void sendSubtitle(String subtitle);

    void sendTitle(String title, int stay);
    void sendSubtitle(String subtitle, int stay);


    void sendTitle(LanguagePath category, Object replacement);
    void sendSubtitle(LanguagePath category, Object replacement);
    void sendTitle(LanguagePath category);
    void sendSubtitle(LanguagePath category);

    void sendTitle(LanguagePath category, Object replacement, int stay);
    void sendSubtitle(LanguagePath category, Object replacement, int stay);
    void sendTitle(LanguagePath category, int stay);
    void sendSubtitle(LanguagePath category, int stay);


    =========================================================


    void sendTitle(String title, String subtitle);
    void sendTitle(String title, String subtitle, int stay);

    void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement);
    void sendTitle(LanguagePath title, LanguagePath subtitle);

    void sendTitle(LanguagePath title, LanguagePath subtitle, Object replacement, int stay);
    void sendTitle(LanguagePath title, LanguagePath subtitle, int stay);










    void sendMessage(String message);

    void sendMessage(LanguagePath category, Object replacement);
    void sendMessage(LanguagePath category);



    */
}