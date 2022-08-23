package ua.com.alevel.hw2.service.optionalexamples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.com.alevel.hw2.dao.products.passengerplanedatabase.PassengerPlaneDatabase;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.service.services.PassengerPlaneService;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Optional;

class OptionalExamplesTest {
    private OptionalExamplesPassengerPlane target;
    private static PassengerPlaneDatabase planeDB;
    private static PassengerPlaneService passengerPlaneService;
    private PassengerPlane passengerPlane;
    private Date date;
    private ManufacturingMaterial manufacturingMaterial;

    @BeforeAll
    public static void setup() {
        planeDB = Mockito.mock(PassengerPlaneDatabase.class);
        setMock(planeDB);
    }

    @BeforeEach
    void setUp() {
        Mockito.reset(planeDB);
        target = new OptionalExamplesPassengerPlane();
        passengerPlaneService = PassengerPlaneService.getInstance(planeDB);

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
    void updateIfPresent_ifPresent() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                1500,
                50,
                "Currency-13",
                date,
                manufacturingMaterial,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.updateIfPresent(passengerPlaneTest, passengerPlaneService);
        Assertions.assertEquals(Optional.of(passengerPlaneTest), planeDB.findById(passengerPlaneTest.getId()));
    }

    @Test
    void updateIfPresent_notPresent() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        target.updateIfPresent(passengerPlane, passengerPlaneService);
        Assertions.assertNotEquals(Optional.of(passengerPlane), planeDB.findById(passengerPlane.getId()));
    }

    @Test
    void findByIdOrReturnFindablePlane_finded() {
        PassengerPlane passengerPlaneTest = new PassengerPlane(passengerPlane.getId(),
                PlaneBrand.LOCKHEED,
                "GoldenStar",
                1500,
                50,
                "Currency-13",
                date,
                manufacturingMaterial,
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
                "Currency-13",
                date,
                manufacturingMaterial,
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
                "Currency-13",
                date,
                manufacturingMaterial,
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
                "Currency-13",
                date,
                manufacturingMaterial,
                300,
                18000);

        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.of(passengerPlane));
        target.updateIfPresentOrSave(passengerPlaneTest, passengerPlaneService);
        Assertions.assertEquals(Optional.of(passengerPlaneTest), planeDB.findById(passengerPlaneTest.getId()));
    }

    @Test
    void updateIfPresentOrSave_ifNotPresent() {
        Mockito.when(planeDB.findById(passengerPlane.getId())).thenReturn(Optional.empty());
        target.updateIfPresentOrSave(passengerPlane, passengerPlaneService);
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
                "Currency-13",
                date,
                manufacturingMaterial,
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
                "Currency-13",
                date,
                manufacturingMaterial,
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
                "Currency-13",
                date,
                manufacturingMaterial,
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

    private static void setMock(PassengerPlaneDatabase mock) {
        try {
            Field instance = PassengerPlaneDatabase.class.getDeclaredField("instance");
            instance.setAccessible(true);
            instance.set(instance, mock);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}