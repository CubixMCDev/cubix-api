package fr.cubixmc.api.world;

import java.util.List;
import net.kyori.adventure.text.Component;

/**
 * Les règles du monde de ce serveur, réglées par son module (le hub les protège toutes, un jeu
 * choisit les siennes). Tout est coupé au départ.
 */
public interface WorldRules {

    /**
     * Monde protégé, comme le hub de la v1 : on ne casse ni ne pose aucun bloc, on n'ouvre rien,
     * on ne jette ni ne ramasse d'objet, personne ne prend de dégâts ni n'a faim. Les joueurs en
     * mode créatif (bâtisseurs) passent outre.
     */
    void setProtected(boolean value);

    boolean isProtected();

    /** Sous cette hauteur, le joueur revient au point d'apparition. */
    void setVoidLevel(double level);

    /** Plus de retour au point d'apparition en tombant. */
    void clearVoidLevel();

    /**
     * Déplacements du lobby, selon le grade : vol pour {@code cubix.perk.hub.fly} et le staff,
     * double, triple ou quadruple saut pour {@code cubix.perk.hub.jump.2} à {@code .4}.
     */
    void setLobbyMovement(boolean value);

    /** Le type du bloc à cet endroit ({@code minecraft:sea_lantern}, {@code minecraft:air}…). */
    String blockAt(Position position);

    /**
     * Écrit sur la face avant d'un panneau déjà posé (quatre lignes au plus). Sans effet si le bloc
     * n'est pas un panneau.
     */
    void setSignText(Position position, List<Component> lines);

    /** Le point d'apparition du serveur. */
    Position spawn();

    /** Fige l'heure du monde ({@code 6000} : midi). Le temps ne passe plus, la météo reste claire. */
    void setFixedTime(long ticks);
}
