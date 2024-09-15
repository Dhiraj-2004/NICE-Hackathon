package com.nice.coday;

public class ConsumptionDetails {

    private  String vehicleType;
    private Double totalUnitConsumed = 0.0;
    private Long totalTimeRequired = 0l;

    private Long numberOfTripsFinished = 0l;

    public ConsumptionDetails(String vehicleType)
    {
        this.vehicleType = vehicleType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Double getTotalUnitConsumed() {
        return totalUnitConsumed;
    }

    public void setTotalUnitConsumed(int unitConsumed) {
        this.totalUnitConsumed += unitConsumed;
    }

    public Long getTotalTimeRequired() {
        return totalTimeRequired;
    }

    public void setTotalTimeRequired(int timeRequired) {
        this.totalTimeRequired += timeRequired;
    }

    public Long getNumberOfTripsFinished() {
        return numberOfTripsFinished;
    }

    public void incrementtripsFinished()
    {
        this.numberOfTripsFinished += 1;
    }

}
