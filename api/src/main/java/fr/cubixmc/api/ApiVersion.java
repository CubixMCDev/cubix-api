package fr.cubixmc.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Version de l'API, réduite à ce qui compte pour la compatibilité : {@code majeure.mineure}.
 *
 * <p>Règle (semver) : un module écrit pour {@code 1.2} tourne sur toute API {@code 1.x} avec
 * {@code x >= 2}. Tant que l'API est en {@code 0.x}, chaque version mineure peut casser : il faut
 * alors exactement la même version mineure.
 */
public record ApiVersion(int major, int minor) implements Comparable<ApiVersion> {

    private static final Pattern FORMAT = Pattern.compile("^(\\d+)\\.(\\d+)(?:\\.\\d+)?(?:-[0-9A-Za-z.-]+)?$");

    public ApiVersion {
        if (major < 0 || minor < 0) {
            throw new IllegalArgumentException("Version négative : " + major + "." + minor);
        }
    }

    /** Lit {@code 1.2}, {@code 1.2.3} ou {@code 1.2.3-SNAPSHOT}. */
    public static ApiVersion parse(String text) {
        Matcher matcher = FORMAT.matcher(text.strip());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Version d'API invalide : '" + text + "' (attendu : 1.2 ou 1.2.3)");
        }
        return new ApiVersion(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    /** Cette API (celle du serveur) peut-elle faire tourner un module qui demande {@code required} ? */
    public boolean canRun(ApiVersion required) {
        if (major != required.major) {
            return false;
        }
        return major == 0 ? minor == required.minor : minor >= required.minor;
    }

    @Override
    public int compareTo(ApiVersion other) {
        return major != other.major ? Integer.compare(major, other.major) : Integer.compare(minor, other.minor);
    }

    @Override
    public String toString() {
        return major + "." + minor;
    }
}
