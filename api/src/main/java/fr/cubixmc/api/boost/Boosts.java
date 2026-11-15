package fr.cubixmc.api.boost;

import fr.cubixmc.api.player.CubixPlayer;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Les boosts de gains du réseau : celui qu'un joueur a acheté (un seul à la fois, +50 % pendant
 * une heure sur un jeu) et ceux du week-end, tirés au sort. Un jeu se désigne par sa famille
 * ({@code rush} vaut pour tous les modes de Rush, {@code hungergames}) ou par un type de serveur.
 *
 * <p>Pour récompenser un joueur à la fin d'une partie, passer par {@link #reward} : boosts et
 * bonus du grade y sont appliqués, et le joueur voit les mêmes chiffres partout.
 */
public interface Boosts {

    /** Le boost acheté en cours. */
    record GameBoost(String game, String boosterName, int percent, Instant endsAt) {}

    enum Purchase {
        BOUGHT,
        /** Un boost est déjà en cours (il n'y en a qu'un à la fois). */
        ALREADY_BOOSTED,
        NOT_ENOUGH_COINS,
        UNKNOWN_GAME
    }

    /** Les familles de jeux qu'on peut booster. */
    List<String> games();

    /** Le boost acheté en cours, relu toutes les 5 secondes. */
    Optional<GameBoost> current();

    /** Bonus du week-end sur ce jeu aujourd'hui, en pour cent (0 en semaine). */
    int weekendPercent(String game);

    /** Bonus total sur ce jeu maintenant (week-end et boost acheté), plafonné à 200 %. */
    int bonusPercent(String game);

    /** Prix d'un boost sur ce jeu : il suit le nombre de joueurs qui y sont. */
    int price(String game);

    /** Achète un boost avec les coins du joueur ; le réseau l'annonce. */
    CompletableFuture<Purchase> buy(CubixPlayer player, String game);

    /**
     * Crédite une récompense de partie : base × bonus du grade × (1 + bonus du jeu).
     *
     * @return les coins réellement crédités
     */
    CompletableFuture<Integer> reward(CubixPlayer player, String game, int baseCoins);
}
