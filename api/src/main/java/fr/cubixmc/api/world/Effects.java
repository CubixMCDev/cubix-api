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
}
