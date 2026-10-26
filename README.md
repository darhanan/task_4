# Strategy Pattern — Navigator

Assignment #5, Software Design Patterns (ShP-2216), Astana IT University.
Author: Darkhan Tynyshtyk, SE-2523.

A route planner that switches between travel modes using the **Strategy** pattern
(Option A — Navigation / Route Planning). The same trip can be planned by car, on foot,
by bicycle or by public transport, and the travel mode can be changed while the program is running.

## Pattern roles

| Role               | Class                                                                                  |
|--------------------|----------------------------------------------------------------------------------------|
| Strategy interface | `strategy/RouteStrategy`                                                               |
| Concrete strategies| `strategy/CarStrategy`, `WalkStrategy`, `CycleStrategy`, `PublicTransportStrategy`     |
| Context            | `context/Navigator`                                                                    |
| Client             | `Main`                                                                                 |

The UML class diagram is in [`docs/uml-class-diagram.png`](docs/uml-class-diagram.png)
(PlantUML source: [`docs/uml-class-diagram.puml`](docs/uml-class-diagram.puml)).

## Project structure

```
src/main/java/kz/aitu/navigator/
├── Main.java                       # client: strategy registry, demo, interactive menu
├── context/Navigator.java          # context: holds and delegates to a RouteStrategy
├── strategy/
│   ├── RouteStrategy.java          # strategy interface
│   ├── CarStrategy.java
│   ├── WalkStrategy.java
│   ├── CycleStrategy.java
│   └── PublicTransportStrategy.java
├── model/Location.java, Route.java # data records
└── util/GeoMath.java               # haversine distance and travel time helpers
```

## How to build and run

Requirements: JDK 17 or newer.

**macOS / Linux**

```sh
./run.sh
```

**Windows**

```bat
run.bat
```

**Manually**

```sh
javac --release 17 -d out $(find src/main/java -name "*.java")
java -cp out kz.aitu.navigator.Main
```

**IntelliJ IDEA:** open the folder, mark `src/main/java` as *Sources Root*, run `kz.aitu.navigator.Main`.

The program first plans the trip *Astana IT University → Baiterek Tower* with every strategy
(runtime switching demo), then opens a menu where you choose the travel mode with keys `1`–`4`
(`q` to quit).

## How to add a new strategy

1. Create a class in `strategy/` that implements `RouteStrategy`:

   ```java
   public class ScooterStrategy implements RouteStrategy {
       private static final double DETOUR_FACTOR = 1.2;
       private static final double SCOOTER_SPEED_KMH = 18.0;

       @Override
       public String modeName() {
           return "Scooter";
       }

       @Override
       public Route buildRoute(Location from, Location to) {
           double distanceKm = GeoMath.straightLineKm(from, to) * DETOUR_FACTOR;
           return new Route(modeName(), from, to, distanceKm,
                   GeoMath.travelMinutes(distanceKm, SCOOTER_SPEED_KMH), 0.0, "Bike lanes and sidewalks");
       }
   }
   ```

2. Register it in `Main.createStrategyRegistry()`:

   ```java
   strategies.put("5", new ScooterStrategy());
   ```

No other class changes: `Navigator`, `RouteStrategy` and the existing strategies stay untouched.
`PublicTransportStrategy` was added exactly this way (see the commit history).

## Note on the numbers

Distances are straight-line (haversine) distances multiplied by a detour factor for each mode.
Speeds, fuel price and ticket price are illustrative constants, not real-time data.
