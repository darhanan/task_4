package kz.aitu.navigator.util;

import kz.aitu.navigator.model.Location;

/**
 * Small geometry helpers shared by all route strategies.
 */
public final class GeoMath {

    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final int MINUTES_PER_HOUR = 60;

    private GeoMath() {
    }

    /** Great-circle ("as the crow flies") distance between two locations, using the haversine formula. */
    public static double straightLineKm(Location from, Location to) {
        double latDelta = Math.toRadians(to.latitude() - from.latitude());
        double lonDelta = Math.toRadians(to.longitude() - from.longitude());
        double fromLat = Math.toRadians(from.latitude());
        double toLat = Math.toRadians(to.latitude());

        double a = Math.pow(Math.sin(latDelta / 2), 2)
                + Math.cos(fromLat) * Math.cos(toLat) * Math.pow(Math.sin(lonDelta / 2), 2);
        return 2 * EARTH_RADIUS_KM * Math.asin(Math.sqrt(a));
    }

    /** Time in whole minutes needed to cover {@code distanceKm} at {@code speedKmh}. */
    public static int travelMinutes(double distanceKm, double speedKmh) {
        return (int) Math.ceil(distanceKm / speedKmh * MINUTES_PER_HOUR);
    }
}
