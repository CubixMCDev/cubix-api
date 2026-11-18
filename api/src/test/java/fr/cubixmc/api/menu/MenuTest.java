package fr.cubixmc.api.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class MenuTest {

    private static final Icon GLASS = Icon.filler("minecraft:gray_stained_glass_pane");
    private static final Icon SWORD = Icon.of("minecraft:iron_sword", Component.text("Rush"));

    @Test
    void theBorderSurroundsTheInsideAndKeepsWhatIsAlreadyThere() {
        Button play = Button.of(SWORD, click -> {});
        Menu menu = Menu.builder(Component.text("Jeux"), 3)
                .set(0, play)
                .border(GLASS)
                .build();

        assertThat(menu.size()).isEqualTo(27);
        assertThat(menu.buttons()).hasSize(20);
        assertThat(menu.button(0)).contains(play);
        assertThat(menu.button(13)).as("le centre reste vide").isEmpty();
        assertThat(menu.button(9))
                .hasValueSatisfying(b -> assertThat(b.handler()).isEmpty());
    }

    @Test
    void rowsAndColumnsCountFromZero() {
        Menu menu = Menu.builder(Component.text("Jeux"), 6)
                .set(2, 4, SWORD)
                .fill(GLASS)
                .build();

        assertThat(menu.button(22))
                .hasValueSatisfying(button -> assertThat(button.icon()).isEqualTo(SWORD));
        assertThat(menu.buttons()).hasSize(54);
    }

    @Test
    void outOfBoundsIsRefusedClearly() {
        assertThatThrownBy(() -> Menu.builder(Component.text("x"), 7)).hasMessageContaining("6 lignes");
        assertThatThrownBy(() -> Menu.builder(Component.text("x"), 1).set(9, SWORD))
                .hasMessageContaining("hors du menu");
        assertThatThrownBy(() -> Menu.builder(Component.text("x"), 2).set(0, 9, Button.decoration(SWORD)))
                .hasMessageContaining("Colonne");
        assertThatThrownBy(() -> SWORD.withAmount(0)).hasMessageContaining("1..99");
    }

    @Test
    void theBackButtonGoesBottomLeftUnlessMovedOrRemoved() {
        assertThat(Menu.builder(Component.text("x"), 3).build().backSlot()).isEqualTo(18);
        assertThat(Menu.builder(Component.text("x"), 1).build().backSlot()).isZero();
        assertThat(Menu.builder(Component.text("x"), 3).backSlot(22).build().backSlot())
                .isEqualTo(22);
        assertThat(Menu.builder(Component.text("x"), 3).withoutBack().build().backSlot())
                .isEqualTo(Menu.NO_BACK);
        assertThatThrownBy(() -> Menu.builder(Component.text("x"), 1).backSlot(9))
                .hasMessageContaining("hors du menu");
    }

    @Test
    void iconsAreImmutable() {
        Icon lored = SWORD.withLore(Component.text("Clic : rejoindre")).glowing();

        assertThat(SWORD.lore()).isEmpty();
        assertThat(SWORD.glow()).isFalse();
        assertThat(lored.lore()).hasSize(1);
        assertThat(lored.glow()).isTrue();
        assertThat(Icon.head("Pipo", Component.text("Profil")).head()).isEqualTo(new Icon.Head.Owner("Pipo"));
    }

    @Test
    void artIsOptional() {
        assertThat(Menu.builder(Component.text("x"), 3).build().art()).isEmpty();
        assertThat(Menu.builder(Component.text("x"), 3).art("profil").build().art())
                .contains("profil");
    }
}
