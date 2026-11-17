package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;

/** Un joueur vient de sauter (il quitte le sol en montant). */
public record PlayerJumpEvent(CubixPlayer player) implements CubixEvent {}
