package com.example.pcb;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SimulationRestController {

    private final SimulationController controller = new SimulationController();

    static {
        initializeDatabase();
    }

    @PostMapping("/simulate")
    public ResultReport simulate(@RequestParam String pcbType) {
        PCB pcb;
        switch (pcbType.toLowerCase()) {
            case "sensorboard":
                pcb = new SensorBoard();
                break;
            case "gatewayboard":
                pcb = new GatewayBoard();
                break;
            case "testboard":
            default:
                pcb = new TestBoard();
                break;
        }

        controller.simulate(pcb, 1000);
        return controller.getReport();
    }

    @GetMapping("/results")
    public ResultReport getResults() {
        return controller.getReport();
    }

    @PostMapping("/store")
    public ResponseEntity<String> storeSimulationResult(@RequestBody ResultReport result) {
        System.out.println(">>> /store endpoint hit");
        System.out.println("pcbType = " + result.getPcbType());
        System.out.println("submitted = " + result.getTotalSubmitted());
        System.out.println("passed = " + result.getPassed());
        System.out.println("failureLog = " + new Gson().toJson(result.getFailureLog()));
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:simulation.db")) {
            String sql = "INSERT INTO simulation_results (pcbType, totalSubmitted, passed, failureLogJson) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, result.getPcbType());
            pstmt.setInt(2, result.getTotalSubmitted());
            pstmt.setInt(3, result.getPassed());

            Gson gson = new Gson();
            pstmt.setString(4, gson.toJson(result.getFailureLog()));
            pstmt.executeUpdate();
            return ResponseEntity.ok("Stored successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing data");
        }
    }

    private static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:simulation.db")) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS simulation_results (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    pcbType TEXT,
                    totalSubmitted INTEGER,
                    passed INTEGER,
                    failureLogJson TEXT
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 
