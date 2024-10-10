package com.example.chatsockets;

import android.content.Context;

import android.util.Log;

import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.Socket;


public class Client {

    public Client() {}

    public void connect(Context context, String host, int port) {
        new Thread(() -> {
            Log.i("Client", "Tentando se conectar ao servidor em " + host + ":" + port);
            try (Socket socket = new Socket()) {

                NetworkHandler.getIpAddres(context);
                socket.connect(new InetSocketAddress(host, port), 10000);
                NetworkHandler.getIpAddres(context);
                Log.i("Client", "Conectado ao servidor: " + host + ":" + port);
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



