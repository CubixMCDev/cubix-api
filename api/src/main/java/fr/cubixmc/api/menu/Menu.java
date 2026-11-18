package fr.cubixmc.api.menu;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntFunction;
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
 * <p><b>Pages</b> : {@link Builder#paged} répartit une longue liste sur plusieurs pages ; le
 * serveur ajoute lui-même les flèches « Page précédente » et « Page suivante » (dans la langue du
 * joueur) et ouvre la page voulue sans allonger le chemin du Retour.
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

    /**
     * Une liste en plusieurs pages.
     *
     * @param page la page affichée, comptée de 0
     * @param pages le nombre de pages (au moins 1)
     * @param open décrit le menu d'une autre page
     */
    public record Paging(int page, int pages, IntFunction<Menu> open, int previousSlot, int nextSlot) {}

    private final Component title;
    private final int rows;
    private final Map<Integer, Button> buttons;
    private final @Nullable Consumer<CubixPlayer> onClose;
    private final int backSlot;
    private final @Nullable String art;
    private final @Nullable Paging paging;

    private Menu(
            Component title,
            int rows,
            Map<Integer, Button> buttons,
            @Nullable Consumer<CubixPlayer> onClose,
            int backSlot,
            @Nullable String art,
            @Nullable Paging paging) {
        this.paging = paging;
        this.title = title;
        this.rows = rows;
        this.buttons = Map.copyOf(buttons);
        this.onClose = onClose;
        this.backSlot = backSlot;
        this.art = art;
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

    /** Le fond dessiné par le pack, s'il en a un à lui (voir {@link Builder#art}). */
    public Optional<String> art() {
        return Optional.ofNullable(art);
    }

    /** Les pages, si ce menu en a (voir {@link Builder#paged}). */
    public Optional<Paging> paging() {
        return Optional.ofNullable(paging);
    }

    /** Les cases à l'intérieur d'un cadre ({@link Builder#border}) : ce qu'il reste pour une liste. */
    public static List<Integer> inner(int rows) {
        List<Integer> slots = new ArrayList<>();
        for (int row = 1; row < rows - 1; row++) {
            for (int column = 1; column < COLUMNS - 1; column++) {
                slots.add(row * COLUMNS + column);
            }
        }
        return List.copyOf(slots);
    }

    /** Le nombre de pages pour tant d'éléments, tant par page (au moins une). */
    public static int pageCount(int items, int perPage) {
        return Math.max(1, (items + perPage - 1) / Math.max(1, perPage));
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
        private @Nullable String art;
        private @Nullable Paging paging;

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

        /**
         * Le fond que le pack CubixMC dessine pour ce menu, chez les joueurs Java qui l'ont : son
         * en-tête à ses couleurs, avec son emblème. Noms connus : {@code hub}, {@code profil},
         * {@code cosmetiques}, {@code social}, {@code minijeux}, {@code boosts}, {@code admin},
         * {@code sanctions}. Un nom inconnu (ou aucun) donne le fond ordinaire.
         */
        public Builder art(String name) {
            this.art = name;
            return this;
        }

        /**
         * Met la page {@code page} (comptée de 0) de cette liste dans les cases {@code slots}, dans
         * l'ordre. Les flèches vont en bas, de part et d'autre du milieu ; {@code open} décrit le
         * menu d'une autre page (le plus simple : la même méthode, avec l'autre numéro).
         *
         * <pre>{@code
         * Menu friends(CubixPlayer player, int page) {
         *     return Menu.builder(title, 6)
         *             .paged(buttons, Menu.inner(6), page, other -> friends(player, other))
         *             .border(frame)
         *             .build();
         * }
         * }</pre>
         *
         * <p>Une page hors des bornes est ramenée à la première ou à la dernière.
         */
        public Builder paged(List<Button> items, List<Integer> slots, int page, IntFunction<Menu> open) {
            if (slots.isEmpty()) {
                throw new IllegalArgumentException("Aucune case pour la liste");
            }
            int pages = pageCount(items.size(), slots.size());
            int shown = Math.clamp(page, 0, pages - 1);
            int first = shown * slots.size();
            for (int i = 0; i < slots.size() && first + i < items.size(); i++) {
                set(slots.get(i), items.get(first + i));
            }
            int bottom = (rows - 1) * COLUMNS;
            this.paging = new Paging(shown, pages, open, bottom + 3, bottom + 5);
            return this;
        }

        public Menu build() {
            return new Menu(title, rows, buttons, onClose, backSlot, art, paging);
        }
    }
}
