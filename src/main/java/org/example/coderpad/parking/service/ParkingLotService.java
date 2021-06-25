package org.example.coderpad.parking.service;

import java.util.ArrayList;
import java.util.List;

import org.example.coderpad.parking.model.*;

public class ParkingLotService {

    private ParkingLot parkingLot = new ParkingLot();

    public void createParkingLot(List<SpotPlace> spotPlaces) throws IllegalArgumentException {
        parkingLot.create(spotPlaces);
    }

    public boolean parkVehicle(String id, Vehicle vehicle) {
        boolean parkOk = false;
        if ((parkingLot.getFreeSpots() != null && parkingLot.getFreeSpots().size() == 0)
            || parkingLot.getBusySpots() == null || id == null || id.isEmpty()) {
            return false;
        }
        final List<SpotPlace> spots = availableSpots(vehicle);
        if (spots.isEmpty()) {
            return false;
        }
        final Spot firstSpot = spots.get(0).getSpot();
        if (vehicle == Vehicle.BIKE || vehicle == Vehicle.CAR
                || (vehicle == Vehicle.VAN && spots.size() >= 1 && firstSpot == Spot.LARGE)) {
            SpotPlace place = new SpotPlace(id, vehicle, firstSpot);
            parkingLot.getBusySpots().add(place);
            parkingLot.getFreeSpots().remove(spots.get(0));
            parkOk = true;
        } else if (vehicle == Vehicle.VAN && spots.size() >= 3 && firstSpot == Spot.REGULAR){
            List<SpotPlace> places = new ArrayList<>();
            places.add(new SpotPlace(id, vehicle, firstSpot));
            places.add(new SpotPlace(id, vehicle, firstSpot));
            places.add(new SpotPlace(id, vehicle, firstSpot));
            parkingLot.getBusySpots().addAll(places);
            parkingLot.getFreeSpots().remove(spots.get(0));
            parkingLot.getFreeSpots().remove(spots.get(1));
            parkingLot.getFreeSpots().remove(spots.get(2));
            parkOk = true;
        }
        return parkOk;
    }

    public void leaveParkVehicle(String id) {
        if ((parkingLot.getBusySpots() == null && parkingLot.getBusySpots().size() == 0)
                || parkingLot.getFreeSpots() == null || id == null || id.isEmpty()) {
            return;
        }
        List<SpotPlace> spotsToRemove = new ArrayList<>();
        parkingLot.getBusySpots().forEach( spot -> {
                    if (id.equals(spot.getVehicleID())) {
                        spotsToRemove.add(spot);
                    }
                }
        );
        parkingLot.getBusySpots().removeAll(spotsToRemove);
        spotsToRemove.forEach( spot -> {
            SpotPlace spotPlace = new SpotPlace("", Vehicle.NONE, spot.getSpot());
            if (Vehicle.VAN == spot.getVehicle()  && Spot.REGULAR == spot.getSpot()) {
                parkingLot.getFreeSpots().add(spotPlace);
                parkingLot.getFreeSpots().add(spotPlace);
                parkingLot.getFreeSpots().add(spotPlace);
            } else {
                parkingLot.getFreeSpots().add(spotPlace);
            }
        });
        //parkingLot.getFreeSpots().addAll(spotsToRemove);
    }

    private List<SpotPlace> availableSpots(Vehicle vehicle) {
        List<SpotPlace> spotPlaces = new ArrayList<>();
        switch (vehicle) {
            case BIKE:
                spotPlaces = parkingLot.getFreeSpots(Spot.COMPACT);
                if (spotPlaces.size() == 0) {
                    spotPlaces = parkingLot.getFreeSpots(Spot.REGULAR);
                    if (spotPlaces.size() == 0) {
                        spotPlaces = parkingLot.getFreeSpots(Spot.LARGE);
                    }
                }
                break;
            case CAR:
                spotPlaces = parkingLot.getFreeSpots(Spot.COMPACT);
                if (spotPlaces.size() == 0) {
                    spotPlaces = parkingLot.getFreeSpots(Spot.REGULAR);
                }
                break;

            case VAN:
                spotPlaces = parkingLot.getFreeSpots(Spot.LARGE);
                break;
        }
        return spotPlaces;
    }

    public ParkingReport parkingReport() {
        return new ParkingReport(parkingLot);
    }

    public int totalSpots() {
        return parkingLot.getTotalSpots();
    }

    public int totalFree() {
        return parkingLot.getTotalFree();
    }

    public int totalBusy() {
        return parkingLot.getTotalBusy();
    }
}