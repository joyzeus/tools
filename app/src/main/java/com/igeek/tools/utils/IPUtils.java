package com.igeek.tools.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import androidx.annotation.RequiresPermission;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtils {

    /**
     * 获取本机IP地址
     *
     * @param context 上下文环境变量
     * @return IP地址
     * @throws SocketException
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE})
    public static String getIPAddress(Context context) throws SocketException {
        String address = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                address = getIPAddressForNetwork();
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                address = getIPAddressForWifi(context);
            }
        }
        return address;
    }


    private static String getIPAddressForNetwork() throws SocketException {
        String address = null;
        for (Enumeration<NetworkInterface> enum1 = NetworkInterface.getNetworkInterfaces(); enum1.hasMoreElements(); ) {
            NetworkInterface networkInterface = enum1.nextElement();
            for (Enumeration<InetAddress> enum2 = networkInterface.getInetAddresses(); enum2.hasMoreElements(); ) {
                InetAddress inetAddress = enum2.nextElement();
                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                    address = inetAddress.getHostAddress();
                }
            }
        }
        return address;
    }

    @RequiresPermission(allOf = {Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_WIFI_STATE})
    private static String getIPAddressForWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return intIP2String(wifiInfo.getIpAddress());
    }

    private static String intIP2String(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
    }
}
