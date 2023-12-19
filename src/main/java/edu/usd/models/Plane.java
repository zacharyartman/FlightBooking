package edu.usd.models;

public class Plane {
    private final String planeMake;
    private final String planeModel;
    private String registrationNumber;
    private int economySeats;
    private int firstClassSeats;
    private double storageCapacity;

    public Plane(String planeMake, String planeModel, String registrationNumber, int economySeats, int firstClassSeats, double storageCapacity) {
        this.planeMake = planeMake;
        this.planeModel = planeModel;
        this.registrationNumber = registrationNumber;
        this.economySeats = economySeats;
        this.firstClassSeats = firstClassSeats;
        this.storageCapacity = storageCapacity;
    }

    public String getPlaneMake() {
        return planeMake;
    }

    public String getPlaneModel() {
        return planeModel;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setEconomySeats (int economySeats) {
        this.economySeats = economySeats;
    }
    public int getEconomySeats() {
        return economySeats;
    }

    public void setFirstClassSeats (int firstClassSeats) {
        this.firstClassSeats = firstClassSeats;
    }
    public int getFirstClassSeats() {
        return firstClassSeats;
    }

    public void setStorageCapacity(double StorageCapacity) {
        this.storageCapacity = StorageCapacity;
    }
    public double getStorageCapacity() {
        return storageCapacity;
    }

    @Override
    public String toString() {
        return getPlaneMake() + " " + getPlaneModel() + " Reg # " + getRegistrationNumber();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Plane plane = (Plane) obj;
        return (plane.getPlaneModel().equals(getPlaneModel()) &&
                plane.getPlaneMake().equals(getPlaneMake()) &&
                (plane.getEconomySeats() == getEconomySeats()) &&
                (plane.getFirstClassSeats() == getFirstClassSeats()) &&
                (plane.getStorageCapacity() == getStorageCapacity()) &&
                plane.getRegistrationNumber().equals(getRegistrationNumber()));
    }
}
