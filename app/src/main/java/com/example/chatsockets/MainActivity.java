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

    Button createConnection, connect;
    String ipAddres;
    String connectToIpAddress;

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
        Client client = new Client();
        ipAddres = NetworkHandler.getIpAddres(MainActivity.this);
        Log.i("IP", "onCreate: " + ipAddres);
        createConnection = findViewById(R.id.createConnectionButton);
        connect = findViewById(R.id.connectButton);

        createConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                server.start(4000);
                Intent intent = new Intent(MainActivity.this,ChatView.class);
                startActivity(intent);
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = createDialog();
                dialog.show();
                Log.i("IP", "onClick: " + connectToIpAddress);
                client.connect(connectToIpAddress, 4000);
            }
        });

    }

    private AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira um IP");
        EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("SIM", (dialog, which) -> {
            connectToIpAddress = input.getText().toString();
        });

        builder.setNegativeButton("NÃO", (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }
}