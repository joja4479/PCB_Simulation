package com.example.pcb;
// Main.java
public class Main {
    public static void main(String[] args) {
        SimulationController controller = new SimulationController();

        PCB[] pcbs = { new TestBoard(), new SensorBoard(), new GatewayBoard() };

        for (PCB pcb : pcbs) {
            controller.simulate(pcb, 1000);
            controller.getReport().display();
            System.out.println();
        }
    }
}