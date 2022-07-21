package ua.com.alevel.hw2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.com.alevel.hw2.db.PlaneDB;
import ua.com.alevel.hw2.model.PassengerPlane;
import ua.com.alevel.hw2.model.PlaneBrand;
import java.util.Optional;

class OptionalExamplesTest {
    private OptionalExamples<PassengerPlane, PassengerPlaneService> target;
    private PlaneDB<PassengerPlane> planeDB;
    private PassengerPlane passengerPlane;

    @BeforeEach
    void setUp() {
        planeDB = Mockito.mock(PlaneDB.class);
        target = new OptionalExamples<>(planeDB);
        passengerPlane = new PassengerPlane("12",
                PlaneBrand.BOEING,
                "747",
                1000,
                200,
                500,
                15_000
        );
    }

    @Test
    void updateIfPresent_ifPresent() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                1500,
                50,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.updateIfPresent(passengerPlaneTest, new PassengerPlaneService(planeDB));
        Assertions.assertEquals(Optional.of(passengerPlaneTest), planeDB.findById(passengerPlaneTest.getId()));
    }

    @Test
    void updateIfPresent_notPresent() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        target.updateIfPresent(passengerPlane, new PassengerPlaneService(planeDB));
        Assertions.assertNotEquals(Optional.of(passengerPlane), planeDB.findById(passengerPlane.getId()));
    }

    @Test
    void findByIdOrReturnFindablePlane_finded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                1500,
                50,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlane));
        PassengerPlane result = target.findByIdOrReturnFindablePlane(passengerPlaneTest);
        Assertions.assertEquals(result, passengerPlane);
    }

    @Test
    void findByIdOrReturnFindablePlane_notFinded() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        PassengerPlane result = target.findByIdOrReturnFindablePlane(passengerPlane);
        Assertions.assertEquals(result, passengerPlane);
    }

    @Test
    void findByIdOrSaveIfNotDuplicate_finded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                1500,
                50,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.findByIdOrSaveIfNotDuplicate(passengerPlaneTest);
        Assertions.assertNotEquals(Optional.of(passengerPlaneTest), planeDB.findById(passengerPlaneTest.getId()));
    }

    @Test
    void findByIdOrSaveIfNotDuplicate_notFinded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                1500,
                50,
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
                1500,
                50,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.updateIfPresentOrSave(passengerPlaneTest, new PassengerPlaneService(planeDB));
        Assertions.assertEquals(Optional.of(passengerPlaneTest), planeDB.findById(passengerPlaneTest.getId()));
    }

    @Test
    void updateIfPresentOrSave_ifNotPresent() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        target.updateIfPresentOrSave(passengerPlane, new PassengerPlaneService(planeDB));
        ArgumentCaptor<PassengerPlane> passengerPlaneArgumentCaptor = ArgumentCaptor.forClass(PassengerPlane.class);
        Mockito.verify(planeDB).save(passengerPlaneArgumentCaptor.capture());
        Assertions.assertEquals(passengerPlane, passengerPlaneArgumentCaptor.getValue());
    }

    @Test
    void deletePlaneIfWasBuiltByBoeing_wasBuiltByBoeing() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.BOEING,
                "GoldenStar",
                1500,
                50,
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
                1500,
                50,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlaneTest));
        target.deletePlaneIfWasBuiltByBoeing(passengerPlaneTest.getId());
        Mockito.verify(planeDB, Mockito.times(0)).delete(passengerPlaneTest.getId());
    }

    @Test
    void findByIdWithException_noException() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        PassengerPlane result = target.findByIdWithException(passengerPlane.getId());
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
                1500,
                50,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlaneTest.getId())).thenReturn(Optional.of(passengerPlane));
        Optional<PassengerPlane> result = target.findByIdOrBackPlaneInOptional(passengerPlaneTest);
        Assertions.assertEquals(Optional.of(passengerPlane), result);
    }

    @Test
    void findByIdOrBackPlaneInOptional_notFinded() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        Optional<PassengerPlane> result = target.findByIdOrBackPlaneInOptional(passengerPlane);
        Assertions.assertEquals(Optional.of(passengerPlane), result);
    }
}