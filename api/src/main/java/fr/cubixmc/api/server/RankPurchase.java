package fr.cubixmc.api.server;

/** Le résultat d'un achat de grade en jeu. */
public enum RankPurchase {
    /** Payé et donné : il devient le grade secondaire du joueur. */
    DONE,
    /** Solde insuffisant : rien n'a changé. */
    NOT_ENOUGH,
    /** Le joueur a déjà ce grade, ou mieux. */
    ALREADY_OWNED,
    /** Grade inconnu. */
    UNKNOWN_RANK,
    /** Payé mais pas enregistré : le paiement est rendu. */
    FAILED
}
