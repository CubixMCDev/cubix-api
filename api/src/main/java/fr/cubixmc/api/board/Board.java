package fr.cubixmc.api.board;

import fr.cubixmc.api.player.CubixPlayer;

/** Un tableau accroché au mur. */
public interface Board {

    /** Redessine le tableau pour tous les joueurs (ses données ont changé). */
    void refresh();

    /** Redessine le tableau pour ce joueur seulement (ses propres chiffres ont changé). */
    void show(CubixPlayer player);

    void remove();
}
