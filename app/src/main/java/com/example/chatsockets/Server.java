package com.example.chatsockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class Server {
    public static void main(String[] args) throws IOException {
        String ip;
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            Socket socket = serverSocket.accept();
        }

    }


}
