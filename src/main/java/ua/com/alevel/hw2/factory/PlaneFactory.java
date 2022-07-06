package ua.com.alevel.hw2.factory;

import ua.com.alevel.hw2.model.*;
import java.util.Random;

public final class PlaneFactory {
    private static final Random RANDOM = new Random();

    private PlaneFactory() {}

    public static Plane[] createPlane(PlaneType planeType, int count) {
        Plane[] plane = new Plane[count];
        for (int i = 0; i < count; i++) {
            plane[i] = create(planeType);
        }

        return plane;
    }

    private static Plane create(PlaneType planeType) {
        return switch (planeType) {
            case CARGO_PLANE -> {
                CargoPlane cargoPlane = new CargoPlane();
                cargoPlane.setLoadCapacity(RANDOM.nextInt(200));
                cargoPlane.setCountOfCrew(RANDOM.nextInt(10));
                yield setPlaneFields(cargoPlane);
            }
            case FIGHTER -> {
                Fighter fighter = new Fighter();
                fighter.setType(getRandomTypeOfFighter());
                fighter.setBombLoad(RANDOM.nextInt(50));
                yield setPlaneFields(fighter);
            }
            case PASSENGER_PLANE -> {
                PassengerPlane passengerPlane = new PassengerPlane();
                passengerPlane.setNumberOfPassenger(RANDOM.nextInt(400));
                passengerPlane.setRangeOfFlight(RANDOM.nextInt(9000));
                yield setPlaneFields(passengerPlane);
            }
            default -> throw new IllegalArgumentException("Unknown plane type: " + planeType);
        };
    }

    private static PlaneBrand getRandomPlaneBrand() {
        PlaneBrand[] brands = PlaneBrand.values();
        int index = RANDOM.nextInt(brands.length);
        return brands[index];
    }

    private static TypeOfFighter getRandomTypeOfFighter() {
        TypeOfFighter[] type = TypeOfFighter.values();
        int index = RANDOM.nextInt(type.length);
        return type[index];
    }

    private static Plane setPlaneFields(Plane plane) {
        plane.setBrand(getRandomPlaneBrand());
        plane.setModel("Model-" + RANDOM.nextInt(300));
        plane.setPrice(RANDOM.nextLong(15000000l));
        return plane;
    }
}
