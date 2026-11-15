package fr.cubixmc.api.event;

/** Un abonnement en cours. */
public interface Subscription {

    /** Arrête l'abonnement. Sans effet s'il est déjà arrêté. */
    void unsubscribe();
}
