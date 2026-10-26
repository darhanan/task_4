package kz.aitu.navigator.strategy;

import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;
import kz.aitu.navigator.util.GeoMath;

/**
 * Plans a walking route: short detours through pedestrian paths, slow but free.
 */
public class WalkStrategy implements RouteStrategy {

    private static final double FOOTPATH_DETOUR_FACTOR = 1.15;
    private static final double WALKING_SPEED_KMH = 5.0;

    @Override
    public String modeName() {
        return "Walk";
    }

    @Override
    public Route buildRoute(Location from, Location to) {
        double walkingDistanceKm = GeoMath.straightLineKm(from, to) * FOOTPATH_DETOUR_FACTOR;
        int durationMinutes = GeoMath.travelMinutes(walkingDistanceKm, WALKING_SPEED_KMH);

        return new Route(modeName(), from, to, walkingDistanceKm, durationMinutes, 0.0,
                "Pedestrian paths, parks and crossings");
    }
}
