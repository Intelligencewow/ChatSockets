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
}
