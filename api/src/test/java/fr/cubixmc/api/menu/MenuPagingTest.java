package fr.cubixmc.api.menu;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.IntStream;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;

class MenuPagingTest {

    private static final List<Button> ITEMS = IntStream.range(0, 30)
            .mapToObj(i -> Button.decoration(Icon.of("minecraft:paper", Component.text(i))))
            .toList();

    private static Menu page(int page) {
        return Menu.builder(Component.text("Liste"), 4)
                .paged(ITEMS, Menu.inner(4), page, MenuPagingTest::page)
                .build();
    }

    @Test
    void itemsAreSpreadOverPages() {
        assertThat(Menu.inner(4)).hasSize(14).startsWith(10).endsWith(25);
        Menu first = page(0);
        assertThat(first.paging()).hasValueSatisfying(paging -> {
            assertThat(paging.pages()).isEqualTo(3);
            assertThat(paging.page()).isZero();
            assertThat(paging.previousSlot()).isEqualTo(30);
            assertThat(paging.nextSlot()).isEqualTo(32);
        });
        assertThat(first.buttons()).hasSize(14);
        assertThat(page(2).buttons()).hasSize(2);
    }

    @Test
    void outOfRangePagesAreClamped() {
        assertThat(page(9).paging().orElseThrow().page()).isEqualTo(2);
        assertThat(page(-1).paging().orElseThrow().page()).isZero();
        assertThat(Menu.pageCount(0, 14)).isEqualTo(1);
    }
}
