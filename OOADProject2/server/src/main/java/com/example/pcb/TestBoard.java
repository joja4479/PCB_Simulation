package com.example.pcb;
// TestBoard.java
import java.util.HashMap;
import java.util.Map;

public class TestBoard implements PCB {
    private final Map<String, Double> defectRates = new HashMap<>();

    public TestBoard() {
        defectRates.put("Place Components", 0.05);
        defectRates.put("Optical Inspection", 0.10);
        defectRates.put("Hand Soldering/Assembly", 0.05);
        defectRates.put("Test (ICT or Flying Probe)", 0.10);
    }

    public double getDefectRate(String stationName) {
        return defectRates.getOrDefault(stationName, 0.0);
    }

    public String getType() {
        return "Test Board";
    }
}






