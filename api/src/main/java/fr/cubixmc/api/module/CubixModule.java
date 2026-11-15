package fr.cubixmc.api.module;

/**
 * Point d'entrée d'un module.
 *
 * <p>La classe indiquée par {@code main} dans {@code cubix-module.json} implémente cette interface
 * et possède un constructeur public sans argument. Le moteur l'instancie une seule fois : pas de
 * rechargement à chaud.
 */
public interface CubixModule {

    /**
     * Appelé au démarrage du serveur, une fois les dépendances du module démarrées.
     *
     * @throws Exception toute erreur empêche le module, et ceux qui en dépendent, de démarrer
     */
    void onEnable(ModuleContext context) throws Exception;

    /**
     * Appelé à l'arrêt du serveur, dans l'ordre inverse du démarrage. Les abonnements et les tâches
     * du module sont annulés automatiquement juste après.
     */
    default void onDisable() throws Exception {}
}
