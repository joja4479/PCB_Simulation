package com.example.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import com.google.gson.*;

public class SimulationClient {

    private static final String BASE_URL = "http://localhost:8080";

    public static void main(String[] args) throws IOException {
        String[] pcbTypes = {"test", "sensor", "gateway"};

    for (String pcbType : pcbTypes) {
        System.out.println("Starting simulation for PCB type: " + pcbType);
        sendPost(BASE_URL + "/simulate?type=" + pcbType);

        try { Thread.sleep(500); } catch (InterruptedException ignored) {}

        String json = sendGet(BASE_URL + "/simulate?type=" + pcbType);
        printReport(json);
        System.out.println();
}
    }

    private static void sendPost(String urlString) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        int responseCode = conn.getResponseCode();
        System.out.println("POST response code: " + responseCode);
        printResponse(conn);
        conn.disconnect();
    }

    private static String sendGet(String urlString) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        int responseCode = conn.getResponseCode();
        System.out.println("GET response code: " + responseCode);
        String response = readResponse(conn);
        conn.disconnect();
        return response;
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) sb.append(line);
        in.close();
        return sb.toString();
    }

    private static void printResponse(HttpURLConnection conn) throws IOException {
        System.out.println(readResponse(conn));
    }

    private static void printReport(String json) {
        Gson gson = new Gson();
        JsonObject obj = gson.fromJson(json, JsonObject.class);

        System.out.println("\n--- Simulation Report ---");
        System.out.println("PCB type: " + obj.get("pcbType").getAsString());
        System.out.println("PCBs run: " + obj.get("totalSubmitted").getAsInt());

        JsonObject stationFailures = obj.getAsJsonObject("failureLog").getAsJsonObject("stationFailures");
        JsonObject defectFailures = obj.getAsJsonObject("failureLog").getAsJsonObject("defectFailures");

        System.out.println("Station Failures");
        for (Map.Entry<String, JsonElement> entry : stationFailures.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getAsInt());
        }

        System.out.println("PCB Defect Failures");
        for (Map.Entry<String, JsonElement> entry : defectFailures.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().getAsInt());
        }

        int total = obj.get("totalSubmitted").getAsInt();
        int passed = obj.get("passed").getAsInt();
        System.out.println("Final Results");
        System.out.println("Total failed PCBs: " + (total - passed));
        System.out.println("Total PCBs produced: " + passed);
    }
}
