package fr.cubixmc.api.scheduler;

/** Une tâche planifiée. */
public interface Task {

    void cancel();

    boolean isCancelled();
}
