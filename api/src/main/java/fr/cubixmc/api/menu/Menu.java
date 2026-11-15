package fr.cubixmc.api.menu;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

/**
 * Un menu en coffre, de 1 à 6 lignes de 9 cases. Immuable : on le décrit une fois, puis on l'ouvre
 * avec {@link CubixPlayer#openMenu(Menu)} autant de fois qu'on veut.
 *
 * <p>Le joueur ne peut rien prendre ni déposer : chaque clic est retenu et transmis au bouton de la
 * case. Les menus en coffre s'affichent aussi chez les joueurs Bedrock.
 *
 * <p><b>Retour</b> : un menu ouvert depuis un autre menu (un clic qui appelle {@code openMenu})
 * reçoit tout seul un bouton « Retour » qui rouvre le précédent, sur autant de niveaux qu'il
 * faut. Il se place dans le coin en bas à gauche ({@link Builder#backSlot}), sauf si un vrai bouton
 * l'occupe ; {@link Builder#withoutBack} le retire. Fermer le menu oublie ce chemin. Le menu
 * précédent se rouvre tel qu'il a été décrit : un contenu qui change se décrit à nouveau.
 *
 * <pre>{@code
 * Menu menu = Menu.builder(Component.text("Jeux"), 3)
 *         .border(Icon.filler("minecraft:gray_stained_glass_pane"))
 *         .set(1, 4, Button.of(rushIcon, click -> click.player().sendMessage(…)))
 *         .build();
 * player.openMenu(menu);
 * }</pre>
 */
public final class Menu {

    public static final int COLUMNS = 9;
    public static final int MAX_ROWS = 6;

    /** Pas de bouton Retour. */
    public static final int NO_BACK = -1;

    private final Component title;
    private final int rows;
    private final Map<Integer, Button> buttons;
    private final @Nullable Consumer<CubixPlayer> onClose;
    private final int backSlot;

    private Menu(
            Component title,
            int rows,
            Map<Integer, Button> buttons,
            @Nullable Consumer<CubixPlayer> onClose,
            int backSlot) {
        this.title = title;
        this.rows = rows;
        this.buttons = Map.copyOf(buttons);
        this.onClose = onClose;
        this.backSlot = backSlot;
    }

    /**
     * Commence un menu.
     *
     * @param rows nombre de lignes, de 1 à 6
     */
    public static Builder builder(Component title, int rows) {
        if (rows < 1 || rows > MAX_ROWS) {
            throw new IllegalArgumentException("Un menu a de 1 à 6 lignes, pas " + rows);
        }
        return new Builder(title, rows);
    }

    public Component title() {
        return title;
    }

    public int rows() {
        return rows;
    }

    public int size() {
        return rows * COLUMNS;
    }

    /** Le bouton d'une case, s'il y en a un. */
    public Optional<Button> button(int slot) {
        return Optional.ofNullable(buttons.get(slot));
    }

    /** Toutes les cases remplies, par numéro de case. */
    public Map<Integer, Button> buttons() {
        return buttons;
    }

    /** Case du bouton Retour, ou {@link #NO_BACK}. */
    public int backSlot() {
        return backSlot;
    }

    /** Appelé quand le joueur ferme le menu (ou quitte le serveur). */
    public Optional<Consumer<CubixPlayer>> onClose() {
        return Optional.ofNullable(onClose);
    }

    /** Construit un {@link Menu}. Les cases se comptent de 0, de gauche à droite puis de haut en bas. */
    public static final class Builder {

        private final Component title;
        private final int rows;
        private final Map<Integer, Button> buttons = new HashMap<>();
        private @Nullable Consumer<CubixPlayer> onClose;
        private int backSlot;

        private Builder(Component title, int rows) {
            this.title = title;
            this.rows = rows;
            this.backSlot = (rows - 1) * COLUMNS;
        }

        /** Remplit une case (remplace ce qui s'y trouvait). */
        public Builder set(int slot, Button button) {
            if (slot < 0 || slot >= rows * COLUMNS) {
                throw new IllegalArgumentException("Case " + slot + " hors du menu (" + rows * COLUMNS + " cases)");
            }
            buttons.put(slot, button);
            return this;
        }

        /** Remplit la case d'une ligne et d'une colonne, comptées de 0. */
        public Builder set(int row, int column, Button button) {
            if (column < 0 || column >= COLUMNS) {
                throw new IllegalArgumentException("Colonne hors de 0..8 : " + column);
            }
            return set(row * COLUMNS + column, button);
        }

        /** Une icône sans action. */
        public Builder set(int slot, Icon icon) {
            return set(slot, Button.decoration(icon));
        }

        /** Une icône sans action, par ligne et colonne. */
        public Builder set(int row, int column, Icon icon) {
            return set(row, column, Button.decoration(icon));
        }

        /** Le pourtour du menu, sur les cases encore vides. */
        public Builder border(Icon icon) {
            for (int slot = 0; slot < rows * COLUMNS; slot++) {
                int row = slot / COLUMNS;
                int column = slot % COLUMNS;
                boolean edge = row == 0 || row == rows - 1 || column == 0 || column == COLUMNS - 1;
                if (edge) {
                    buttons.putIfAbsent(slot, Button.decoration(icon));
                }
            }
            return this;
        }

        /** Toutes les cases encore vides. */
        public Builder fill(Icon icon) {
            for (int slot = 0; slot < rows * COLUMNS; slot++) {
                buttons.putIfAbsent(slot, Button.decoration(icon));
            }
            return this;
        }

        /** Où mettre le bouton Retour (par défaut : le coin en bas à gauche). */
        public Builder backSlot(int slot) {
            if (slot < 0 || slot >= rows * COLUMNS) {
                throw new IllegalArgumentException("Case " + slot + " hors du menu (" + rows * COLUMNS + " cases)");
            }
            this.backSlot = slot;
            return this;
        }

        /** Pas de bouton Retour, même ouvert depuis un autre menu. */
        public Builder withoutBack() {
            this.backSlot = NO_BACK;
            return this;
        }

        public Builder onClose(Consumer<CubixPlayer> action) {
            this.onClose = action;
            return this;
        }

        public Menu build() {
            return new Menu(title, rows, buttons, onClose, backSlot);
        }
    }
}
