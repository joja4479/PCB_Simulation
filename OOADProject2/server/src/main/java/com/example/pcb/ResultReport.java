package com.example.pcb;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultReport {

    @JsonProperty
    private String pcbType;

    @JsonProperty
    private int totalSubmitted;

    @JsonProperty
    private int passed;

    @JsonProperty
    private FailureLog failureLog;

    // Required no-arg constructor for Jackson
    public ResultReport() {}

    public ResultReport(String pcbType, int totalSubmitted, int passed, FailureLog failureLog) {
        this.pcbType = pcbType;
        this.totalSubmitted = totalSubmitted;
        this.passed = passed;
        this.failureLog = failureLog;
    }

    // Getters for serialization
    public String getPcbType() {
        return pcbType;
    }

    public int getTotalSubmitted() {
        return totalSubmitted;
    }

    public int getPassed() {
        return passed;
    }

    public FailureLog getFailureLog() {
        return failureLog;
    }

    // Console display (unchanged)
    public void display() {
        System.out.println("PCB type: " + pcbType);
        System.out.println("PCBs run: " + totalSubmitted);
        System.out.println("Station Failures");
        failureLog.getStationFailures().forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println("PCB Defect Failures");
        failureLog.getDefectFailures().forEach((k, v) -> System.out.println(k + " " + v));
        System.out.println("Final Results");
        System.out.println("Total failed PCBs: " + (totalSubmitted - passed));
        System.out.println("Total PCBs produced: " + passed);
    }
}
