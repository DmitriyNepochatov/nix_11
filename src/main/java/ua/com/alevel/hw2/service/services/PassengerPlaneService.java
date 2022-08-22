package ua.com.alevel.hw2.service.services;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.dao.products.passengerplanedatabase.PassengerPlaneDatabase;
import ua.com.alevel.hw2.factory.PlaneFactory;
import ua.com.alevel.hw2.model.*;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import ua.com.alevel.hw2.service.PlaneService;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class PassengerPlaneService extends PlaneService<PassengerPlane> {
    private static PassengerPlaneService instance;

    public static PassengerPlaneService getInstance() {
        if (instance == null) {
            instance = new PassengerPlaneService(PassengerPlaneDatabase.getInstance());
        }

        return instance;
    }

    public static PassengerPlaneService getInstance(PassengerPlaneDatabase planeDB) {
        if (instance == null) {
            instance = new PassengerPlaneService(planeDB);
        }

        return instance;
    }

    @Autowired
    private PassengerPlaneService(PassengerPlaneDatabase planeDB) {
        super(planeDB);
    }

    @Override
    public PassengerPlane[] createPlane(int count) {
        Plane[] arr = PlaneFactory.createPlane(PlaneType.PASSENGER_PLANE, count);
        return Arrays.copyOf(arr, arr.length, PassengerPlane[].class);
    }

    @Override
    public void updatePlane(PassengerPlane updatepablePlane, PassengerPlane plane) {
        updatepablePlane.setNumberOfPassenger(plane.getNumberOfPassenger());
        updatepablePlane.setRangeOfFlight(plane.getRangeOfFlight());
        updatepablePlane.setModel(plane.getModel());
        updatepablePlane.setPrice(plane.getPrice());
        updatepablePlane.setId(plane.getId());
        updatepablePlane.setBrand(plane.getBrand());
        updatepablePlane.setCount(plane.getCount());
    }

    @Override
    public PassengerPlane createPlaneFromMapFoo(Map<String, Object> map) {
        return new PassengerPlane(UUID.randomUUID().toString(),
                PlaneBrand.valueOf(map.get("brand").toString()),
                map.get("model").toString(),
                Integer.parseInt(map.get("price").toString()),
                Integer.parseInt(map.get("count").toString()),
                map.get("currency").toString(),
                (Date) map.get("created"),
                (ManufacturingMaterial) map.get("manufacturingMaterial"),
                Integer.parseInt(map.get("numberOfPassenger").toString()),
                Integer.parseInt(map.get("rangeOfFlight").toString()));
    }
}
