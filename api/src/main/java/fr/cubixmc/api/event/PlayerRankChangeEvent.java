package fr.cubixmc.api.event;

import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.rank.Rank;

/**
 * Le grade d'un joueur présent sur ce serveur vient de changer (commande, achat, changement fait
 * depuis un autre serveur). {@link CubixPlayer#rank()} renvoie déjà le nouveau grade.
 */
public record PlayerRankChangeEvent(CubixPlayer player, Rank previous, Rank current) implements CubixEvent {}
