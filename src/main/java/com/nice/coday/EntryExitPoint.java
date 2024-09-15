package com.nice.coday;

public class EntryExitPoint {
    private String entryExitPoint;
    private int distanceFromStart;

    public EntryExitPoint(String entryExitPoint, int distanceFromStart) {
        this.entryExitPoint = entryExitPoint;
        this.distanceFromStart = distanceFromStart;
    }

    public String getEntryExitPoint() {
        return entryExitPoint;
    }

    public void setEntryExitPoint(String entryExitPoint) {
        this.entryExitPoint = entryExitPoint;
    }

    public int getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }
}
