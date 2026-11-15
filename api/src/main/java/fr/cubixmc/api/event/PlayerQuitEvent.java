package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;

/** Un joueur quitte ce serveur (déconnexion ou changement de serveur). */
public record PlayerQuitEvent(CubixPlayer player) implements CubixEvent {}
