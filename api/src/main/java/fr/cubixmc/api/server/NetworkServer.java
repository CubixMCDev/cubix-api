package fr.cubixmc.api.server;

/**
 * Un serveur du réseau, tel qu'il s'est annoncé il y a quelques secondes.
 *
 * @param name nom unique ({@code hub-1}, {@code rush-1v1-7lwzk})
 * @param type son type ({@code hub}, {@code rush_1v1}…), celui que l'on donne à {@code play}
 * @param joinable accepte-t-il des joueurs (hub, ou partie en attente) ?
 */
public record NetworkServer(String name, String type, int players, int maxPlayers, boolean joinable) {}
