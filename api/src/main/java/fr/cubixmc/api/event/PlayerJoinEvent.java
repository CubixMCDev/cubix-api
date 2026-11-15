package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;

/** Un joueur vient d'arriver sur ce serveur et se trouve dans le monde. */
public record PlayerJoinEvent(CubixPlayer player) implements CubixEvent {}
