package org.example.coderpad.parking;

import org.example.coderpad.parking.model.Spot;
import org.example.coderpad.parking.model.SpotPlace;
import org.example.coderpad.parking.model.Vehicle;
import org.example.coderpad.parking.service.ParkingLotService;

import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class ParkingLotServiceTest {

    private final static SpotPlace COMPACT_SPOT = new SpotPlace(Spot.COMPACT);
    private final static SpotPlace REGULAR_SPOT = new SpotPlace(Spot.REGULAR);
    private final static SpotPlace LARGE_SPOT = new SpotPlace(Spot.LARGE);
    private static List<SpotPlace> spots;

    private ParkingLotService parkingLotService;


    @BeforeClass
    public static void initSetUp() {
        System.out.println("Setup before the class start.");
        System.out.println("Create Spots list for a Parking Lot.");
        spots = Arrays.asList(COMPACT_SPOT, COMPACT_SPOT, REGULAR_SPOT, REGULAR_SPOT, COMPACT_SPOT,
                REGULAR_SPOT, REGULAR_SPOT, REGULAR_SPOT, LARGE_SPOT, LARGE_SPOT);
    }

    @Before
    public void setUp() {
        System.out.println("Setup before each test start.");
        parkingLotService = new ParkingLotService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateParkingLotShouldBeIllegalArgumentException() {
        parkingLotService.createParkingLot(null);
    }

    @Test
    public void createParkingLotShouldBeOk() {
        parkingLotService.createParkingLot(spots);

        assertEquals(10, parkingLotService.totalSpots());
    }

    @Test
    public void parkingVehicleShouldBeOk() {
        // Arrange or Given
        parkingLotService.createParkingLot(spots);

        // Act or Given
        // Parking Actions
        parkingLotService.parkVehicle("AA-1234-OO", Vehicle.BIKE);
        parkingLotService.parkVehicle("AA-4529-PP", Vehicle.CAR);
        parkingLotService.parkVehicle("AA-4678-HH", Vehicle.VAN);
        parkingLotService.parkVehicle("AA-2567-PP", Vehicle.CAR);
        parkingLotService.parkVehicle("AA-7269-PP", Vehicle.CAR);
        parkingLotService.parkVehicle("AA-4284-OO", Vehicle.BIKE);

        // Assert or When
        assertEquals(10, parkingLotService.totalSpots());
        assertEquals(4, parkingLotService.totalFree());
        assertEquals(6, parkingLotService.totalBusy());
    }

    @Test
    public void leaveParkingVehicleShouldBeOk() {
        // Arrange or Given
        initParkingLotWithVehicles();

        // Act or Given
        // Leave parking actions
        parkingLotService.leaveParkVehicle("AA-4678-HH");
        parkingLotService.leaveParkVehicle("AA-1234-OO");
        parkingLotService.leaveParkVehicle("AA-2567-PP");

        // Assert or When
        assertEquals(10, parkingLotService.totalSpots());
        assertEquals(7, parkingLotService.totalFree());
        assertEquals(3, parkingLotService.totalBusy());
    }

    @Test
    public void parkAVehicleInBusyParkingLotShouldBeFalse() {

    }

    @Test
    public void leaveParkingVehicleInAFreeParkingLotShouldBeFalse() {

    }

    private void initParkingLotWithVehicles() {
        parkingLotService.createParkingLot(spots);
        parkingLotService.parkVehicle("AA-1234-OO", Vehicle.BIKE);
        parkingLotService.parkVehicle("AA-4529-PP", Vehicle.CAR);
        parkingLotService.parkVehicle("AA-4678-HH", Vehicle.VAN);
        parkingLotService.parkVehicle("AA-2567-PP", Vehicle.CAR);
        parkingLotService.parkVehicle("AA-7269-PP", Vehicle.CAR);
        parkingLotService.parkVehicle("AA-4284-OO", Vehicle.BIKE);
    }

    @After
    public void tearDown() {
        System.out.println("Tear down after each test finish.");
        parkingLotService = null;
    }

    @AfterClass
    public static void closeSetUp() {
        System.out.println("Setup before the class start.");
        spots = null;
      }
}
