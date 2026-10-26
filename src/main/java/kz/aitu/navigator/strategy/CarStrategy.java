package kz.aitu.navigator.strategy;

import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;
import kz.aitu.navigator.util.GeoMath;

/**
 * Plans a route by car: follows the road network, pays for fuel
 * and spends a few minutes looking for parking.
 */
public class CarStrategy implements RouteStrategy {

    private static final double ROAD_DETOUR_FACTOR = 1.35;
    private static final double AVERAGE_CITY_SPEED_KMH = 35.0;
    private static final double FUEL_LITRES_PER_KM = 0.08;
    private static final double FUEL_PRICE_KZT_PER_LITRE = 300.0;
    private static final int PARKING_SEARCH_MINUTES = 5;

    @Override
    public String modeName() {
        return "Car";
    }

    @Override
    public Route buildRoute(Location from, Location to) {
        double roadDistanceKm = GeoMath.straightLineKm(from, to) * ROAD_DETOUR_FACTOR;
        int durationMinutes = GeoMath.travelMinutes(roadDistanceKm, AVERAGE_CITY_SPEED_KMH) + PARKING_SEARCH_MINUTES;
        double fuelCostKzt = roadDistanceKm * FUEL_LITRES_PER_KM * FUEL_PRICE_KZT_PER_LITRE;

        return new Route(modeName(), from, to, roadDistanceKm, durationMinutes, fuelCostKzt,
                "Main roads and avenues, includes time to find parking");
    }
}
