package com.example.chatsockets;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button createConnection;
    Button connect;
    String ipAddres;
    String connectToIpAddress;
    Client client;
    TextInputEditText userName;


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

        userName = findViewById(R.id.userName);
        //Log.i("ChatSocketss", "USERNAME: " + userName.getText().toString());
        Server server = new Server();
        client = new Client();
        ipAddres = NetworkHandler.getIpAddres(MainActivity.this);
        Log.i("ChatSocketss", "onCreate: " + ipAddres);
        createConnection = findViewById(R.id.createConnectionButton);
        connect = findViewById(R.id.connectButton);


        createConnection.setOnClickListener(v -> {
            AlertDialog dialog = createWaitForConnectionDialog();
            dialog.show();
            server.setUserName(userName.getText().toString());
            server.start(this, ipAddres, 8080);
            //Intent intent = new Intent(this, ChatActivity.class);
            //startActivity(intent);
        });

        connect.setOnClickListener(v -> {
            Log.i("ChatSocketss", "Cliquei no botão");
            AlertDialog dialog = createDialog();
            dialog.show();

        });

    }

    private AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insira um IP");
        EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("SIM", (dialog, which) -> {
            connectToIpAddress = input.getText().toString();
            Log.i("ChatSocketss", "onClick: " + connectToIpAddress);
            client.testConnect(this, connectToIpAddress, 8080);
            Intent intent = new Intent(this, ClientChatActivity.class);
            //intent.putExtra("userName", )
            intent.putExtra("connectToIpAddress", connectToIpAddress);
            intent.putExtra("Port", 8080);
            intent.putExtra("userName", userName.getText().toString());
            startActivity(intent);
        });

        builder.setNegativeButton("NÃO", (dialog, which) ->
                dialog.dismiss()
        );

        return builder.create();
    }

    private AlertDialog createWaitForConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Esperando conexão");
        builder.setMessage("Compartilhe esse ip para o usuário se conectar no seu servidor : " + ipAddres);


        return builder.create();
    }
}