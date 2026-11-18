package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;
import org.jspecify.annotations.Nullable;

/**
 * Un joueur présent ici vient d'entrer dans une file d'attente, ou d'en sortir.
 *
 * @param mode le mode attendu ; {@code null} : il n'attend plus
 */
public record PlayerQueueEvent(CubixPlayer player, @Nullable String mode) implements CubixEvent {}
