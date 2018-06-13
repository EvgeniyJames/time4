package com.zamkovenko.time4parent.Utils;

import android.telephony.SmsManager;

/**
 * User: Yevgeniy Zamkovenko
 * Date: 15.05.2018
 */
public class SmsUtils {
    public static void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);

//            System.out.println("smsManager.sendTextMessage("+phoneNo+", null, "+msg+", null, null);\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
