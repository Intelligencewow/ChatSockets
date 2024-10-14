package com.example.chatsockets;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {


    //This was static
    private Socket socket;

    private MessageListener messageListener;
    private PrintStream out;

    private final ExecutorService sendExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService receiveExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService connectExecutor = Executors.newSingleThreadExecutor();

    public Client() {

    }

    public void connect(String host, int port) {
        connectExecutor.execute(() -> {
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
        });
    }

    public void testConnect(String host, int port, ConnectionListener connectionListener) {
        connectExecutor.execute(() -> {
            Log.i("ChatSocketss", "Testando conexão com o servidor: " + host + ":" + port);
            boolean success = false;
            try {
                socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), 10000);
                success = true;

            } catch (IOException e) {
                Log.e("ChatSocketss", "Falha ao conectar ao servidor: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                Log.e("ChatSocketss", "Erro inesperado: " + e.getMessage());
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (connectionListener != null){
                    connectionListener.onConnectionresult(success);
                }
            }
        });
    }
    public void exchangeMessage(String message, String userName) {
        sendExecutor.execute(()-> {
            if (socket != null && !socket.isClosed()) {
                Log.i("ChatSocketss", "Mandei mensagem pra fora: " + message);
                out.println(userName + ":" + message);
            } else {
                Log.i("ChatSocketss", "O cliente está fechado ou nulo");

            }
        });

    }

    public void recieveMessage() {
        receiveExecutor.execute(() -> {
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
        });
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

}




