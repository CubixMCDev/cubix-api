package fr.cubixmc.api.module;

import fr.cubixmc.api.event.EventBus;
import fr.cubixmc.api.scheduler.Scheduler;
import fr.cubixmc.api.server.CubixServer;
import java.nio.file.Path;
import org.slf4j.Logger;

/** Tout ce que le moteur met à disposition d'un module. Un contexte par module. */
public interface ModuleContext {

    ModuleDescriptor descriptor();

    /** Logger au nom du module. */
    Logger logger();

    /** Dossier réservé au module (config, données), créé au besoin. */
    Path dataDirectory();

    CubixServer server();

    /** Abonnements du module, annulés automatiquement à son arrêt. */
    EventBus events();

    /** Tâches du module, annulées automatiquement à son arrêt. */
    Scheduler scheduler();
}
