package fr.cubixmc.api.text;

import java.time.Duration;
import java.util.regex.Pattern;

/**
 * Un message traduit, identifié par sa clé dans les fichiers de langue.
 *
 * <p>À déclarer en constante, une fois : {@code static final MessageKey WELCOME =
 * MessageKey.of("hub.welcome");}
 *
 * <p>Anti-spam : le même message identique n'est jamais renvoyé au même joueur dans la seconde.
 * Un message qui répond à une action répétée (« attends avant de rejouer », « tu n'as pas assez de
 * coins ») peut demander un délai plus long avec {@link #throttled}, quels que soient ses
 * arguments.
 *
 * @param key clé dans les fichiers de langue (minuscules, chiffres, points, tirets, soulignés)
 * @param cooldown délai minimal entre deux envois de ce message au même joueur ({@link
 *     Duration#ZERO} : seul l'anti-doublon d'une seconde s'applique)
 */
public record MessageKey(String key, Duration cooldown) {

    private static final Pattern FORMAT = Pattern.compile("^[a-z0-9_-]+(\\.[a-z0-9_-]+)+$");

    public MessageKey {
        if (!FORMAT.matcher(key).matches()) {
            throw new IllegalArgumentException(
                    "Clé de message invalide : '" + key + "' (ex. hub.welcome, minuscules et points)");
        }
        if (cooldown.isNegative()) {
            throw new IllegalArgumentException("Délai négatif pour " + key);
        }
    }

    public static MessageKey of(String key) {
        return new MessageKey(key, Duration.ZERO);
    }

    /** Message envoyé au plus une fois par {@code cooldown} au même joueur. */
    public static MessageKey throttled(String key, Duration cooldown) {
        return new MessageKey(key, cooldown);
    }
}
