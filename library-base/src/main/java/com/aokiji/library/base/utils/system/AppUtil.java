package com.aokiji.library.base.utils.system;

import android.os.Build;

import java.net.NetworkInterface;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangdonghai on 2018/7/2.
 */

public class AppUtil {

    public static String getMacAddr() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : interfaces) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return "02:00:00:00:00:00";
    }

    public static String getUniquePsuedoID() {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data

        String[] supportedABIArray;
        if (Build.VERSION.SDK_INT >= 21) {
            supportedABIArray = Build.SUPPORTED_ABIS;
        } else {
            supportedABIArray = new String[]{Build.CPU_ABI};
        }

        String supportedABIs = "";
        try {
            for (String s : supportedABIArray) {
                supportedABIs += s;
            }
        } catch (Exception e) {
            supportedABIs = "";
        }

        String m_szDevIDShort = "35";
        if (null != Build.BOARD) m_szDevIDShort += (Build.BOARD.length() % 10);
        if (null != Build.BRAND) m_szDevIDShort += (Build.BRAND.length() % 10);
        if (null != supportedABIs) m_szDevIDShort += (supportedABIs.length() % 10);
        if (null != Build.DEVICE) m_szDevIDShort += (Build.DEVICE.length() % 10);
        if (null != Build.MANUFACTURER) m_szDevIDShort += (Build.MANUFACTURER.length() % 10);
        if (null != Build.MODEL) m_szDevIDShort += (Build.MODEL.length() % 10);
        if (null != Build.PRODUCT)
            m_szDevIDShort += (Build.PRODUCT.length() % 10);// Thanks to @Roman SL!
        // http://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        String serial = null;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();

            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            // serial = "serial"; // constant value
            serial = "" + Calendar.getInstance().getTimeInMillis(); // variable value
        }

        // Thanks @Joe!
        // http://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to create a unique
        // identifier
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

}
