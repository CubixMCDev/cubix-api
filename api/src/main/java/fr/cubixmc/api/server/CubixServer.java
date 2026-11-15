package fr.cubixmc.api.server;

import fr.cubixmc.api.boost.Boosts;
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

    /** Les serveurs du réseau (ce serveur seul sans Redis), par nom, relus toutes les 5 secondes. */
    List<NetworkServer> networkServers();

    /** Type de ce serveur ({@code hub}, {@code rush_1v1}…). */
    String type();

    /** Les boosts de gains du réseau. */
    Boosts boosts();

    /** Tous les grades, dans l'ordre d'affichage (le plus important en premier). */
    List<Rank> ranks();

    Optional<Rank> rank(String id);
}
