package kz.aitu.navigator.strategy;

import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;
import kz.aitu.navigator.util.GeoMath;

/**
 * Plans a cycling route: prefers bike lanes, faster than walking, no fuel cost.
 */
public class CycleStrategy implements RouteStrategy {

    private static final double BIKE_LANE_DETOUR_FACTOR = 1.2;
    private static final double CYCLING_SPEED_KMH = 15.0;

    @Override
    public String modeName() {
        return "Cycle";
    }

    @Override
    public Route buildRoute(Location from, Location to) {
        double cyclingDistanceKm = GeoMath.straightLineKm(from, to) * BIKE_LANE_DETOUR_FACTOR;
        int durationMinutes = GeoMath.travelMinutes(cyclingDistanceKm, CYCLING_SPEED_KMH);

        return new Route(modeName(), from, to, cyclingDistanceKm, durationMinutes, 0.0,
                "Bike lanes where available, quiet streets otherwise");
    }
}
