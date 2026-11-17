package fr.cubixmc.api.cosmetic;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jspecify.annotations.Nullable;

/**
 * Les cosmétiques des joueurs : ce qu'ils possèdent (pour tout le réseau), ce qu'ils portent, et
 * leurs coffres mystère. Le catalogue (prix, niveau, rendu) appartient au module qui les montre.
 * Les coins restent purement cosmétiques (EULA).
 */
public interface Cosmetics {

    /** Les trois sortes, chacune avec un seul actif à la fois. */
    enum Kind {
        PARTICLE,
        GADGET,
        PET
    }

    /** Les trois coffres mystère. */
    enum Box {
        COMMON,
        RARE,
        EPIC
    }

    enum Purchase {
        BOUGHT,
        ALREADY_OWNED,
        NOT_ENOUGH_COINS
    }

    /** Les identifiants qu'il possède. */
    CompletableFuture<Set<String>> owned(CubixPlayer player);

    /** Achète un cosmétique : les coins ne partent que s'il ne l'avait pas, en une seule fois. */
    CompletableFuture<Purchase> buy(CubixPlayer player, String id, int price);

    /** Le donne (coffre mystère…) ; {@code false} s'il l'avait déjà. */
    CompletableFuture<Boolean> grant(UUID player, String id);

    /** Ce qu'il porte, lu à son arrivée. Ne bloque jamais. */
    Optional<String> active(CubixPlayer player, Kind kind);

    /** Change ce qu'il porte ({@code null} : rien). */
    CompletableFuture<Void> setActive(CubixPlayer player, Kind kind, @Nullable String id);

    /** Ses coffres mystère, par sorte. */
    CompletableFuture<Map<Box, Integer>> boxes(CubixPlayer player);

    /** Ajoute (ou retire, si {@code delta} est négatif) des coffres ; {@code false} s'il n'en a pas assez. */
    CompletableFuture<Boolean> addBoxes(UUID player, Box box, int delta);

    /**
     * Tire au sort un coffre en fin de partie, comme en v1 : une chance sur dix, puis commun, rare
     * (4 %) ou épique (1 %). Le coffre gagné est ajouté.
     *
     * @return le coffre gagné, s'il y en a un
     */
    CompletableFuture<Optional<Box>> rollDrop(CubixPlayer player);
}
