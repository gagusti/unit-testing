package org.example.coderpad.parking.model;

import java.util.Map;
import java.util.stream.Collectors;

public class ParkingReport {
    private int totalSpots;
    private int totalFreeSpots;
    private int totalBusySpots;
    private boolean isFull;
    private boolean isEmpty;
    private Map<Vehicle, Long> freeSpotsByVehicle;
    private Map<Vehicle, Long> busySpotsByVehicle;

    public ParkingReport(ParkingLot parkingLot) {
        if (parkingLot == null) {
            return;
        }
        this.totalSpots = parkingLot.getTotalSpots();
        this.totalFreeSpots = parkingLot.getTotalFree();
        this.totalBusySpots = parkingLot.getTotalBusy();
        freeSpotsGroupByVehicle(parkingLot);
        busySpotsGroupByVehicle(parkingLot);
    }

    private void freeSpotsGroupByVehicle(ParkingLot parkingLot) {
        freeSpotsByVehicle = parkingLot.getFreeSpots().stream()
                .collect(Collectors.groupingBy(SpotPlace::getVehicle, Collectors.counting()));
    }

    private Long calculateVehicleSlot(Vehicle vehicle, Long value) {
        return vehicle == Vehicle.VAN ? value / 3 : value;
    }

    private void busySpotsGroupByVehicle(ParkingLot parkingLot) {
        Map<Vehicle, Long> groupBusySpots = parkingLot.getBusySpots().stream()
                .collect(Collectors.groupingBy(SpotPlace::getVehicle, Collectors.counting()));
        busySpotsByVehicle = groupBusySpots.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, mp -> calculateVehicleSlot(mp.getKey(), mp.getValue())));
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getTotalFreeSpots() {
        return totalFreeSpots;
    }

    public int getTotalBusySpots() {
        return totalBusySpots;
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Map<Vehicle, Long> getFreeSpotsByVehicle() {
        return freeSpotsByVehicle;
    }

    public Map<Vehicle, Long> getBusySpotsByVehicle() {
        return busySpotsByVehicle;
    }
}
