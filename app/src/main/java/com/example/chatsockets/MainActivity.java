package com.example.chatsockets;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button createConnection;
    Button connect;
    String ipAddres;
    String connectToIpAddress;
    Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        Server server = new Server();
        client = new Client();
        ipAddres = NetworkHandler.getIpAddres(MainActivity.this);
        Log.i("Myip", "onCreate: " + ipAddres);
        createConnection = findViewById(R.id.createConnectionButton);
        connect = findViewById(R.id.connectButton);

        createConnection.setOnClickListener(v -> {
            server.start(ipAddres, 8080);
            Intent intent = new Intent(MainActivity.this,ChatView.class);
            startActivity(intent);
        });

        connect.setOnClickListener(v -> {
            Log.i("Myip", "Cliquei no botão");
            AlertDialog dialog = createDialog();
            dialog.show();

        });

    }

    private AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira um IP");
        EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("SIM", (dialog, which) -> {
                    connectToIpAddress = input.getText().toString();
            Log.i("Myip", "onClick: " + connectToIpAddress);

            client.connect(this,connectToIpAddress, 8080);
                }
        );

        builder.setNegativeButton("NÃO", (dialog, which) ->
            dialog.dismiss()
        );

        return builder.create();
    }
}