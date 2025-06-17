package com.example.pcb;
// FailureLog.java
import java.util.HashMap;
import java.util.Map;

public class FailureLog {
    private final Map<String, Integer> failuresByStation = new HashMap<>();
    private final Map<String, Integer> defectsByStation = new HashMap<>();

    public void recordStationFailure(String station) {
        failuresByStation.put(station, failuresByStation.getOrDefault(station, 0) + 1);
    }

    public void recordDefectFailure(String station) {
        defectsByStation.put(station, defectsByStation.getOrDefault(station, 0) + 1);
    }

    public Map<String, Integer> getStationFailures() {
        return failuresByStation;
    }

    public Map<String, Integer> getDefectFailures() {
        return defectsByStation;
    }
}