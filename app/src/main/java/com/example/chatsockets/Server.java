package com.example.chatsockets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{
    ServerSocket serverSocket;
    static MessageListener messageListener;
    private static List<PrintWriter> socketoutputs = new ArrayList<>() ;
    private boolean isActivityStarted = false;
    private static String userName;

    private static final ExecutorService sendExecutor = Executors.newSingleThreadExecutor();
    private static final ExecutorService receiveExecutor = Executors.newSingleThreadExecutor();



    public Server() {}

    public void start(Context context, String ip, int port) {
        new Thread(() -> {
            try {
                InetAddress address = InetAddress.getByName(ip);
                serverSocket = new ServerSocket(port, 50, address);
                Log.i("ChatSocketss", "Servidor iniciado no IP " + address + " e porta " + port);


                while (true){

                    new ClientHandler(serverSocket.accept()).start();
                    Log.i("ChatSocketss", "Cliente se conectou;");

                    if (!isActivityStarted) {
                        isActivityStarted = true;
                        Intent intent = new Intent(context, ServerChatActivity.class);
                        intent.putExtra("userName", userName);
                        context.startActivity(intent);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ).start();
    }

    public static void exchangeMessage(String string, String userName, PrintWriter sender) {

        receiveExecutor.execute(() -> {
            for (PrintWriter socket1 : socketoutputs) {
                if (socket1 != null && socket1 != sender) {
                    socket1.println(string);
                    Log.i("ChatSocketss", "Enviei mensagens usando o for: "  + string);
                }

            }
        });

    }


    public static void exchangeMessage(String string, String userName) {

         sendExecutor.execute(()-> {
            for (PrintWriter socket1 : socketoutputs) {
                if (socket1 != null) {
                    socket1.println(userName + ":" +  string);
                    Log.i("ChatSocketss", "exchangeMessage: " + userName + ":" +  string);
                }

            }
        });

    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public static class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {

                try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                socketoutputs.add(out);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    Message messageobject = null;
                    if (messageListener != null) {
                        Log.i("ChatSocketss", "Mensagem que recebi: " + inputLine);
                        messageobject = new Message(inputLine, false);
                        messageListener.onMessageReceived(messageobject);
                    }
                    exchangeMessage(inputLine, messageobject.getSender(), out);
                }

                Log.i("ChatSocketss", "Um cliente se desconectou");

                socketoutputs.remove(out);
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setUserName(String userName) {
        Server.userName = userName;
    }
}
