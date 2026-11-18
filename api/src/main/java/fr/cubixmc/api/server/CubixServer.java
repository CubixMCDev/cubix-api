package fr.cubixmc.api.server;

import fr.cubixmc.api.boost.Boosts;
import fr.cubixmc.api.cosmetic.Cosmetics;
import fr.cubixmc.api.daily.DailyRewards;
import fr.cubixmc.api.economy.Currency;
import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.progress.Progress;
import fr.cubixmc.api.rank.Rank;
import fr.cubixmc.api.social.Social;
import fr.cubixmc.api.world.Effects;
import fr.cubixmc.api.world.WorldRules;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

    /** Statistiques, quêtes, succès et niveau des joueurs. */
    Progress progress();

    /** Les règles du monde de ce serveur (protection, vide, déplacements du lobby). */
    WorldRules world();

    /** Particules et feux d'artifice. */
    Effects effects();

    /** Amis, groupes et profils du réseau. */
    Social social();

    /** La récompense du jour. */
    DailyRewards dailyRewards();

    /** Cosmétiques et coffres mystère. */
    Cosmetics cosmetics();

    /** Tous les grades, dans l'ordre d'affichage (le plus important en premier). */
    List<Rank> ranks();

    Optional<Rank> rank(String id);

    /**
     * Achète un grade en jeu : le prix est pris, et le grade devient le grade secondaire du joueur
     * (son étoile ; le grade principal reste le sien). Hors du thread de jeu.
     */
    CompletableFuture<RankPurchase> buyRank(CubixPlayer player, String rankId, Currency currency, int price);

    /** Les files d'attente des jeux. */
    Queues queues();
}
