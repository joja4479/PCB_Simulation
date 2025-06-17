package com.example.pcb;
// SensorBoard.java
import java.util.HashMap;
import java.util.Map;

public class SensorBoard implements PCB {
    private final Map<String, Double> defectRates = new HashMap<>();

    public SensorBoard() {
        defectRates.put("Place Components", 0.002);
        defectRates.put("Optical Inspection", 0.002);
        defectRates.put("Hand Soldering/Assembly", 0.004);
        defectRates.put("Test (ICT or Flying Probe)", 0.004);
    }

    public double getDefectRate(String stationName) {
        return defectRates.getOrDefault(stationName, 0.0);
    }

    public String getType() {
        return "Sensor Board";
    }
}