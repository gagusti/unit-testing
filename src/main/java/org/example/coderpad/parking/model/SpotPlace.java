package org.example.coderpad.parking.model;

public class SpotPlace {
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