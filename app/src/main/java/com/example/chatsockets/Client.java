package com.example.chatsockets;

import android.content.Context;

import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;


public class Client {

    public Client() {}

    public void connect(Context context, String host, int port) {
        new Thread(() -> {
            Log.i("Client", "Tentando se conectar ao servidor em " + host + ":" + port);
            try (Socket socket = new Socket()) {

                PrintStream out = new PrintStream(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in);


                NetworkHandler.getIpAddres(context);
                socket.connect(new InetSocketAddress(host, port), 10000);
                NetworkHandler.getIpAddres(context);

                Log.i("Client", "Conectado ao servidor: " + host + ":" + port);

                Intent intent = new Intent(context, ClientActivity.class);
                context.startActivity(intent);

            } catch (IOException e) {
                Log.e("Client", "Falha ao conectar ao servidor: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("Client", "Erro inesperado: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }

}



