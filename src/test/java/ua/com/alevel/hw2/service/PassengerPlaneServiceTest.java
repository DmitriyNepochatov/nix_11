package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.model.ManufacturingMaterial;
import ua.com.alevel.hw2.model.PassengerPlane;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import java.util.Date;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;

class PassengerPlaneServiceTest {
    private PassengerPlaneService target;
    private PlaneDB<PassengerPlane> planeDB;
    private PassengerPlane passengerPlane;
    private Date date;
    private ManufacturingMaterial manufacturingMaterial;

    @BeforeEach
    void setUp() {
        planeDB = Mockito.mock(PlaneDB.class);
        target = PassengerPlaneService.getInstance(planeDB);

        date = new Date();
        manufacturingMaterial = new ManufacturingMaterial("Material-" + 150, "Color-" + 300);

        passengerPlane = new PassengerPlane("12",
                PlaneBrand.BOEING,
                "747",
                1000,
                200,
                "Currency-13",
                date,
                manufacturingMaterial,
                500,
                15_000
        );
    }

    @Test
    void save() {
        target.save(passengerPlane);
        ArgumentCaptor<PassengerPlane> passengerPlaneArgumentCaptor = ArgumentCaptor.forClass(PassengerPlane.class);
        Mockito.verify(planeDB).save(passengerPlaneArgumentCaptor.capture());
        Assertions.assertEquals(passengerPlane, passengerPlaneArgumentCaptor.getValue());
    }

    @Test
    void save_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void update() {
        PassengerPlane plane = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.BOEING,
                "707",
                5000,
                100,
                "Currency-13",
                date,
                manufacturingMaterial,
                150,
                2_000
        );

        target.update(plane);
        ArgumentCaptor<PassengerPlane> planeCaptor = ArgumentCaptor.forClass(PassengerPlane.class);
        Mockito.verify(planeDB).update(planeCaptor.capture());
        Assertions.assertEquals(plane, planeCaptor.getValue());
    }

    @Test
    void update_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(null));
    }

    @Test
    void delete() {
        target.delete(passengerPlane.getId());
        Mockito.verify(planeDB).delete(passengerPlane.getId());
    }

    @Test
    void delete_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.delete(null));
    }

    @Test
    void findById() {
        Mockito.when(planeDB.findById(ArgumentMatchers.argThat(findableID -> {
            Assertions.assertEquals("12", findableID);
            return true;
        }))).thenReturn(Optional.of(passengerPlane));

        Optional<PassengerPlane> expectedPassengerPlane = target.findById("12");
        Mockito.verify(planeDB).findById(anyString());
        Assertions.assertEquals(Optional.of(passengerPlane), expectedPassengerPlane);
    }

    @Test
    void findById_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findById(null));
    }

    @Test
    void findById_nullExceptions() {
        Mockito.when(planeDB.findById(anyString())).thenThrow(RuntimeException.class);
        Assertions.assertThrows(RuntimeException.class, () -> target.findById(anyString()));
        Mockito.verify(planeDB).findById(anyString());
    }

    @Test
    void findAll() {
        target.findAll();
        Mockito.verify(planeDB).findAll();
    }

    @Test
    void findAll_nothing() {
        Mockito.when(planeDB.findAll()).thenCallRealMethod();
        Assertions.assertThrows(NullPointerException.class, () -> target.findAll());
        Mockito.verify(planeDB).findAll();
    }
}