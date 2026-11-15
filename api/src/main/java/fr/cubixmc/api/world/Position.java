package fr.cubixmc.api.world;

/** Un point du monde de ce serveur. */
public record Position(double x, double y, double z) {

    /** Le même point, déplacé. */
    public Position add(double dx, double dy, double dz) {
        return new Position(x + dx, y + dy, z + dz);
    }
}
