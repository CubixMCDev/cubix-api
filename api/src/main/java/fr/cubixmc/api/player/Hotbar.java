package fr.cubixmc.api.player;

import fr.cubixmc.api.menu.Icon;
import java.util.function.Consumer;

/**
 * Les objets de la barre rapide d'un joueur que le module y pose (boussole du hub, objets de
 * spectateur…). Ils sont <b>verrouillés</b> : impossible de les déplacer, de les jeter ou de les
 * passer dans l'autre main. Un clic droit avec l'objet en main (dans le vide ou sur un bloc, et un
 * appui sur Bedrock) lance son action, sur le thread de jeu.
 *
 * <p>Sur le thread de jeu uniquement.
 */
public interface Hotbar {

    int SIZE = 9;

    /** Barre rapide (0 à 8) et inventaire (9 à 35) : un objet hors de la barre s'utilise d'un clic. */
    int INVENTORY = 36;

    /**
     * Pose un objet.
     *
     * @param slot case de 0 (à gauche) à 8
     */
    void set(int slot, Icon icon, Consumer<CubixPlayer> onUse);

    /** Pose un objet sans action (décoration, information). */
    void set(int slot, Icon icon);

    /** Retire l'objet posé dans cette case. */
    void clear(int slot);

    /** Retire tous les objets posés par les modules. */
    void clearAll();

    /** Sélectionne la case tenue en main (0 à 8). */
    void select(int slot);
}
