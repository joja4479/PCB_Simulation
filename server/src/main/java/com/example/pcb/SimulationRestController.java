package com.example.pcb;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/simulate")
public class SimulationRestController {

    private ResultReport lastReport;

    @GetMapping
    public ResultReport simulateGet(@RequestParam("type") String type) {
        return runSimulation(type);
    }

    @PostMapping
    public ResultReport simulatePost(@RequestParam("type") String type) {
        return runSimulation(type);
    }

    @GetMapping("/report")
    public ResultReport getLastReport() {
        if (lastReport == null) {
            throw new IllegalStateException("No simulation has been run yet.");
        }
        return lastReport;
    }

    private ResultReport runSimulation(String type) {
        PCB pcb;
        switch (type.toLowerCase()) {
            case "sensor":
                pcb = new SensorBoard();
                break;
            case "gateway":
                pcb = new GatewayBoard();
                break;
            case "test":
                pcb = new TestBoard();
                break;
            default:
                throw new IllegalArgumentException("Unknown PCB type: " + type);
        }

        SimulationController controller = new SimulationController();
        controller.simulate(pcb, 1000);
        lastReport = controller.getReport(); // store latest report
        return lastReport;
    }
}
