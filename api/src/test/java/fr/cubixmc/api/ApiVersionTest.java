package fr.cubixmc.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ApiVersionTest {

    @Test
    void readsShortFullAndSnapshotForms() {
        assertThat(ApiVersion.parse("1.2")).isEqualTo(new ApiVersion(1, 2));
        assertThat(ApiVersion.parse("1.2.3")).isEqualTo(new ApiVersion(1, 2));
        assertThat(ApiVersion.parse(" 0.1.0-SNAPSHOT ")).isEqualTo(new ApiVersion(0, 1));
    }

    @Test
    void rejectsGarbage() {
        assertThatThrownBy(() -> ApiVersion.parse("")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ApiVersion.parse("v1")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> ApiVersion.parse("1")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void stableApiRunsOlderMinorOfSameMajor() {
        ApiVersion server = new ApiVersion(1, 3);
        assertThat(server.canRun(new ApiVersion(1, 0))).isTrue();
        assertThat(server.canRun(new ApiVersion(1, 3))).isTrue();
        assertThat(server.canRun(new ApiVersion(1, 4))).isFalse();
        assertThat(server.canRun(new ApiVersion(2, 0))).isFalse();
    }

    @Test
    void zeroMajorNeedsExactMinor() {
        ApiVersion server = new ApiVersion(0, 2);
        assertThat(server.canRun(new ApiVersion(0, 2))).isTrue();
        assertThat(server.canRun(new ApiVersion(0, 1))).isFalse();
        assertThat(server.canRun(new ApiVersion(0, 3))).isFalse();
    }

    @Test
    void runtimeVersionComesFromTheJar() {
        assertThat(CubixApi.version()).isEqualTo(new ApiVersion(0, 1));
    }
}
