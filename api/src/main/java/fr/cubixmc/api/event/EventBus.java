package fr.cubixmc.api.event;

import java.util.function.Consumer;

/** Abonnement aux événements. Les écouteurs sont appelés sur le thread de jeu. */
public interface EventBus {

    <E extends CubixEvent> Subscription subscribe(Class<E> type, Consumer<? super E> listener);
}
