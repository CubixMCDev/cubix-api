package fr.cubixmc.api.economy;

import java.util.concurrent.CompletableFuture;

/**
 * Les coins et cristaux d'un joueur.
 *
 * <p>Ils restent <b>cosmétiques</b> (règles d'utilisation de Minecraft) : jamais d'avantage en
 * partie contre de l'argent réel. Un module s'en sert pour récompenser et pour vendre des
 * cosmétiques.
 *
 * <p>Le solde lu est le dernier connu sur ce serveur : il suit les opérations faites ici et celles
 * faites ailleurs sur le réseau (votes, commandes du staff). La base reste la référence : un ajout
 * ou un retrait y est écrit, hors du thread de jeu, et ne fait jamais passer le solde sous zéro.
 */
public interface Wallet {

    /** Dernier solde connu. */
    int balance(Currency currency);

    default int coins() {
        return balance(Currency.COINS);
    }

    default int crystals() {
        return balance(Currency.CRYSTALS);
    }

    /**
     * Ajoute (montant positif) ou retire (montant négatif).
     *
     * <p>Le résultat arrive hors du thread de jeu : pour toucher au monde ensuite, repasser par le
     * {@code Scheduler} du module.
     *
     * @return vrai si c'est fait ; faux si le solde ne suffisait pas (rien n'a changé)
     */
    CompletableFuture<Boolean> add(Currency currency, int amount);

    /** Retire, seulement si le solde suffit. */
    default CompletableFuture<Boolean> take(Currency currency, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Montant négatif : " + amount);
        }
        return add(currency, -amount);
    }
}
