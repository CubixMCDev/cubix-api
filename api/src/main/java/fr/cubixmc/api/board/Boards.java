package fr.cubixmc.api.board;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.world.Position;
import java.util.List;
import java.util.function.Function;
import net.kyori.adventure.text.Component;

/**
 * Les tableaux d'un module, retirés à son arrêt : des cartes dans des cadres invisibles, où le
 * serveur dessine des panneaux (titre, lignes, pied), chacun à sa façon pour chaque joueur (sa
 * propre ligne « Toi »). Java comme Bedrock les voient. Les textes sont écrits en capitales d'une
 * police pixel ; leurs couleurs viennent des composants.
 */
public interface Boards {

    /** Le côté vers lequel le tableau regarde (celui d'où on le lit). */
    enum Facing {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    /** Une ligne : à gauche (le rang), au milieu (le nom), à droite (la valeur). */
    record Line(Component left, Component name, Component value) {}

    /** Un panneau ; plusieurs panneaux se partagent la largeur du tableau. */
    record Panel(Component title, Component subtitle, List<Line> lines, Component footer) {}

    /**
     * Accroche un tableau.
     *
     * @param topLeft le bloc du coin en haut à gauche, vu de face, juste devant le mur
     * @param width largeur en cartes (128 pixels chacune)
     * @param height hauteur en cartes
     * @param content ce que voit ce joueur ; appelé hors du thread de jeu
     */
    Board create(Position topLeft, Facing facing, int width, int height, Function<CubixPlayer, List<Panel>> content);
}
