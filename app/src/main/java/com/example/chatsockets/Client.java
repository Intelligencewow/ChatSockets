package com.example.chatsockets;

import android.util.Log;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client() {
    }

    public void connect(String host, int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("IP", "Vai tentar se conectar");
                try (Socket socket = new Socket(host, port);
                     PrintStream out = new PrintStream(socket.getOutputStream());
                     //Scanner scanner = new Scanner(System.in))
                )
                {
                    Log.i("IP", "Connected to " + host + ":" + port);
                    System.out.println("Connected to " + host + ":" + port);

//                    while(scanner.hasNextLine()){
//                        String teclado = scanner.nextLine();
//                        out.println(teclado);
//                    }

                } catch (IOException e) {
                    Log.e("IP", "Caiu na exceção", e );
                    e.printStackTrace();
                }
            }
        }).start();
    }



}


