package kz.aitu.navigator;

import kz.aitu.navigator.context.Navigator;
import kz.aitu.navigator.model.Location;
import kz.aitu.navigator.model.Route;
import kz.aitu.navigator.strategy.CarStrategy;
import kz.aitu.navigator.strategy.CycleStrategy;
import kz.aitu.navigator.strategy.RouteStrategy;
import kz.aitu.navigator.strategy.WalkStrategy;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * Client of the Strategy pattern.
 * It is the only class that knows the concrete strategies: it creates them, puts them
 * into a registry and gives the selected one to the {@link Navigator}.
 */
public class Main {

    private static final String QUIT_COMMAND = "q";

    private static final Location AITU = new Location("Astana IT University", 51.0906, 71.4180);
    private static final Location BAITEREK = new Location("Baiterek Tower", 51.1283, 71.4305);
    private static final Location KHAN_SHATYR = new Location("Khan Shatyr", 51.1324, 71.4036);

    public static void main(String[] args) {
        Map<String, RouteStrategy> strategies = createStrategyRegistry();
        Navigator navigator = new Navigator(new CarStrategy());

        runDemo(navigator, strategies.values());
        runInteractiveMenu(navigator, strategies);
    }

    /** Menu key -> strategy. A new travel mode only needs one new line here. */
    private static Map<String, RouteStrategy> createStrategyRegistry() {
        Map<String, RouteStrategy> strategies = new LinkedHashMap<>();
        strategies.put("1", new CarStrategy());
        strategies.put("2", new WalkStrategy());
        strategies.put("3", new CycleStrategy());
        return strategies;
    }

    /** Plans the same trip with every strategy, switching the Navigator's strategy at runtime. */
    private static void runDemo(Navigator navigator, Collection<RouteStrategy> strategies) {
        printHeader("DEMO: " + AITU.name() + " -> " + BAITEREK.name() + " (strategy switched at runtime)");
        for (RouteStrategy strategy : strategies) {
            navigator.setStrategy(strategy);
            printRoute(navigator.planRoute(AITU, BAITEREK));
        }
    }

    /** Lets the user pick the travel mode from the keyboard. */
    private static void runInteractiveMenu(Navigator navigator, Map<String, RouteStrategy> strategies) {
        printHeader("INTERACTIVE: " + AITU.name() + " -> " + KHAN_SHATYR.name());
        Scanner scanner = new Scanner(System.in);

        String choice = readChoice(scanner, strategies);
        while (!QUIT_COMMAND.equalsIgnoreCase(choice)) {
            handleChoice(navigator, strategies, choice);
            choice = readChoice(scanner, strategies);
        }
        System.out.println("Goodbye!");
    }

    private static String readChoice(Scanner scanner, Map<String, RouteStrategy> strategies) {
        System.out.println();
        strategies.forEach((key, strategy) -> System.out.println("  " + key + ") " + strategy.modeName()));
        System.out.println("  " + QUIT_COMMAND + ") Quit");
        System.out.print("Choose travel mode: ");
        return scanner.hasNextLine() ? scanner.nextLine().trim() : QUIT_COMMAND;
    }

    private static void handleChoice(Navigator navigator, Map<String, RouteStrategy> strategies, String choice) {
        Optional.ofNullable(strategies.get(choice)).ifPresentOrElse(
                strategy -> {
                    navigator.setStrategy(strategy);
                    System.out.println("Strategy switched to: " + navigator.getStrategy().modeName());
                    printRoute(navigator.planRoute(AITU, KHAN_SHATYR));
                },
                () -> System.out.println("Unknown option: " + choice));
    }

    private static void printHeader(String title) {
        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println(title);
        System.out.println("=".repeat(70));
    }

    private static void printRoute(Route route) {
        System.out.printf(Locale.US, "%n[%s] %s -> %s%n", route.travelMode(), route.from().name(), route.to().name());
        System.out.printf(Locale.US, "    distance : %.2f km%n", route.distanceKm());
        System.out.printf(Locale.US, "    duration : %d min%n", route.durationMinutes());
        System.out.printf(Locale.US, "    cost     : %.0f KZT%n", route.costKzt());
        System.out.printf(Locale.US, "    route    : %s%n", route.description());
    }
}
