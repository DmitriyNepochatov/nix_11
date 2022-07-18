package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.model.PassengerPlane;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneBrand;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;

class PlaneServiceTest {
    private PlaneService target;
    private PlaneDB planeDB;
    private PassengerPlane passengerPlane;

    @BeforeEach
    void setUp() {
        planeDB = Mockito.mock(PlaneDB.class);
        target = new PlaneService(planeDB);
        passengerPlane = new PassengerPlane("12",
                PlaneBrand.BOEING,
                "747",
                1_000_000l,
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
                500_000l,
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

        Optional<Plane> expectedPassengerPlane = target.findById("12");
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

    @Test
    void updateIfPresent_ifPresent() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.updateIfPresent(passengerPlaneTest);
        Assertions.assertEquals(Optional.of(passengerPlaneTest), target.findById(passengerPlaneTest.getId()));
    }

    @Test
    void updateIfPresent_notPresent() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        target.updateIfPresent(passengerPlane);
        Assertions.assertNotEquals(Optional.of(passengerPlane), target.findById(passengerPlane.getId()));
    }

    @Test
    void findByIdOrReturnFindablePlane_finded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlane));
        Plane result = target.findByIdOrReturnFindablePlane(passengerPlaneTest);
        Assertions.assertEquals(result, passengerPlane);
    }

    @Test
    void findByIdOrReturnFindablePlane_notFinded() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        Plane result = target.findByIdOrReturnFindablePlane(passengerPlane);
        Assertions.assertEquals(result, passengerPlane);
    }

    @Test
    void findByIdOrSaveIfNotDuplicate_finded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.findByIdOrSaveIfNotDuplicate(passengerPlaneTest);
        Assertions.assertNotEquals(Optional.of(passengerPlaneTest), target.findById(passengerPlaneTest.getId()));
    }

    @Test
    void findByIdOrSaveIfNotDuplicate_notFinded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.empty());
        target.findByIdOrSaveIfNotDuplicate(passengerPlaneTest);
        ArgumentCaptor<PassengerPlane> passengerPlaneArgumentCaptor = ArgumentCaptor.forClass(PassengerPlane.class);
        Mockito.verify(planeDB).save(passengerPlaneArgumentCaptor.capture());
        Assertions.assertEquals(passengerPlaneTest, passengerPlaneArgumentCaptor.getValue());
    }

    @Test
    void planeToStringMap_finded() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        String result = target.planeToStringMap(passengerPlane.getId());
        Assertions.assertEquals(passengerPlane.toString(), result);
    }

    @Test
    void planeToStringMap_notFinded() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        String result = target.planeToStringMap(passengerPlane.getId());
        Assertions.assertNotEquals(passengerPlane.toString(), result);
    }

    @Test
    void updateIfPresentOrSave_ifPresent() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.updateIfPresentOrSave(passengerPlaneTest);
        Assertions.assertEquals(Optional.of(passengerPlaneTest), target.findById(passengerPlaneTest.getId()));
    }

    @Test
    void updateIfPresentOrSave_ifNotPresent() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        target.updateIfPresentOrSave(passengerPlane);
        ArgumentCaptor<PassengerPlane> passengerPlaneArgumentCaptor = ArgumentCaptor.forClass(PassengerPlane.class);
        Mockito.verify(planeDB).save(passengerPlaneArgumentCaptor.capture());
        Assertions.assertEquals(passengerPlane, passengerPlaneArgumentCaptor.getValue());
    }

    @Test
    void deletePlaneIfWasBuiltByBoeing_wasBuiltByBoeing() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.BOEING,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlaneTest));
        target.deletePlaneIfWasBuiltByBoeing(passengerPlaneTest.getId());
        ArgumentCaptor<String> passengerPlaneArgumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(planeDB).delete(passengerPlaneArgumentCaptor.capture());
        Assertions.assertEquals(passengerPlaneTest.getId(), passengerPlaneArgumentCaptor.getValue());
    }

    @Test
    void deletePlaneIfWasBuiltByBoeing_wasNotBuiltByBoeing() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlaneTest));
        target.deletePlaneIfWasBuiltByBoeing(passengerPlaneTest.getId());
        Mockito.verify(planeDB, Mockito.times(0)).delete(passengerPlaneTest.getId());
    }

    @Test
    void findByIdWithException_noException() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        Plane result = target.findByIdWithException(passengerPlane.getId());
        Assertions.assertEquals(passengerPlane, result);
    }

    @Test
    void findByIdWithException_exception() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.findByIdWithException(passengerPlane.getId()));
    }

    @Test
    void findByIdOrBackPlaneInOptional_finded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                150_000_000l,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlane));
        Optional<Plane> result = target.findByIdOrBackPlaneInOptional(passengerPlaneTest);
        Assertions.assertEquals(Optional.of(passengerPlane), result);
    }

    @Test
    void findByIdOrBackPlaneInOptional_notFinded() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        Optional<Plane> result = target.findByIdOrBackPlaneInOptional(passengerPlane);
        Assertions.assertEquals(Optional.of(passengerPlane), result);
    }
}