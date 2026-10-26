package kz.aitu.navigator.strategy;

import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;
import kz.aitu.navigator.util.GeoMath;

/**
 * Plans a route by city bus: longer path along bus lines, time to walk to the stop
 * and wait for the bus, fixed ticket price.
 */
public class PublicTransportStrategy implements RouteStrategy {

    private static final double BUS_LINE_DETOUR_FACTOR = 1.4;
    private static final double AVERAGE_BUS_SPEED_KMH = 22.0;
    private static final int WALK_TO_STOP_MINUTES = 6;
    private static final int AVERAGE_WAITING_MINUTES = 8;
    private static final double TICKET_PRICE_KZT = 120.0;

    @Override
    public String modeName() {
        return "Public transport";
    }

    @Override
    public Route buildRoute(Location from, Location to) {
        double busDistanceKm = GeoMath.straightLineKm(from, to) * BUS_LINE_DETOUR_FACTOR;
        int durationMinutes = WALK_TO_STOP_MINUTES + AVERAGE_WAITING_MINUTES
                + GeoMath.travelMinutes(busDistanceKm, AVERAGE_BUS_SPEED_KMH);

        return new Route(modeName(), from, to, busDistanceKm, durationMinutes, TICKET_PRICE_KZT,
                "City bus, includes walking to the stop and waiting");
    }
}
