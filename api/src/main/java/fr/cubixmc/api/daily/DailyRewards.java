package fr.cubixmc.api.daily;

import fr.cubixmc.api.player.CubixPlayer;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * La récompense du jour, comme en v1 : une fois toutes les 24 heures, une série de sept jours aux
 * coins croissants, avec des cristaux les jours 3, 6 et 7. Un jour manqué (plus de 48 heures)
 * recommence la série.
 */
public interface DailyRewards {

    /** Ce qu'un retrait a rapporté. */
    record Claimed(int day, int coins, int crystals) {}

    /**
     * Où en est le joueur.
     *
     * @param remaining temps avant la prochaine (zéro si elle est disponible)
     * @param day le jour de la série que rapportera la prochaine (1 à 7)
     */
    record Status(Duration remaining, int day) {

        public boolean available() {
            return remaining.isZero();
        }
    }

    CompletableFuture<Status> status(CubixPlayer player);

    /** Retire la récompense ; rien si elle n'est pas encore disponible. */
    CompletableFuture<Optional<Claimed>> claim(CubixPlayer player);
}
