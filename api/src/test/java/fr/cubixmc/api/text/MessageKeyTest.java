package fr.cubixmc.api.text;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class MessageKeyTest {

    @Test
    void keysAreDottedLowercase() {
        assertThat(MessageKey.of("hub.welcome").cooldown()).isZero();
        assertThat(MessageKey.throttled("queue.wait-a-bit", Duration.ofSeconds(5))
                        .cooldown())
                .isEqualTo(Duration.ofSeconds(5));
        assertThatThrownBy(() -> MessageKey.of("welcome")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MessageKey.of("Hub.Welcome")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> MessageKey.throttled("a.b", Duration.ofSeconds(-1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void placeholderNamesFollowMiniMessageRules() {
        assertThat(Placeholder.text("joueur", "Pipo")).isEqualTo(new Placeholder.Text("joueur", "Pipo"));
        assertThat(Placeholder.text("nombre", 3)).isEqualTo(new Placeholder.Text("nombre", "3"));
        assertThatThrownBy(() -> Placeholder.text("Joueur", "x")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Placeholder.text("un joueur", "x")).isInstanceOf(IllegalArgumentException.class);
    }
}
