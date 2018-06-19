package com.zamkovenko.time4child.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * User: Yevgeniy Zamkovenko
 * Date: 15.05.2018
 */
public class SmsUtils {

    public static void sendSMS(String phoneNo, String msg, Context context) {
//        try {
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//            int simId = prefs.getInt(SimUtils.PARAM_SIM_CARD, -1);
//
//            if (simId != -1) {
//                sendSMS(context, simId, phoneNo, null, msg, null, null);
//            }
//            else{
//                Toast.makeText(context, "simId != -1", Toast.LENGTH_SHORT).show();
//            }
//
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
////            System.out.println("smsManager.sendTextMessage("+phoneNo+", null, "+msg+", null, null);\n");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    private static boolean sendSMS(Context ctx, int simID, String toNum, String centerNum, String smsText, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        String name;

        try {
            if (simID == 0) {
                name = "isms";
                // for model : "Philips T939" name = "isms0"
            } else if (simID == 1) {
                name = "isms2";
            } else {
                throw new Exception("can not get service which for sim '" + simID + "', only 0,1 accepted as values");
            }
            Method method = Class.forName("android.os.ServiceManager").getDeclaredMethod("getService", String.class);
            method.setAccessible(true);
            Object param = method.invoke(null, name);

            method = Class.forName("com.android.internal.telephony.ISms$Stub").getDeclaredMethod("asInterface", IBinder.class);
            method.setAccessible(true);
            Object stubObj = method.invoke(null, param);
            if (Build.VERSION.SDK_INT < 18) {
                method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                method.invoke(stubObj, toNum, centerNum, smsText, sentIntent, deliveryIntent);
            } else {
                method = stubObj.getClass().getMethod("sendText", String.class, String.class, String.class, String.class, PendingIntent.class, PendingIntent.class);
                method.invoke(stubObj, ctx.getPackageName(), toNum, centerNum, smsText, sentIntent, deliveryIntent);
            }

            return true;
        } catch (ClassNotFoundException e) {
            Log.e("apipas", "ClassNotFoundException:" + e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e("apipas", "NoSuchMethodException:" + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e("apipas", "InvocationTargetException:" + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e("apipas", "IllegalAccessException:" + e.getMessage());
        } catch (Exception e) {
            Log.e("apipas", "Exception:" + e.getMessage());
        }
        return false;
    }

}
