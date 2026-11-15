package fr.cubixmc.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

/** Informations sur l'API elle-même. */
public final class CubixApi {

    private static final ApiVersion VERSION = loadVersion();

    private CubixApi() {}

    /** Version de l'API présente au runtime. */
    public static ApiVersion version() {
        return VERSION;
    }

    private static ApiVersion loadVersion() {
        try (InputStream in = CubixApi.class.getResourceAsStream("cubix-api.properties")) {
            if (in == null) {
                throw new IllegalStateException("cubix-api.properties introuvable dans le jar de l'API");
            }
            Properties properties = new Properties();
            properties.load(in);
            return ApiVersion.parse(properties.getProperty("version", ""));
        } catch (IOException e) {
            throw new UncheckedIOException("Lecture de la version de l'API impossible", e);
        }
    }
}
