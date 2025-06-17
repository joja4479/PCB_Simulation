package com.example.pcb;
// SimulationController.java
import java.util.List;

public class SimulationController {
    private final List<Station> stations = List.of(
            new Station("Apply Solder Paste", false),
            new Station("Place Components", true),
            new Station("Reflow Solder", false),
            new Station("Optical Inspection", true),
            new Station("Hand Soldering/Assembly", true),
            new Station("Cleaning", false),
            new Station("Depanelization", false),
            new Station("Test (ICT or Flying Probe)", true));

    private ResultReport report;

    public void simulate(PCB pcb, int runs) {
        FailureLog log = new FailureLog();
        int passed = 0;

        for (int i = 0; i < runs; i++) {
            boolean failed = false;
            for (Station station : stations) {
                boolean result = station.processBoard(pcb);
                if (result) {
                    if (station.checksDefects()) {
                        log.recordDefectFailure(station.getName());
                    } else {
                        log.recordStationFailure(station.getName());
                    }
                    failed = true;
                    break;
                }
            }
            if (!failed)
                passed++;
        }
        report = new ResultReport(pcb.getType(), runs, passed, log);
    }

    public ResultReport getReport() {
        return report;
    }
}