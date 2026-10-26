package kz.aitu.navigator.strategy;

import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;

/**
 * Strategy interface: one way of planning a route between two locations.
 * Every travel mode implements this interface, so the Navigator can use any of them
 * without knowing which one it is.
 */
public interface RouteStrategy {

    /** Human-readable name of the travel mode, e.g. "Car". */
    String modeName();

    /** Plans a route from {@code from} to {@code to} using this travel mode. */
    Route buildRoute(Location from, Location to);
}
