package com.example.pcb;
// GatewayBoard.java
import java.util.HashMap;
import java.util.Map;

public class GatewayBoard implements PCB {
    private final Map<String, Double> defectRates = new HashMap<>();

    public GatewayBoard() {
        defectRates.put("Place Components", 0.004);
        defectRates.put("Optical Inspection", 0.004);
        defectRates.put("Hand Soldering/Assembly", 0.008);
        defectRates.put("Test (ICT or Flying Probe)", 0.008);
    }

    public double getDefectRate(String stationName) {
        return defectRates.getOrDefault(stationName, 0.0);
    }

    public String getType() {
        return "Gateway Board";
    }
}