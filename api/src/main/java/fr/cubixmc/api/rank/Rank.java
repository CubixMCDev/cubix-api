package fr.cubixmc.api.rank;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

/** Un grade du réseau (Joueur, VIP, Modérateur…). */
public interface Rank {

    /** Identifiant stable ({@code player}, {@code vip}, {@code admin}…). */
    String id();

    /** Nom affiché, en texte brut. */
    String displayName();

    TextColor color();

    boolean bold();

    /** Autorité : un grade n'agit que sur les grades de power strictement inférieur. */
    int power();

    /** Le nom du grade, en couleur (et en gras s'il y a lieu). */
    default Component display() {
        return Component.text(displayName(), color()).decoration(TextDecoration.BOLD, bold());
    }

    /** Ce grade a-t-il autorité sur {@code other} (sanction, changement de grade) ? */
    default boolean outranks(Rank other) {
        return power() > other.power();
    }
}
