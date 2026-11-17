package fr.cubixmc.api.npc;

import net.kyori.adventure.text.Component;

/** Un personnage cliquable. */
public interface Npc {

    /** Change le nom affiché au-dessus. */
    void setName(Component name);

    void remove();
}
