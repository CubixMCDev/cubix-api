package fr.cubixmc.api.menu;

import fr.cubixmc.api.player.CubixPlayer;

/**
 * Un clic dans un menu.
 *
 * @param slot case cliquée, comptée de 0
 * @param view le menu ouvert, pour changer une case ou le fermer
 */
public record MenuClick(CubixPlayer player, int slot, Kind kind, MenuView view) {

    /** La façon de cliquer. Sur Bedrock, presque tout arrive en {@link #LEFT}. */
    public enum Kind {
        LEFT,
        RIGHT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
        MIDDLE,
        /** Touche « jeter » (Q) sur la case. */
        DROP,
        /** Touche de la barre rapide (1 à 9). */
        NUMBER_KEY,
        OTHER
    }

    public boolean isRight() {
        return kind == Kind.RIGHT || kind == Kind.SHIFT_RIGHT;
    }

    public boolean isShift() {
        return kind == Kind.SHIFT_LEFT || kind == Kind.SHIFT_RIGHT;
    }
}
