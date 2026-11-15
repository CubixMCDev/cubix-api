package fr.cubixmc.api.menu;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Une case de menu : son icône, et ce que fait un clic dessus.
 *
 * @param onClick action, sur le thread de jeu ; {@code null} pour une simple décoration
 */
public record Button(Icon icon, @Nullable ClickHandler onClick) {

    /** Ce que fait un clic. Appelé sur le thread de jeu. */
    @FunctionalInterface
    public interface ClickHandler {
        void onClick(MenuClick click);
    }

    public static Button of(Icon icon, ClickHandler onClick) {
        return new Button(icon, onClick);
    }

    public static Button decoration(Icon icon) {
        return new Button(icon, null);
    }

    public Optional<ClickHandler> handler() {
        return Optional.ofNullable(onClick);
    }
}
