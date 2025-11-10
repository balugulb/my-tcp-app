package com.example.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * MainActivity
 *
 * A simple Android app that sends a message to a specified server over TCP.
 * The layout contains two EditText fields:
 * 1. Message text
 * 2. Server IP address
 *
 * The message is sent asynchronously using an ExecutorService to avoid blocking the UI thread.
 */
public class MainActivity extends AppCompatActivity {
    private EditText e1, e2; // EditText fields for message and server IP
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    // Thread pool for background operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge layout for modern UI design
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Adjust padding for system bars (status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        e1 = (EditText) findViewById(R.id.editText);
        e2 = (EditText) findViewById(R.id.editTextText);
    }

    /**
     * Sends the message to the server when the button is clicked.
     * Reads the user input, validates it, and sends the message asynchronously.
     *
     * @param view The clicked View (Button)
     */
    public void send_data(View view) {
        String message = e1.getText().toString().trim();  // Message from EditText
        String ip = e2.getText().toString().trim();       // IP address from EditText

        // Input validation
        if (message.isEmpty() || ip.isEmpty()) {
            Toast.makeText(this, "Bitte Nachricht und IP-Adresse eingeben", Toast.LENGTH_SHORT).show();
            return;
        }

        String msg = message + "\r\n"; // Append line break

        // Asynchronous network thread
        executor.execute(() -> {
            try (Socket socket = new Socket(ip, 3333);
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                writer.write(msg); // Send message
                writer.flush();

                // UI feedback on the main thread
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Nachricht gesendet!", Toast.LENGTH_SHORT).show()
                );

            } catch (IOException e) {
                e.printStackTrace();

                // Error feedback on the main thread
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Verbindung fehlgeschlagen: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow(); // Shutdown background threads when the app is closed
    }
}