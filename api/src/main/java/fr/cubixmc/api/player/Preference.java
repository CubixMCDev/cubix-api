package fr.cubixmc.api.player;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

/**
 * Une préférence d'un joueur, gardée pour tout le réseau. On la lit par
 * {@link CubixPlayer#preference(Preference)} et on la change par
 * {@link CubixPlayer#setPreference(Preference, Object)}.
 *
 * <p>La clé ne doit plus changer : elle sert de nom en base.
 *
 * @param <T> {@link Boolean} ou {@link Allowed}
 */
public final class Preference<T> {

    /** Qui a le droit de faire quelque chose au joueur (l'inviter, lui écrire). */
    public enum Allowed {
        EVERYONE,
        FRIENDS,
        NOBODY
    }

    /** Recevoir des demandes d'ami. */
    public static final Preference<Boolean> FRIEND_REQUESTS = bool("friend-requests", true);

    /** Qui peut l'inviter dans un groupe. */
    public static final Preference<Allowed> PARTY_INVITES = allowed("party-invites", Allowed.EVERYONE);

    /** Qui peut lui envoyer un message privé (le staff le peut toujours). */
    public static final Preference<Allowed> PRIVATE_MESSAGES = allowed("private-messages", Allowed.EVERYONE);

    /** Suivre le chef de son groupe quand il change de serveur. */
    public static final Preference<Boolean> PARTY_FOLLOW = bool("party-follow", true);

    /** Voir les autres joueurs au hub. */
    public static final Preference<Boolean> SHOW_PLAYERS = bool("show-players", true);

    /** Effets de sang en jeu. */
    public static final Preference<Boolean> BLOOD = bool("blood", true);

    /** Toutes, dans l'ordre du menu. */
    public static final List<Preference<?>> ALL =
            List.of(FRIEND_REQUESTS, PARTY_INVITES, PRIVATE_MESSAGES, PARTY_FOLLOW, SHOW_PLAYERS, BLOOD);

    private final String key;
    private final T fallback;
    private final List<T> choices;
    private final Function<String, Optional<T>> parser;

    private Preference(String key, T fallback, List<T> choices, Function<String, Optional<T>> parser) {
        this.key = key;
        this.fallback = fallback;
        this.choices = List.copyOf(choices);
        this.parser = parser;
    }

    private static Preference<Boolean> bool(String key, boolean fallback) {
        return new Preference<>(key, fallback, List.of(true, false), raw -> switch (raw) {
            case "true" -> Optional.of(true);
            case "false" -> Optional.of(false);
            default -> Optional.empty();
        });
    }

    private static Preference<Allowed> allowed(String key, Allowed fallback) {
        return new Preference<>(key, fallback, List.of(Allowed.values()), raw -> {
            try {
                return Optional.of(Allowed.valueOf(raw.toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        });
    }

    public String key() {
        return key;
    }

    public T fallback() {
        return fallback;
    }

    /** Les valeurs possibles, dans l'ordre où un clic les fait défiler. */
    public List<T> choices() {
        return choices;
    }

    /** La valeur suivante, pour un bouton qui fait défiler les choix. */
    public T next(T current) {
        int index = choices.indexOf(current);
        return choices.get((index + 1) % choices.size());
    }

    /** Lit une valeur enregistrée ; rien si elle n'a pas de sens. */
    public Optional<T> parse(String raw) {
        return parser.apply(raw);
    }

    /** Écrit une valeur pour l'enregistrer. */
    public String write(T value) {
        return value instanceof Allowed allowed ? allowed.name().toLowerCase(Locale.ROOT) : String.valueOf(value);
    }

    @Override
    public String toString() {
        return key;
    }
}
