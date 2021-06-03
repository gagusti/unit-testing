package org.example.coderpad;

/* This is an example pad with a question attached. This question is an example of how you can use the pad during an interview with a candidate.

You can play around, type and run code as needed. Once you click on View Playback in the Settings in the bottom right hand corner, you will be able to view the playback of this pad and what was typed during the interview.
--------------------------------------------------------
Design a parking lot using object-oriented principles

Goals:
- Your solution should be in Java - if you would like to use another language, please let the interviewer know.
- Boilerplate is provided. Feel free to change the code as you see fit

Assumptions:
- The parking lot can hold motorcycles, cars and vans
- The parking lot has motorcycle spots, car spots and large spots
- A motorcycle can park in any spot
- A car can park in a single compact spot, or a regular spot
- A van can park, but it will take up 3 regular spots
- These are just a few assumptions. Feel free to ask your interviewer about more assumptions as needed

Here are a few methods that you should be able to run:
- Tell us how many spots are remaining
- Tell us how many total spots are in the parking lot
- Tell us when the parking lot is full
- Tell us when the parking lot is empty
- Tell us when certain spots are full e.g. when all motorcycle spots are taken
- Tell us how many spots vans are taking up

Hey candidate! Welcome to your interview. I'll start off by giving you a Solution class. To run the code at any time, please hit the run button located in the top left corner.
*/

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotSolution {
    public static void main(String[] args) {
        ParkingLotSolution solution = new ParkingLotSolution();
        ParkingLotSolution.ParkingLot parkingLot = solution.new ParkingLot();
        SpotPlace compact = solution.new SpotPlace(Spot.COMPACT);
        SpotPlace regular = solution.new SpotPlace(Spot.REGULAR);
        SpotPlace large = solution.new SpotPlace(Spot.COMPACT);
        List<SpotPlace> spots = Arrays.asList(compact, compact, regular, regular, compact,
                regular, regular, regular, large, large);
        parkingLot.create(spots);

        // Parking actions
        System.out.println(String.format(parkingLot.printStatus()));
        parkingLot.parkVehicle("AA-1234-OO", Vehicle.BIKE);
        parkingLot.parkVehicle("AA-2567-PP", Vehicle.CAR);
        parkingLot.parkVehicle("AA-4678-HH", Vehicle.VAN);
        parkingLot.parkVehicle("AA-2567-PP", Vehicle.CAR);
        parkingLot.parkVehicle("AA-2567-PP", Vehicle.CAR);
        parkingLot.parkVehicle("AA-1234-OO", Vehicle.BIKE);
        if (!parkingLot.parkVehicle("AA-4678-HH", Vehicle.VAN)) {
            System.out.println(String.format("VAN Plate: AA-4678-HH No Parked"));
        }
        parkingLot.parkVehicle("AA-2567-PP", Vehicle.CAR);
        System.out.println(String.format(parkingLot.printStatus()));

        // Leave parking actions
        parkingLot.leaveParkVehicle("AA-4678-HH");
        parkingLot.leaveParkVehicle("AA-1234-OO");
        parkingLot.leaveParkVehicle("AA-2567-PP");
        System.out.println(String.format(parkingLot.printStatus()));

    }

    public enum Vehicle {
        NONE,
        BIKE,
        CAR,
        VAN
    }
    public enum Spot {
        COMPACT,
        REGULAR,
        LARGE
    }
    private class SpotPlace {
        private Spot spot;
        private Vehicle vehicle;
        private String vehicleID;

        public SpotPlace(Spot spot) {
            this.vehicleID = "";
            this.vehicle = Vehicle.NONE;
            this.spot = spot;
        }
        public SpotPlace(String id, Vehicle vehicle, Spot spot) {
            this.vehicleID = id;
            this.vehicle = vehicle;
            this.spot = spot;
        }

        public Spot getSpot() {
            return spot;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public String getVehicleID() {
            return vehicleID;
        }
    }
    private class ParkingLot {

        private List<SpotPlace> freeSpots;
        private List<SpotPlace> busySpots;

        public ParkingLot() {
            freeSpots = new ArrayList<>();
            busySpots = new ArrayList<>();
        }
        public void create(List<SpotPlace> spots) {
            if (freeSpots == null) {
                freeSpots = new ArrayList<>();
            }
            freeSpots.addAll(spots);
        }

        public boolean parkVehicle(String id, Vehicle vehicle) {
            boolean parkOk = false;
            if (freeSpots != null && freeSpots.size() <= 0) {
                return false;
            }
            if (busySpots == null) {
                busySpots = new ArrayList<>();
            }
            List<SpotPlace> spots = availableSpots(vehicle);
            if (vehicle == Vehicle.BIKE || vehicle == Vehicle.CAR
                    || (vehicle == Vehicle.VAN && spots.size() == 1 && spots.get(0).spot == Spot.LARGE)) {
                SpotPlace place = new SpotPlace(id, vehicle, spots.get(0).spot);
                busySpots.add(place);
                freeSpots.remove(spots.get(0));
                parkOk = true;
            } else if (vehicle == Vehicle.VAN && spots.size() >= 3 && spots.get(0).spot == Spot.REGULAR){
                List<SpotPlace> places = new ArrayList<>();
                places.add(new SpotPlace(id, vehicle, spots.get(0).spot));
                busySpots.addAll(places);
                freeSpots.remove(spots.get(0));
                freeSpots.remove(spots.get(1));
                freeSpots.remove(spots.get(2));
                parkOk = true;
            }
            return parkOk;
        }

        public void leaveParkVehicle(String id) {
            if ((busySpots != null && busySpots.size() <= 0) &&
                freeSpots != null) {
                return;
            }
            List<SpotPlace> spotsToRemove = new ArrayList<>();
            busySpots.forEach( spot -> {
                        if (spot.vehicleID == id) {
                            spotsToRemove.add(spot);
                        }
                    }
            );
            busySpots.removeAll(spotsToRemove);
            spotsToRemove.forEach( spot -> {
                SpotPlace spotPlace = new SpotPlace("", Vehicle.NONE, spot.spot);
                if (spot.vehicle == Vehicle.VAN && spot.spot == Spot.REGULAR) {
                    freeSpots.add(spotPlace);
                    freeSpots.add(spotPlace);
                    freeSpots.add(spotPlace);
                } else {
                    freeSpots.add(spotPlace);
                }
            });
            freeSpots.addAll(spotsToRemove);
        }

        private List<SpotPlace> availableSpots(Vehicle vehicle) {
            List<SpotPlace> spotPlaces = new ArrayList<>();
            switch (vehicle) {
                case BIKE:
                    spotPlaces = getFreeSpots(Spot.COMPACT);
                    if (spotPlaces.size() == 0) {
                        spotPlaces = getFreeSpots(Spot.REGULAR);
                        if (spotPlaces.size() == 0) {
                            spotPlaces = getFreeSpots(Spot.LARGE);
                        }
                    }
                    break;
                case CAR:
                    spotPlaces = getFreeSpots(Spot.COMPACT);
                    if (spotPlaces.size() == 0) {
                        spotPlaces = getFreeSpots(Spot.REGULAR);
                    }
                    break;

                case VAN:
                    spotPlaces = getFreeSpots(Spot.REGULAR);
                    if (spotPlaces.size() < 3) {
                        spotPlaces = getFreeSpots(Spot.LARGE);
                    }
                    break;            }

            return spotPlaces;
        }

        public int remainingSpots() {
            return freeSpots.size();
        }

        public int totalSpots() {
            return freeSpots.size() + busySpots.size();
        }

        public int totalFree() {
            return freeSpots.size();
        }

        public int totalBusy() {
            return busySpots.size();
        }

        public boolean isFull() {
            return freeSpots.size() == 0;
        }

        public boolean isEmpty() {
            return busySpots == null || (busySpots != null && busySpots.size() == 0);
        }

        public boolean isSpotFull(Spot spotType) {
            return freeSpots.stream().filter( spot -> spot.spot == spotType).count() == 0;
        }

        private List<SpotPlace> getFreeSpots(Spot spotType) {
            return freeSpots.stream().filter( spot -> spot.spot == spotType).collect(Collectors.toList());
        }

        public long totalVehicleSpots(Vehicle vehicle) {
            return busySpots.stream().filter( spot -> spot.vehicle == vehicle).count();
        }

        public String printStatus(){
            StringBuilder parkingStatus = new StringBuilder();
            parkingStatus.append(String.format("Parking Total spots: %d \n", totalSpots()));
            parkingStatus.append(String.format("Parking is Empty? %s \n", isEmpty()));
            parkingStatus.append(String.format("Parking is Full? %s \n", isFull()));
            parkingStatus.append("Free parking spots: \n");
            Map<Vehicle, Long> groupFreeSpots = freeSpots.stream()
                    .collect(Collectors.groupingBy(SpotPlace::getVehicle, Collectors.counting()));
            parkingStatus.append(groupFreeSpots.entrySet().stream()
                    .map(mp -> mp.getKey() + ": " + mp.getValue())
                    .collect(Collectors.joining(", ","[", "]")).toString() + "\n");
            parkingStatus.append(String.format("Parking Total Free spots: %d \n", totalFree()));
            parkingStatus.append("Busy parking spots: \n");
            Map<Vehicle, Long> groupBusySpots = busySpots.stream()
                    .collect(Collectors.groupingBy(SpotPlace::getVehicle, Collectors.counting()));
            parkingStatus.append(groupBusySpots.entrySet().stream()
                    .map(mp ->  mp.getKey() + ": " + (mp.getKey() == Vehicle.VAN ? mp.getValue() / 3 : mp.getValue()))
                    .collect(Collectors.joining(", ","[", "]")).toString() + "\n");
            parkingStatus.append(String.format("Parking Total Busy spots: %d \n", totalBusy()));

            return parkingStatus.toString();
        }
    }
}
