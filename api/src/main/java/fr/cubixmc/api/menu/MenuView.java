package fr.cubixmc.api.menu;

import fr.cubixmc.api.player.CubixPlayer;

/**
 * Un menu ouvert chez un joueur. Ses cases peuvent changer pendant qu'il le regarde (compteur,
 * page suivante…). Sur le thread de jeu uniquement.
 */
public interface MenuView {

    CubixPlayer viewer();

    /** Le menu tel qu'il a été ouvert. */
    Menu menu();

    /** Change une case, tout de suite, chez ce joueur seulement. */
    void set(int slot, Button button);

    /** Vide une case. */
    void clear(int slot);

    /** Le joueur regarde-t-il encore ce menu ? */
    boolean isOpen();

    void close();

    /**
     * Rouvre le menu d'où le joueur venait (ce que fait le bouton Retour).
     *
     * @return faux s'il n'y en avait pas
     */
    boolean back();
}
