package kz.aitu.navigator.model;

/**
 * The result of route planning: how far, how long and how expensive the trip is
 * for one particular way of travelling.
 */
public record Route(String travelMode,
                    Location from,
                    Location to,
                    double distanceKm,
                    int durationMinutes,
                    double costKzt,
                    String description) {
}
