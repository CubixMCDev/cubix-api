package fr.cubixmc.api.hologram;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.world.Position;

/**
 * Un texte qui flotte dans le monde. Chaque joueur voit le sien (dans sa langue, avec ses
 * chiffres) : son contenu est redemandé à chaque {@link #refresh}. Sur le thread de jeu.
 */
public interface Hologram {

    Position position();

    /** Redemande le contenu pour chaque joueur et met à jour ce qui a changé. */
    void refresh();

    /** Redemande le contenu pour ce joueur seulement (ses chiffres viennent de changer). */
    void refresh(CubixPlayer player);

    /** Retire l'hologramme pour tout le monde. */
    void remove();
}
