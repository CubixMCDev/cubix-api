package fr.cubixmc.api.hud;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.world.Position;
import java.util.List;
import net.kyori.adventure.text.Component;

/**
 * Le haut de l'écran, au-dessus des barres de boss ordinaires.
 *
 * <ul>
 *   <li>la boussole : les points cardinaux défilent quand le joueur tourne, avec des repères
 *       (un portail, un personnage) à leur direction. Elle demande le pack CubixMC : sans lui
 *       (refusé, ou Bedrock), elle ne s'affiche pas ;
 *   <li>la ligne : un texte sous la boussole (minuteur, objectif). Elle s'affiche pour tous ;
 *       sans le pack, elle garde l'aspect d'une barre de boss.
 * </ul>
 *
 * <p>Tout est retiré à l'arrêt du module. Sur n'importe quel thread.
 */
public interface Hud {

    /**
     * Un repère sur la boussole.
     *
     * @param label court, en capitales de préférence (la largeur du texte doit être connue)
     */
    record Marker(Position position, Component label) {}

    /** Montre la boussole à ce joueur (ou change ses repères). */
    void compass(CubixPlayer player, List<Marker> markers);

    void hideCompass(CubixPlayer player);

    /** La ligne sous la boussole ; {@link Component#empty()} la retire. */
    void line(CubixPlayer player, Component text);

    /**
     * Deux encadrés posés sur la barre de vie (à gauche) et la barre de nourriture (à droite), pour
     * un lobby où elles ne servent à rien. Pack requis ; sans lui, les barres restent. Les textes
     * n'emploient que des lettres sans accent, des chiffres et les symboles ⛃ et ✦ (la police
     * descendue du pack n'a qu'eux).
     */
    void panels(CubixPlayer player, Component left, Component right);

    void clearPanels(CubixPlayer player);

    /**
     * Le message de la barre d'action. Passer par ici plutôt que {@code sendActionBar} : avec les
     * encadrés, les deux partagent la même barre.
     */
    void actionBar(CubixPlayer player, Component text);

    /**
     * Le logo CubixMC pour ce joueur : dessiné (pixel art du pack) s'il a le pack, en texte sinon.
     * Pour un titre, la barre de côté, un message d'accueil.
     */
    Component logo(CubixPlayer player);

    /** Le joueur a-t-il le pack (la boussole lui est visible) ? */
    boolean equipped(CubixPlayer player);
}
