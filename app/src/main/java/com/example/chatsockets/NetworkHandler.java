package com.example.chatsockets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.NetworkCapabilities;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;

public class NetworkHandler {

    private NetworkHandler(){
        throw new IllegalStateException("Utility Class");
    }

    public static String getIpAddres(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

        if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                LinkProperties linkProperties = connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
                //String linkAddress = linkProperties.getLinkAddresses().get(1).toString();
                for(LinkAddress linkadress : linkProperties.getLinkAddresses()){
                    InetAddress address = linkadress.getAddress();
                    if (address instanceof Inet4Address){
                        String ipAddress = address.getHostAddress();
                        Log.i("ChatSocketss", "getIpAddress: " + ipAddress);
                        return ipAddress;
                    }
                }
            }
        return null;
    }

    public static void pingHost(String host) {
        new Thread(() -> {
            try {
                Log.i("ChatSocketss", "pingHost: ");
                // Executa o comando de ping
                Process process = Runtime.getRuntime().exec("ping -c 4 " + host);

                // Lê a saída do comando ping
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                StringBuilder output = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                // Aguarda o processo terminar e pega o código de saída
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    Log.i("ChatSocketss", "Ping bem-sucedido:\n" + output);
                } else {
                    Log.e("ChatSocketss", "Ping falhou com código de saída: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                Log.e("ChatSocketss", "Erro ao executar o ping: " + e.getMessage());
            }
        }).start();
    }
}
