package fr.cubixmc.api.sidebar;

import java.util.List;
import net.kyori.adventure.text.Component;

/**
 * La barre de côté d'un joueur (le « scoreboard ») : un titre et jusqu'à {@link #MAX_LINES} lignes,
 * sans numéros à droite. Propre à chaque joueur, donc dans sa langue.
 *
 * <p>Appeler {@link #show} aussi souvent que voulu (chaque seconde pour un minuteur) : seules les
 * lignes qui changent sont renvoyées au client. Sur le thread de jeu uniquement.
 */
public interface Sidebar {

    int MAX_LINES = 15;

    /**
     * Affiche ou remplace la barre.
     *
     * @param lines de haut en bas ; au-delà de {@link #MAX_LINES}, les dernières sont ignorées. Une
     *     ligne vide fait un espace.
     */
    void show(Component title, List<Component> lines);

    /** Retire la barre. */
    void hide();

    boolean isShown();
}
