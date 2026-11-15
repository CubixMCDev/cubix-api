package fr.cubixmc.api.player;

import fr.cubixmc.api.economy.Wallet;
import fr.cubixmc.api.menu.Menu;
import fr.cubixmc.api.menu.MenuView;
import fr.cubixmc.api.rank.Rank;
import fr.cubixmc.api.sidebar.Sidebar;
import java.util.Locale;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;

/**
 * Un joueur connecté à ce serveur.
 *
 * <p>Hérite d'{@link Audience} : messages, titres, sons, barre d'action, etc.
 */
public interface CubixPlayer extends Audience {

    UUID uuid();

    String name();

    /** Joueur Bedrock (via Geyser) : le moteur adapte alors menus et affichages. */
    boolean isBedrock();

    /** Langue des messages : choisie par le joueur, sinon celle de son client, sinon le français. */
    Locale locale();

    /** Grade actuel. Suit les changements faits ailleurs sur le réseau. */
    Rank rank();

    /** Permission accordée par le grade du joueur ({@code cubix.staff}, {@code cubix.perk.hub.fly}…). */
    boolean hasPermission(String permission);

    /** Ses coins et cristaux. */
    Wallet wallet();

    /** Ouvre un menu (en ferme un autre s'il y en avait un). Sur le thread de jeu. */
    MenuView openMenu(Menu menu);

    /** Ferme le menu ouvert, s'il y en a un. */
    void closeMenu();

    /** Sa barre de côté. */
    Sidebar sidebar();

    /** Les objets verrouillés de sa barre rapide. */
    Hotbar hotbar();

    /**
     * Cherche une partie pour lui (et son groupe s'il en est le chef), comme {@code /play <mode>} :
     * le proxy l'envoie sur un serveur libre, ou le met dans la file. Il est prévenu dans sa langue
     * si le mode n'existe pas ou s'il n'est pas le chef de son groupe.
     *
     * @param mode type des serveurs de jeu ({@code rush_1v1}, {@code hungergames}…)
     */
    void play(String mode);
}
