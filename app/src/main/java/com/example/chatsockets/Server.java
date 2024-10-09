package com.example.chatsockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Server {
    Socket socket;
    ServerSocket serverSocket;

    public Server() {}

    public void start(int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    serverSocket = new ServerSocket(port);

                    while (true) {
                        new ClientHandler(serverSocket.accept()).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    public static class ClientHandler extends Thread
    {
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
