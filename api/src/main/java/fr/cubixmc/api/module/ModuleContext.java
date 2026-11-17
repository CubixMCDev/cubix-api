package fr.cubixmc.api.module;

import fr.cubixmc.api.event.EventBus;
import fr.cubixmc.api.hologram.Holograms;
import fr.cubixmc.api.npc.Npcs;
import fr.cubixmc.api.scheduler.Scheduler;
import fr.cubixmc.api.server.CubixServer;
import fr.cubixmc.api.text.Messages;
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

    /** Messages du module, lus dans les fichiers {@code lang/*.properties} de son jar. */
    Messages messages();

    /** Hologrammes du module, retirés automatiquement à son arrêt. */
    Holograms holograms();

    /** Les personnages cliquables de ce module, retirés à son arrêt. */
    Npcs npcs();
}
