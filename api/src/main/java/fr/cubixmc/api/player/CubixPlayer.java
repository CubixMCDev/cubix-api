package fr.cubixmc.api.player;

import fr.cubixmc.api.economy.Wallet;
import fr.cubixmc.api.menu.Menu;
import fr.cubixmc.api.menu.MenuView;
import fr.cubixmc.api.rank.Rank;
import fr.cubixmc.api.sidebar.Sidebar;
import fr.cubixmc.api.world.Position;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.audience.Audience;
import org.jspecify.annotations.Nullable;

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

    /** Son niveau (1 au départ). */
    int level();

    /** Son expérience dans ce niveau. */
    int experience();

    /** L'expérience qu'il faut pour passer au niveau suivant. */
    int experienceNeeded();

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

    /** L'envoie sur ce serveur du réseau (hub choisi dans un menu…). */
    void connect(String server);

    /**
     * Change sa langue pour tout le réseau, comme {@code /lang}.
     *
     * @param locale une langue proposée, ou {@code null} pour suivre celle de son client
     */
    void setLanguage(@Nullable Locale locale);

    /** Où il se trouve (ses pieds). */
    Position position();

    /** Le téléporte sur ce serveur, en gardant son regard. */
    void teleport(Position position);

    /** Le ramène au point d'apparition du serveur. */
    void teleportToSpawn();

    /**
     * S'il se déconnecte, peut-il revenir ici (dans les 15 minutes) ? Un jeu le met à vrai au
     * début de la partie, à faux quand le joueur est éliminé ou que la partie se termine.
     */
    void setRejoinable(boolean rejoinable);

    /** Une de ses préférences, lue à son arrivée sur ce serveur. Ne bloque jamais. */
    <T> T preference(Preference<T> preference);

    /** Change une de ses préférences, pour tout le réseau. */
    <T> CompletableFuture<Void> setPreference(Preference<T> preference, T value);

    /** Cache (ou montre à nouveau) les autres joueurs à ses yeux, sur ce serveur. Le staff reste visible. */
    void setPlayersHidden(boolean hidden);

    /** Les autres joueurs lui sont-ils cachés ? */
    boolean playersHidden();
}
