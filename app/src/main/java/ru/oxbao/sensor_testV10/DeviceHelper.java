package ru.oxbao.sensor_testV10;


import android.os.Build;

public class DeviceHelper
{
    public static boolean g_moreGingerBread;
    private static DeviceHelper ourInstance = new DeviceHelper();

    public static DeviceHelper getInstance()
    {
        return ourInstance;
    }

    private DeviceHelper()
    {
    }

    public static void GetOsVersion()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        {
            g_moreGingerBread = true;
        } else
        {
            g_moreGingerBread = false;
        }
    }
}
