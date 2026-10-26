package kz.aitu.navigator.model;

/**
 * A named point on the map, given by its geographic coordinates.
 */
public record Location(String name, double latitude, double longitude) {
}
