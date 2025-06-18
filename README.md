# PCB Assembly Line Simulation (Client–Server)

This project simulates a PCB (Printed Circuit Board) assembly process, including station-specific defect and failure rates. It uses a Java-based server to run simulations and expose REST endpoints, and a Java-based client to initiate simulations and store results.

---

## 📦 Project Structure

pcb_sim_backup/
├── client/ # Java console client
├── server/ # Spring Boot server
├── simulation.db # SQLite database (created by server)
└── README.md

---

## 🛠 Technologies Used

- Java 17
- Spring Boot 3
- SQLite (via JDBC)
- Gson (for JSON serialization)
- Maven (build system)

---

## 🚀 How It Works

1. The **client** sends a POST request to the server to simulate one of three PCB types:
   - `testboard`
   - `sensorboard`
   - `gatewayboard`

2. The server runs a simulation of 1000 PCBs through multiple stations.

3. Each station has:
   - A chance to fail independently (`station failure`)
   - A chance to detect a PCB defect (`defect failure`)

4. After the simulation, the **client** captures the results and sends them to the server’s `/store` endpoint.

5. The server saves results to a local **SQLite** database.

---

## 🧪 API Endpoints

| Endpoint         | Method | Description                              |
|------------------|--------|------------------------------------------|
| `/simulate`      | POST   | Runs a simulation for a PCB type         |
| `/results`       | GET    | Returns the most recent simulation report |
| `/store`         | POST   | Accepts and stores simulation JSON       |

> 💡 The server automatically creates the `simulation_results` table on startup if it doesn't exist.

---

## 🧰 Prerequisites

- Java 17
- Maven
- (Optional) `sqlite3` CLI or DB Browser for SQLite

---

## 🔧 Building and Running

### Server

cd server
mvn clean install
java -jar target/server-1.0-SNAPSHOT.jar

The server will start on http://localhost:8080

### Client

In a separate terminal:

cd client
mvn clean install
java -cp target/classes:~/.m2/repository/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar com.example.client.SimulationClient

Follow the prompt to select a PCB type.

---

## 🗄 Viewing Stored Data

### Option 1: CLI

sqlite3 simulation.db
sqlite> SELECT * FROM simulation_results;

### Option 2: GUI

Use DB Browser for SQLite to inspect and query the database visually.

---

## 🧼 Notes

    The database is created in the server's working directory as simulation.db.

    The client must be run after the server is up and listening.

    The client automatically posts the simulation results to the server.

