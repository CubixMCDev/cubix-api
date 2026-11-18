package fr.cubixmc.api.hologram;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.world.Position;
import java.util.List;
import java.util.function.Function;
import net.kyori.adventure.text.Component;

/**
 * Les hologrammes d'un module, retirés tout seuls à son arrêt.
 *
 * <p>Sur Java, un texte d'affichage léger ; sur Bedrock, une ligne par armure invisible (Geyser
 * espace mal les lignes des textes d'affichage). Le module n'a rien à faire pour ça.
 *
 * <pre>{@code
 * context.holograms().create(new Position(0.5, 43, 6.5), player -> List.of(
 *         messages.render(player, WELCOME_TITLE),
 *         messages.render(player, WELCOME_LINE, Placeholder.text("joueur", player.name()))));
 * }</pre>
 */
public interface Holograms {

    /** Écart vertical entre deux lignes, en blocs. */
    double LINE_HEIGHT = 0.28;

    /**
     * Crée un hologramme, montré à chaque joueur présent ou qui arrive.
     *
     * @param position la première ligne (la plus haute) ; les suivantes descendent
     * @param content les lignes pour ce joueur ; une liste vide ne montre rien
     */
    Hologram create(Position position, Function<CubixPlayer, List<Component>> content);

    /**
     * Un titre : le même hologramme, agrandi et sans fond (au-dessus d'un portail, d'une zone).
     * Bedrock ne sait pas agrandir : il voit un hologramme ordinaire.
     *
     * @param scale l'agrandissement (2 ou 3 pour un titre)
     */
    Hologram title(Position position, float scale, Function<CubixPlayer, List<Component>> content);
}
