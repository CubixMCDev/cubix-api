package fr.cubixmc.api.world;

import fr.cubixmc.api.player.CubixPlayer;
import java.time.Duration;
import java.util.List;
import net.kyori.adventure.text.Component;

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

    /** Un son entendu autour de ce point ({@code minecraft:entity.slime.jump}…). */
    void sound(Position at, String sound, float volume, float pitch);

    /**
     * Lance un objet qui vole puis disparaît dans un nuage de particules (au sol, ou au bout de trois
     * secondes). Purement visuel : il ne touche personne.
     *
     * @param item l'objet ({@code minecraft:snowball}, {@code minecraft:tnt}…)
     * @param velocity en blocs par tick
     * @param burst les particules à l'arrivée ({@code minecraft:explosion}…)
     */
    void throwItem(Position from, Position velocity, String item, String burst);

    /**
     * Un animal (ou une autre entité) qui apparaît puis disparaît, sans intelligence : chauves-souris
     * qui s'envolent, vaches lancées…
     *
     * @param entity type d'entité ({@code minecraft:bat}…)
     * @param velocity en blocs par tick
     */
    void spawnTemporary(String entity, Position at, Position velocity, Duration life);

    /** Un éclair, sans dégâts ni feu. */
    void lightning(Position at);

    /**
     * Un familier qui suit son maître sur ce serveur, avec son nom au-dessus.
     *
     * @param entity type d'entité ({@code minecraft:wolf}…)
     */
    Pet spawnPet(CubixPlayer owner, String entity, Component name);

    /**
     * Montre un bloc à ce joueur seulement, puis remet le vrai ({@code minecraft:nether_portal} à
     * l'arrivée, par exemple). Rien ne change dans le monde.
     */
    void flashBlock(CubixPlayer viewer, Position at, String block, Duration duration);

    /**
     * L'effet d'un portail du Nether, chez ce joueur seulement : le voile violet qui tourne, le
     * son d'entrée. Son jeu le dessine lui-même, comme s'il se tenait dans un portail : deux blocs
     * de portail envoyés à lui seul, à ses pieds et à sa tête. Il est recentré sur son bloc (de
     * quelques centimètres), ce qui met sa caméra entre les deux faces du portail : il ne voit pas
     * les blocs, seulement l'effet. L'effet monte en 4 secondes environ puis retombe : 2 à 4
     * secondes rendent bien. Rien ne change dans le monde.
     */
    void portalEffect(CubixPlayer viewer, Duration duration);

    /** De la poussière colorée ({@code 0xAA00FF}), vue de tous. */
    void dust(Position at, int color, float size, int count, double spread);
}
