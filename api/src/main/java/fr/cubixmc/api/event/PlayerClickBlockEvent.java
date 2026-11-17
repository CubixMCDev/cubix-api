package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.world.Position;

/**
 * Un joueur fait un clic droit sur un bloc (main principale).
 *
 * @param block coordonnées entières du bloc
 * @param blockKey son type ({@code minecraft:oak_wall_sign}…)
 */
public record PlayerClickBlockEvent(CubixPlayer player, Position block, String blockKey) implements CubixEvent {}
