package com.example.backend;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        origins = "http://localhost:5173",
        allowedHeaders = "*",
        methods = {RequestMethod.POST, RequestMethod.OPTIONS}
)
public class ApiKeyController {

    private static final String DB_URL =
            "jdbc:mysql://127.0.0.1:3306/api_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "Mini123";

    @PostMapping("/create-key")
    public ResponseEntity<String> createKey(@RequestBody ApiKey apiKey) {

        String generatedKey = UUID.randomUUID().toString();

        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS api_keys (
                id INT AUTO_INCREMENT PRIMARY KEY,
                owner_name VARCHAR(255) NOT NULL,
                api_key VARCHAR(255) NOT NULL,
                rate_limit INT NOT NULL,
                window_seconds INT NOT NULL
            )
        """;

        String insertSQL = """
            INSERT INTO api_keys
            (owner_name, api_key, rate_limit, window_seconds)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            try (PreparedStatement createStmt = conn.prepareStatement(createTableSQL)) {
                createStmt.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement(insertSQL)) {
                ps.setString(1, apiKey.getOwnerName());
                ps.setString(2, generatedKey);
                ps.setInt(3, apiKey.getRateLimit());
                ps.setInt(4, apiKey.getWindowSeconds());

                int rows = ps.executeUpdate();
                System.out.println("ROWS INSERTED = " + rows);
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("API KEY CREATED: " + generatedKey);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database error while creating API key");
        }
    }
}
