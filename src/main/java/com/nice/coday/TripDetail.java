package com.nice.coday;

public class TripDetail {
    private String id;
    private String vehicleType;
    private int remainingBatteryPercentage;
    private String entryPoint;
    private String exitPoint;

    public TripDetail(String id, String vehicleType, int remainingBatteryPercentage, String entryPoint, String exitPoint) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.remainingBatteryPercentage = remainingBatteryPercentage;
        this.entryPoint = entryPoint;
        this.exitPoint = exitPoint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getRemainingBatteryPercentage() {
        return remainingBatteryPercentage;
    }

    public void setRemainingBatteryPercentage(int remainingBatteryPercentage) {
        this.remainingBatteryPercentage = remainingBatteryPercentage;
    }

    public String getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(String entryPoint) {
        this.entryPoint = entryPoint;
    }

    public String getExitPoint() {
        return exitPoint;
    }

    public void setExitPoint(String exitPoint) {
        this.exitPoint = exitPoint;
    }
}
