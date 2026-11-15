package fr.cubixmc.api.player;

import fr.cubixmc.api.rank.Rank;
import java.util.Locale;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;

/**
 * Un joueur connecté à ce serveur.
 *
 * <p>Hérite d'{@link Audience} : messages, titres, sons, barre d'action, etc.
 */
public interface CubixPlayer extends Audience {

    UUID uuid();

    String name();

    /** Joueur Bedrock (via Geyser) : le moteur adapte alors menus et affichages. */
    boolean isBedrock();

    /** Langue des messages : choisie par le joueur, sinon celle de son client, sinon le français. */
    Locale locale();

    /** Grade actuel. Suit les changements faits ailleurs sur le réseau. */
    Rank rank();

    /** Permission accordée par le grade du joueur ({@code cubix.staff}, {@code cubix.perk.hub.fly}…). */
    boolean hasPermission(String permission);
}
