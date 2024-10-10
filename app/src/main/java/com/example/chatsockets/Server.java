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

public class Server {
    ServerSocket serverSocket;
    Socket socket;

    public Server() {
    }

    public void start(Context context, String ip, int port) {
        new Thread(() -> {
            try {
                Intent intent = new Intent(context, ChatActivity.class);
                InetAddress address = InetAddress.getByName(ip);


                serverSocket = new ServerSocket(port, 50, address);
                while (true) {
                    Log.i("Server", "Servidor iniciado no IP " + address + " e porta " + port);
                    context.startActivity(intent);
                    new ClientHandler(serverSocket.accept()).start();
                    Log.i("Server", "O CLIENTE SE CONECTOU");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
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
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
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
    }


}
