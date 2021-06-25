package org.example.coderpad.parking.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLot {

    private List<SpotPlace> freeSpots;
    private List<SpotPlace> busySpots;

    public ParkingLot() {
        freeSpots = new ArrayList<>();
        busySpots = new ArrayList<>();
    }

    public void create(List<SpotPlace> spots) throws IllegalArgumentException {
        if (spots == null || spots.isEmpty()) {
            throw new IllegalArgumentException("Can not create Parking Lot");
        }
        freeSpots.addAll(spots);
    }
    public List<SpotPlace> getFreeSpots() {
        return freeSpots;
    }

    public List<SpotPlace> getBusySpots() {
        return busySpots;
    }

    public int remainingSpots() {
        return freeSpots.size();
    }

    public int getTotalSpots() {
        return freeSpots.size() + busySpots.size();
    }

    public int getTotalFree() {
        return freeSpots.size();
    }

    public int getTotalBusy() {
        return busySpots.size();
    }

    public boolean isFull() {
        return freeSpots.size() == 0;
    }

    public boolean isEmpty() {
        return busySpots == null || (busySpots != null && busySpots.size() == 0);
    }

    public boolean isSpotFull(Spot spotType) {
        return freeSpots.stream().filter( spot -> spot.getSpot() == spotType).count() == 0;
    }

    public List<SpotPlace> getFreeSpots(Spot spotType) {
        return freeSpots.stream().filter( spot -> spot.getSpot() == spotType).collect(Collectors.toList());
    }

    public long totalVehicleSpots(Vehicle vehicle) {
        return busySpots.stream().filter( spot -> spot.getVehicle() == vehicle).count();
    }
}