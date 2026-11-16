package fr.cubixmc.api.world;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.List;

/** Effets visuels dans le monde du serveur : particules et feux d'artifice. Sur n'importe quel fil. */
public interface Effects {

    /**
     * Des particules, vues par tous les joueurs proches.
     *
     * @param particle clé Minecraft ({@code minecraft:flame}, {@code minecraft:heart}…) ; une clé
     *     inconnue ne fait rien
     * @param spread écart autour du point, dans chaque direction
     */
    void particles(String particle, Position at, int count, double spread);

    /** Des particules que seul ce joueur voit. */
    void particles(CubixPlayer viewer, String particle, Position at, int count, double spread);

    /**
     * Un feu d'artifice qui éclate aussitôt.
     *
     * @param colors couleurs en {@code 0xRRGGBB}
     */
    void firework(Position at, List<Integer> colors);
}
