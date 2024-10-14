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
            Log.i("ChatSocketss", "Tentando se conectar ao servidor em " + host + ":" + port);
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 10000);

                recieveMessage();
                out = new PrintStream(socket.getOutputStream(), true);
                Log.i("ChatSocketss", "Conectado ao servidor: " + host + ":" + port);

            } catch (IOException e) {
                Log.e("ChatSocketss", "Falha ao conectar ao servidor: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("ChatSocketss", "Erro inesperado: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void testConnect(Context context, String host, int port) {
        new Thread(() -> {
            Log.i("ChatSocketss", "Testando Conexão com o servidor: " + host + ":" + port);
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 10000);
                socket.close();

            } catch (IOException e) {
                Log.e("ChatSocketss", "Falha ao conectar ao servidor: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("ChatSocketss", "Erro inesperado: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void exchangeMessage(String message, String userName) {

        new Thread(() -> {
                if (socket != null && !socket.isClosed()) {
                    Log.i("ChatSocketss", "Mandei mensagem pra fora: " + message);
                    out.println(userName + ":" + message);
                } else {
                    Log.i("ChatSocketss", "O cliente está fechado ou nulo");

                }
        }).start();

    }

    public void recieveMessage() {
        new Thread(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                        Log.i("ChatSocketss", "recieveMessage: " + inputLine);
                        messageListener.onMessageReceived(new Message(inputLine, false));
                }
            } catch (IOException e) {
                Log.e("ChatSocketss", "recieveMessageException: " + e);
            }
        }).start();
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

}




