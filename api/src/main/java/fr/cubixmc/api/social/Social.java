package fr.cubixmc.api.social;

import fr.cubixmc.api.player.CubixPlayer;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.jspecify.annotations.Nullable;

/**
 * Amis, groupe et profils, lus pour tout le réseau (ce qu'un menu affiche). Pour agir (ajouter un
 * ami, inviter, quitter un groupe…), {@link CubixPlayer#performCommand(String)} lance la commande
 * du proxy : mêmes règles, mêmes messages, préférences respectées.
 */
public interface Social {

    /**
     * Un ami.
     *
     * @param server son serveur s'il est en ligne, sinon {@code null}
     */
    record Friend(UUID uuid, String name, @Nullable String server) {

        public boolean online() {
            return server != null;
        }
    }

    /** Un membre du groupe ; {@code server} vaut {@code null} s'il n'est pas en ligne. */
    record Member(
            UUID uuid,
            String name,
            boolean leader,
            @Nullable String server) {

        public boolean online() {
            return server != null;
        }
    }

    /** Un groupe, chef en premier. */
    record Party(UUID leader, List<Member> members, int maxSize) {}

    /**
     * Ce que le réseau sait d'un joueur.
     *
     * @param server son serveur s'il est en ligne, sinon {@code null}
     */
    record Profile(
            UUID uuid,
            String name,
            String rankId,
            int level,
            Instant firstSeen,
            Instant lastSeen,
            long playtimeSeconds,
            @Nullable String server) {

        public boolean online() {
            return server != null;
        }
    }

    /** Ses amis, en ligne d'abord, puis par nom. */
    CompletableFuture<List<Friend>> friends(CubixPlayer player);

    /** Les pseudos de ceux qui lui ont demandé d'être amis (demandes encore valables). */
    CompletableFuture<List<String>> friendRequests(CubixPlayer player);

    CompletableFuture<Boolean> areFriends(CubixPlayer player, UUID other);

    /** Son groupe, s'il en a un. */
    CompletableFuture<Optional<Party>> party(CubixPlayer player);

    /** Les chefs qui l'ont invité dans leur groupe (invitations encore valables). */
    CompletableFuture<List<String>> partyInvites(CubixPlayer player);

    /** Le profil d'un joueur, connecté ou non. */
    CompletableFuture<Optional<Profile>> profile(String name);
}
