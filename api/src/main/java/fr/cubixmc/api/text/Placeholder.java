package fr.cubixmc.api.text;

import java.util.regex.Pattern;
import net.kyori.adventure.text.ComponentLike;

/**
 * Une valeur insérée dans un message : {@code <joueur>} dans le texte, {@code
 * Placeholder.text("joueur", name)} dans le code.
 *
 * <p>Une valeur texte est insérée telle quelle : un pseudo ou un message de joueur ne peut pas
 * injecter de couleurs ni de boutons.
 */
public sealed interface Placeholder {

    /** Nom de la balise, sans les chevrons. */
    String name();

    /** Texte brut (pseudo, nombre, nom de serveur…). */
    static Placeholder text(String name, Object value) {
        return new Text(name, String.valueOf(value));
    }

    /** Texte déjà mis en forme (nom de grade coloré…). */
    static Placeholder component(String name, ComponentLike value) {
        return new Rich(name, value);
    }

    record Text(String name, String value) implements Placeholder {
        public Text {
            Names.check(name);
        }
    }

    record Rich(String name, ComponentLike value) implements Placeholder {
        public Rich {
            Names.check(name);
        }
    }

    /** Règle de nommage commune (celle des balises MiniMessage). */
    final class Names {
        private static final Pattern FORMAT = Pattern.compile("^[a-z0-9_-]+$");

        private Names() {}

        static void check(String name) {
            if (!FORMAT.matcher(name).matches()) {
                throw new IllegalArgumentException(
                        "Nom de balise invalide : '" + name + "' (minuscules, chiffres, - et _)");
            }
        }
    }
}
