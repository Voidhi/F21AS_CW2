package models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Log {

    // Singleton instance
    private static Log instance = null;

    // Internal list of log messages
    private final List<String> logs;

    // Private constructor
    private Log() {
        logs = new ArrayList<>();
    }

    // Thread-safe singleton accessor
    public static synchronized Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    // Add a timestamped event to the log
    public synchronized void logEvent(String message) {
        String timestamp = LocalDateTime.now().toString();
        logs.add("[" + timestamp + "] " + message);
    }

    // Optionally log and print
    public synchronized void logAndPrint(String message) {
        logEvent(message);
        System.out.println("LOG: " + message);
    }

    // Write all logs to a file
    public synchronized void writeToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String log : logs) {
                writer.println(log);
            }
        } catch (IOException e) {
            System.err.println("Error writing log file: " + e.getMessage());
        }
    }
}
