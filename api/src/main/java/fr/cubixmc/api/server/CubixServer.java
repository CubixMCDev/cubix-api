package fr.cubixmc.api.server;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.rank.Rank;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Le serveur de jeu courant. */
public interface CubixServer {

    /** Nom unique sur le réseau (ex. {@code hub-7lwzk}). */
    String name();

    /** Joueurs connectés, en lecture seule. */
    Collection<CubixPlayer> onlinePlayers();

    Optional<CubixPlayer> player(UUID uuid);

    /** Joueurs sur tout le réseau (ce serveur seul s'il tourne sans Redis), relu toutes les 5 secondes. */
    int networkPlayerCount();

    /** Tous les grades, dans l'ordre d'affichage (le plus important en premier). */
    List<Rank> ranks();

    Optional<Rank> rank(String id);
}
