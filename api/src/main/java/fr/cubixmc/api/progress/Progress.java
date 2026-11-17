package fr.cubixmc.api.progress;

import fr.cubixmc.api.player.CubixPlayer;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;

/**
 * La progression des joueurs : statistiques, quêtes du jour, succès, niveau.
 *
 * <p>Un jeu n'a qu'une chose à faire : signaler ce qui se passe avec {@link #record}
 * ({@code record(joueur, "rush", "kill", 1)}). La statistique est comptée, les quêtes du jour qui
 * comptent cette action avancent, les succès atteints se débloquent et se paient, et le joueur
 * est prévenu dans sa langue. Les actions connues : {@code play}, {@code win}, {@code kill},
 * {@code death}, {@code bow_kill}, {@code bed}, {@code golden_apple}, {@code top3},
 * {@code chest}.
 */
public interface Progress {

    record Quest(String id, String category, String game, String action, int target, int xp, int coins) {}

    record QuestState(Quest quest, long progress, boolean claimed) {

        public boolean done() {
            return progress >= quest.target();
        }
    }

    /**
     * Un succès.
     *
     * @param icon objet Minecraft qui le représente ({@code minecraft:gold_ingot})
     */
    record Achievement(String id, String icon, int target, int coins, int xp) {}

    record AchievementState(Achievement achievement, long progress, boolean unlocked) {}

    /** Une ligne de classement. */
    record Ranked(String name, long value) {}

    enum Claim {
        CLAIMED,
        NOT_DONE,
        ALREADY_CLAIMED,
        /** Ce n'est pas une quête du jour. */
        NOT_TODAY
    }

    /**
     * Le joueur vient de faire quelque chose. En arrière-plan ; ne bloque jamais.
     *
     * @param game {@code rush}, {@code hungergames}, ou un mini-jeu du hub
     */
    void record(CubixPlayer player, String game, String action, int amount);

    /** Les quêtes du jour (les mêmes pour tout le réseau), et l'avancement du joueur. */
    CompletableFuture<List<QuestState>> quests(CubixPlayer player);

    /** Réclame la récompense d'une quête terminée. */
    CompletableFuture<Claim> claim(CubixPlayer player, String questId);

    /** Tous les succès, et l'avancement du joueur. */
    CompletableFuture<List<AchievementState>> achievements(CubixPlayer player);

    /** Les compteurs d'un joueur, par jeu puis par action. */
    CompletableFuture<Map<String, Map<String, Long>>> stats(UUID player);

    /** Les meilleurs sur un compteur. */
    CompletableFuture<List<Ranked>> top(String game, String action, int limit);

    /** Le classement du mois en cours : remis à zéro le 1er, à l'heure de Bruxelles. */
    CompletableFuture<List<Ranked>> topOfMonth(String game, String action, int limit);

    /** Le nom d'une quête, dans cette langue. */
    Component questName(Locale locale, String questId);

    /** Le nom d'un succès, dans cette langue. */
    Component achievementName(Locale locale, String achievementId);

    /** Ce qu'il faut faire pour un succès, dans cette langue. */
    Component achievementDescription(Locale locale, String achievementId);
}
