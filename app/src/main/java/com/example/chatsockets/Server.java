package com.example.chatsockets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    ServerSocket serverSocket;
    Socket socket;
    static MessageListener messageListener;
    private PrintStream out;


    public Server() {
    }

    public void start(Context context, String ip, int port) {
        new Thread(() -> {
            try {
                Intent intent = new Intent(context, ChatActivity.class);
                InetAddress address = InetAddress.getByName(ip);
                serverSocket = new ServerSocket(port, 50, address);
                Log.i("Server", "Servidor iniciado no IP " + address + " e porta " + port);
                socket = serverSocket.accept();
                context.startActivity(intent);
                Log.i("Server", "O CLIENTE SE CONECTOU");
                recieveMessage();
                /*
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String x;
                while ((x = reader.readLine())!= null){
                    Log.i("SERVER", "cliente: " + x);
                }*/

            } catch (IOException e) {
                e.printStackTrace();
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
                        Log.i("SERVER", "Enviando para o messageLIstener: ");
                        messageListener.onMessageReceived(new Message(inputLine, false));
                    }
                    Log.i("SERVER", "cliente: " + inputLine);
                }
            } catch (IOException e) {
                Log.e("Client", "exchangeMessage: " + e);
            }
        }).start();
    }

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }


    /*
    public class ClientHandler extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {

                    if(messageListener != null){
                        messageListener.onMessageReceived(new Message(inputLine, true));
                    }

                    if (inputLine.equals("bye")) {
                        break;
                    }
                    out.println(inputLine);
                    System.out.println(in.readLine());
                }

                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/


}
