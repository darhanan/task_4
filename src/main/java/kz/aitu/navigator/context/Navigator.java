package kz.aitu.navigator.context;

import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;
import kz.aitu.navigator.strategy.RouteStrategy;

import java.util.Objects;

/**
 * Context of the Strategy pattern.
 * Keeps a reference to the current {@link RouteStrategy} and delegates route planning to it.
 * The strategy can be replaced at any time while the program is running.
 */
public final class Navigator {

    private RouteStrategy strategy;

    public Navigator(RouteStrategy strategy) {
        setStrategy(strategy);
    }

    public void setStrategy(RouteStrategy strategy) {
        this.strategy = Objects.requireNonNull(strategy, "Route strategy must not be null");
    }

    public RouteStrategy getStrategy() {
        return strategy;
    }

    public Route planRoute(Location from, Location to) {
        return strategy.buildRoute(from, to);
    }
}
