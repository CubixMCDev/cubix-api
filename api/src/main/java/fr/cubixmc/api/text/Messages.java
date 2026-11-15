package fr.cubixmc.api.text;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.Locale;
import net.kyori.adventure.text.Component;

/**
 * Les messages d'un module, dans la langue de chaque joueur.
 *
 * <p>La langue est celle choisie par le joueur, sinon celle de son client, sinon le français. Une
 * clé absente d'une langue est cherchée en français ; absente partout, elle s'affiche telle quelle
 * (et un test du module doit l'avoir signalé avant).
 */
public interface Messages {

    /** Envoie le message, sauf si l'anti-spam le retient (voir {@link MessageKey}). */
    boolean send(CubixPlayer player, MessageKey key, Placeholder... placeholders);

    /** Le message dans la langue du joueur, sans l'envoyer (titre, objet, menu…). */
    Component render(CubixPlayer player, MessageKey key, Placeholder... placeholders);

    Component render(Locale locale, MessageKey key, Placeholder... placeholders);
}
