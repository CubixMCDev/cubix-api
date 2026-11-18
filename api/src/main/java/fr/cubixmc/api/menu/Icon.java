package fr.cubixmc.api.menu;

import java.util.List;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

/**
 * Ce qu'affiche une case de menu : un objet Minecraft, son nom et sa description.
 *
 * <p>Immuable : chaque méthode {@code with…} renvoie une nouvelle icône. Le nom et la description
 * ne sont pas en italique (contrairement au défaut de Minecraft).
 *
 * <pre>{@code
 * Icon sword = Icon.of("minecraft:iron_sword", Component.text("Rush"))
 *         .withLore(Component.text("Clic : rejoindre"))
 *         .glowing();
 * Icon head = Icon.head("Pipo", Component.text("Ton profil"));
 * }</pre>
 *
 * @param material identifiant de l'objet ({@code minecraft:diamond}) ; un identifiant inconnu
 *     s'affiche comme une barrière
 * @param amount 1 à 99
 * @param head la tête à afficher, si l'objet est {@code minecraft:player_head}
 * @param model l'icône dessinée par le pack CubixMC ({@code cubix:menu/rush}), montrée à la place
 *     de l'objet chez les joueurs qui ont le pack ; les autres voient {@code material}
 */
public record Icon(
        String material,
        Component name,
        List<Component> lore,
        int amount,
        boolean glow,
        @Nullable Head head,
        @Nullable String model) {

    public static final String PLAYER_HEAD = "minecraft:player_head";

    /** La tête d'un joueur : par son pseudo, ou par une texture encodée en Base64. */
    public sealed interface Head {
        record Owner(String name) implements Head {}

        record Texture(String base64) implements Head {}
    }

    /** Sans icône dessinée. */
    public Icon(String material, Component name, List<Component> lore, int amount, boolean glow, @Nullable Head head) {
        this(material, name, lore, amount, glow, head, null);
    }

    public Icon {
        Objects.requireNonNull(material, "material");
        Objects.requireNonNull(name, "name");
        lore = List.copyOf(lore);
        if (amount < 1 || amount > 99) {
            throw new IllegalArgumentException("Quantité hors de 1..99 : " + amount);
        }
    }

    public static Icon of(String material, Component name) {
        return new Icon(material, name, List.of(), 1, false, null);
    }

    /** Une case de décoration, sans nom. */
    public static Icon filler(String material) {
        return of(material, Component.empty());
    }

    /** La tête d'un joueur (son skin est chargé par le client). */
    public static Icon head(String playerName, Component name) {
        return new Icon(PLAYER_HEAD, name, List.of(), 1, false, new Head.Owner(playerName));
    }

    /** Une tête à texture, comme sur les sites de têtes ({@code eyJ0ZXh0dXJlcyI6…}). */
    public static Icon texture(String base64, Component name) {
        return new Icon(PLAYER_HEAD, name, List.of(), 1, false, new Head.Texture(base64));
    }

    public Icon withLore(Component... lines) {
        return withLore(List.of(lines));
    }

    public Icon withLore(List<Component> lines) {
        return new Icon(material, name, lines, amount, glow, head, model);
    }

    public Icon withName(Component newName) {
        return new Icon(material, newName, lore, amount, glow, head, model);
    }

    public Icon withAmount(int newAmount) {
        return new Icon(material, name, lore, newAmount, glow, head, model);
    }

    /** Fait briller l'objet, comme s'il était enchanté. */
    public Icon glowing() {
        return new Icon(material, name, lore, amount, true, head, model);
    }

    /** Un autre objet (pour qui n'a pas le pack), en gardant l'icône dessinée. */
    public Icon withMaterial(String newMaterial) {
        return new Icon(newMaterial, name, lore, amount, glow, head, model);
    }

    /**
     * L'icône dessinée par le pack ({@code cubix:menu/rush}) : les joueurs qui ont le pack la
     * voient à la place de l'objet. Les icônes disponibles sont listées dans le README du pack.
     */
    public Icon withModel(String newModel) {
        return new Icon(material, name, lore, amount, glow, head, newModel);
    }
}
