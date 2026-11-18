package fr.cubixmc.api.server;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.Optional;

/**
 * Les files d'attente des jeux (tenues par le proxy) : {@code /play <mode>} y place un joueur
 * quand tous les serveurs sont pleins.
 */
public interface Queues {

    /** Le mode où ce joueur attend, s'il attend. */
    Optional<String> mode(CubixPlayer player);

    /** Joueurs en attente sur le réseau pour les modes qui commencent ainsi ({@code rush}). */
    int waiting(String modePrefix);

    /** Fait quitter sa file au joueur (le proxy le lui confirme). */
    void leave(CubixPlayer player);
}
