package fr.cubixmc.api.server;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/** Le serveur de jeu courant. */
public interface CubixServer {

    /** Nom unique sur le réseau (ex. {@code hub-7lwzk}). */
    String name();

    /** Joueurs connectés, en lecture seule. */
    Collection<CubixPlayer> onlinePlayers();

    Optional<CubixPlayer> player(UUID uuid);
}
