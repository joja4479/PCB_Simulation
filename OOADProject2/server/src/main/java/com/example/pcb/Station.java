package com.example.pcb;
// Station.java
import java.util.Random;

public class Station {
    private static final double STATION_FAILURE_RATE = 0.002;
    private final String name;
    private final boolean checksDefects;
    private final Random random = new Random();

    public Station(String name, boolean checksDefects) {
        this.name = name;
        this.checksDefects = checksDefects;
    }

    public String getName() {
        return name;
    }

    public boolean checksDefects() {
        return checksDefects;
    }

    public boolean processBoard(PCB pcb) {
        if (random.nextDouble() < STATION_FAILURE_RATE)
            return true;
        if (checksDefects && random.nextDouble() < pcb.getDefectRate(name))
            return true;
        return false;
    }
}