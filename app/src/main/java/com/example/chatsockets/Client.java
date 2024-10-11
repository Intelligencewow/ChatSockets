package com.example.chatsockets;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;



public class Client{

    static Socket socket;
    private MessageListener messageListener;
    private PrintStream out;

    public Client() {}

    public void connect(Context context, String host, int port) {
        new Thread(() -> {
            Log.i("Client", "Tentando se conectar ao servidor em " + host + ":" + port);
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 10000);

                out = new PrintStream(socket.getOutputStream());
                out.println("Macaco");
                Log.i("Client", "Conectado ao servidor: " + host + ":" + port);

                NetworkHandler.getIpAddres(context);
            } catch (IOException e) {
                Log.e("Client", "Falha ao conectar ao servidor: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("Client", "Erro inesperado: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void exchangeMessage(String string) {

        new Thread(() -> {
            try {
                out = new PrintStream(socket.getOutputStream(), true);
                out.println(string);

                Log.i("Client", "exchangeMessage: " + string);
            } catch (IOException e) {
                Log.e("Client", "exchangeMessage: " + e);
            }

        }).start();

    }

    public void recieveMessage() {
        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    if(messageListener != null){
                        messageListener.onMessageReceived(new Message(inputLine, false));
                    }
                    System.out.println(in.readLine());
                }
            } catch (IOException e) {
                Log.e("Client", "exchangeMessage: " + e);
            }
        }).start();
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}




