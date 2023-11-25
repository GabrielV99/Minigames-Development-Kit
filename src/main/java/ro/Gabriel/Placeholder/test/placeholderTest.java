package ro.Gabriel.Placeholder.test;

import org.bukkit.entity.Player;
import ro.Gabriel.BuildBattle.Main;

public class placeholderTest {

    public static <T> String applyPlaceholders(Player source, String text, Placeholder<T>... placeholders) {
        for (Placeholder<T> placeholder : placeholders) {
            if (source != null) {
                text = text.replace(placeholder.getPlaceholder(), placeholder.apply((T)source));
            }
        }
        return text;
    }

    public static void test(Player source, String text) {
        Placeholder<Player> namePlaceholder = new Placeholder<>("{name}", player -> player.getName());

        Main.log("nume: &c" + applyPlaceholders(source, text, namePlaceholder));
    }
}
