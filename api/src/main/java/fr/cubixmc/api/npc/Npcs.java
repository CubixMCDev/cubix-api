package fr.cubixmc.api.npc;

import fr.cubixmc.api.menu.Icon;
import fr.cubixmc.api.player.CubixPlayer;
import fr.cubixmc.api.world.Position;
import java.util.function.Consumer;
import net.kyori.adventure.text.Component;

/**
 * Les personnages cliquables d'un module, retirés à son arrêt : un objet ou une tête porté par un
 * socle invisible, visible et cliquable sur Java comme sur Bedrock.
 */
public interface Npcs {

    /**
     * Fait apparaître un personnage.
     *
     * @param look ce que le personnage porte (une tête, un bloc…)
     * @param name le nom au-dessus, le même pour tous ; {@code Component.empty()} n'en montre pas
     *     (un hologramme écrit alors le nom dans la langue de chacun)
     * @param onClick appelé au clic droit, sur le thread de jeu
     */
    Npc spawn(Position position, Icon look, Component name, Consumer<CubixPlayer> onClick);
}
