package ro.Gabriel.Player;

import net.md_5.bungee.api.chat.*;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import ro.Gabriel.Language.LanguageCategory;
import ro.Gabriel.Placeholder.Placeholder;
import ro.Gabriel.Language.LanguageUtils;
import ro.Gabriel.Messages.MessageSender;
import ro.Gabriel.Repository.BaseEntity;
import ro.Gabriel.Messages.MessageUtils;
import ro.Gabriel.Language.LanguageKey;
import ro.Gabriel.Language.Language;

import java.util.List;
import java.util.UUID;

public class SpigotUser extends BaseEntity<UUID> implements MessageSender {
	private Player player;

	public Language language;

	public SpigotUser(Player player) {
		super(player != null ? player.getUniqueId() : UUID.randomUUID());
		this.player = player;
	}

	public SpigotUser(UUID uuid) {
		super(uuid != null ? uuid : UUID.randomUUID());
		if(uuid != null) {
			this.player = Bukkit.getPlayer(uuid);
		}

		/*try {
			Main.log("get &ben &elanguage");
			this.language = new GeneralLanguage("ro");
		} catch (Exception e) {
			Main.log("&4FAIL LANGUAGE");
			e.printStackTrace();
		}*/
	}

	public String getName() {
		return this.player.getName();
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public void sendMessage(String message) {
		this.player.sendMessage(message);
	}

	private void sendMessage(Object message) {
		if(message == null)	{
			return;
		}

		if(message instanceof String) {
			this.sendMessage((String) message);
		} else if(message instanceof TextComponent) {
			if(player != null) {
				player.spigot().sendMessage((TextComponent) message);
			} else {
				this.sendMessage(MessageUtils.textComponentToString((TextComponent)message));
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void sendMessage(LanguageKey key, Object replacement) {
		Object message = MessageUtils.transform(key, getLanguage(), replacement);//Object message = MessageUtils.transform(this.language.get(key, LanguageCategory.MESSAGES), (Placeholder<Object>) key.getPlaceholder(), replacement);
		this.sendMessage(message);
	}

	@Override
	public void sendMessage(LanguageKey key) {
		this.sendMessage(key, this);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void sendMessage(List<SpigotUser> users, LanguageKey languageKey, Object placeholderSource) {
		Language currentLanguage;
		SpigotUser currentUser;

		for(int i = 0; i < users.size(); i++) {
			currentUser = users.get(i);
			if(LanguageUtils.wasBefore(users, i)) {
				continue;
			}
			currentLanguage = currentUser.getLanguage();

			Object message = MessageUtils.transform(currentLanguage.get(languageKey, LanguageCategory.MESSAGES), language, (Placeholder<Object>) languageKey.getPlaceholder(), placeholderSource);

			currentUser.sendMessage(message);

			for(int j = i + 1; j < users.size(); j++) {
				currentUser = users.get(j);
				if(currentUser.getLanguage().equals(currentLanguage)) {
					currentUser.sendMessage(message);
				}
			}
		}
	}

	@Override
	public void sendMessage(List<SpigotUser> users, LanguageKey languageKey) {
		this.sendMessage(users, languageKey, this);
	}

	public void sendMessage(List<SpigotUser> users, LanguageKey languageKey, Placeholder<?> placeholder) {

	}

	public boolean hasPermission(String permission) {
		return this.player.hasPermission(permission);
	}
}