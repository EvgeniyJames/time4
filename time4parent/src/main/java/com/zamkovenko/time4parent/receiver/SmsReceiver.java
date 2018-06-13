package com.zamkovenko.time4parent.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

import com.zamkovenko.time4parent.activity.EnterParentPhoneActivity;
import com.zamkovenko.time4parent.service.SmsProcessorService;
import com.zamkovenko.utils.ParseUtils;

/**
 * User: Yevgeniy Zamkovenko
 * Date: 10.12.2017
 */

public class SmsReceiver extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        if (intent != null && intent.getAction() != null &&
                ACTION.compareToIgnoreCase(intent.getAction()) == 0) {
            Bundle extras = intent.getExtras();
            assert extras != null;
            Object[] pduArray = (Object[]) extras.get("pdus");
            assert pduArray != null;
            SmsMessage[] messages = new SmsMessage[pduArray.length];
            for (int i = 0; i < pduArray.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pduArray[i]);
            }

            dispatchMessage(messages);
        }
    }

    private void dispatchMessage(SmsMessage[] messages) {
        String from = ParseUtils.GetClearNumer(messages[0].getDisplayOriginatingAddress());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String parentPhone = prefs.getString(EnterParentPhoneActivity.PARAM_PARENT_PHONE, "");

        boolean isOk = from.contains(parentPhone);
        if (isOk) {
            StringBuilder bodyText = new StringBuilder();

            for (SmsMessage message : messages) {
                bodyText.append(message.getMessageBody());
            }

            String body = bodyText.toString();

            Intent smsServiceIntent = new Intent(context, SmsProcessorService.class);
            smsServiceIntent.putExtra(SmsProcessorService.PARAM_FROM, from);
            smsServiceIntent.putExtra(SmsProcessorService.PARAM_BODY, body);
            context.startService(smsServiceIntent);

            abortBroadcast();
        }
    }
}
