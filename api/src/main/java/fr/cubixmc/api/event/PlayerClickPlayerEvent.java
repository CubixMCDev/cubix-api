package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;

/**
 * Un joueur fait un clic droit sur un autre (main principale).
 *
 * @param sneaking s'il tenait Maj enfoncé
 */
public record PlayerClickPlayerEvent(CubixPlayer player, CubixPlayer target, boolean sneaking) implements CubixEvent {}
