/**
 * Messages traduits.
 *
 * <p>Les textes vivent dans {@code lang/fr_fr.properties}, {@code lang/en_us.properties}… à la
 * racine du jar du module, au format <a href="https://docs.advntr.dev/minimessage/format.html">
 * MiniMessage</a>. Le code ne manipule que des {@link fr.cubixmc.api.text.MessageKey}.
 *
 * <p>Couleurs : utiliser les balises de la palette du réseau plutôt que des codes couleur, pour que
 * tous les modules se ressemblent. {@code <prefix:NOM>} (préfixe, ex. {@code <prefix:RUSH>}),
 * {@code <text>} (texte courant), {@code <primary>} (pseudos, valeurs, commandes), {@code
 * <secondary>} (titres), {@code <brand>}, {@code <muted>} (détails), {@code <success>}, {@code
 * <error>}, {@code <warning>}, {@code <line>} (trait pour encadrer un titre).
 */
@NullMarked
package fr.cubixmc.api.text;

import org.jspecify.annotations.NullMarked;
