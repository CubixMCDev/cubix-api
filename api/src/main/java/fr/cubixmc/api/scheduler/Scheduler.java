package fr.cubixmc.api.scheduler;

import java.time.Duration;

/**
 * Planification de tâches.
 *
 * <p>{@link #runLater} et {@link #runRepeating} s'exécutent sur le thread de jeu ; {@link #runAsync}
 * sur un thread virtuel, pour le réseau ou la base de données.
 */
public interface Scheduler {

    Task runLater(Duration delay, Runnable task);

    Task runRepeating(Duration initialDelay, Duration period, Runnable task);

    Task runAsync(Runnable task);
}
