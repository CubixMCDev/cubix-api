package fr.cubixmc.api.world;

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
}
