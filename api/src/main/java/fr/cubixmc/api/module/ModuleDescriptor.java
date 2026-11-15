package fr.cubixmc.api.module;

import fr.cubixmc.api.ApiVersion;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Description d'un module, lue dans le fichier {@code cubix-module.json} à la racine de son jar.
 *
 * @param id identifiant unique : minuscules, chiffres et tirets (ex. {@code hub}, {@code rush-core})
 * @param name nom affiché
 * @param version version du module
 * @param main classe qui implémente {@link CubixModule}
 * @param apiVersion version d'API visée (voir {@link ApiVersion#canRun})
 * @param depends modules requis, démarrés avant celui-ci
 * @param softDepends modules facultatifs, démarrés avant celui-ci s'ils sont présents
 */
public record ModuleDescriptor(
        String id,
        String name,
        String version,
        String main,
        ApiVersion apiVersion,
        List<String> depends,
        List<String> softDepends) {

    private static final Pattern ID = Pattern.compile("^[a-z][a-z0-9-]{1,31}$");

    public ModuleDescriptor {
        if (!ID.matcher(id).matches()) {
            throw new IllegalArgumentException(
                    "Identifiant de module invalide : '" + id + "' (minuscules, chiffres, tirets, 2 à 32 caractères)");
        }
        if (name.isBlank() || version.isBlank() || main.isBlank()) {
            throw new IllegalArgumentException("Module '" + id + "' : name, version et main sont obligatoires");
        }
        depends = List.copyOf(depends);
        softDepends = List.copyOf(softDepends);
        if (depends.contains(id) || softDepends.contains(id)) {
            throw new IllegalArgumentException("Module '" + id + "' : il ne peut pas dépendre de lui-même");
        }
    }
}
