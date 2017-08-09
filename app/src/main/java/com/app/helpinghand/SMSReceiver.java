package com.app.helpinghand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Deepanshu on 12/2/2017.
 */

public class SMSReceiver extends BroadcastReceiver {

    private SharedPreferences preferences;
    static String text = "";

    public void setText(String text){
        this.text = text;
    }

    Custom custom;

    final SmsManager sms = SmsManager.getDefault();
    public void onReceive(Context context, Intent intent){

        System.out.println("ACTIVITY STARTED");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if(bundle!=null){
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0;i<msgs.length;i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();
                        String text = msg_from+"\t"+msgBody+"\n";

                        //MESSAGE PARSING CONDITION HERE  [[](.*?)[\\]]
                        String str = msgBody;
                        int paramsCount = 0;
                        for (int j = 0; j < str.length(); j++) {
                            if (str.charAt(j) == '[') {
                                paramsCount++;
                            }
                        }


                        String[] data = new String[paramsCount];
                        for (int k = 0; k < paramsCount; k++) {
                            data[k] = str.substring(1, str.indexOf("]"));

                            str = str.substring(str.indexOf("]") + 1);
                            System.out.println(data[k]);

                        }
                        //
                        if(data[0].equals("HelpCancelled")){

                            System.out.println(">>"+text);
                            setText("Your request has been successfully cancelled.");
                            Intent ine = new Intent(context, ReceiverEndActivity.class);
                            ine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(ine);
                            Toast.makeText(context,"Request Cancelled", Toast.LENGTH_LONG).show();
                        }
                        if(data[0].equals("HelpResponse")){

                            System.out.println(">>"+text);
                            setText(data[1]);
                            Intent ine = new Intent(context, ReceiverEndActivity.class);
                            ine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(ine);
                            Toast.makeText(context,data[1], Toast.LENGTH_LONG).show();
                        }
                        else if(data[0].equals("ReceivedVictim") && paramsCount==1) {
                            String bogus = "You have been sending fake help requests. Thus, your account has been blocked.";
                            System.out.println(">>" + text);
                            setText(bogus);
                            Intent ine = new Intent(context, ReceiverEndActivity.class);
                            ine.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(ine);
                            Toast.makeText(context, bogus, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public enum Code{
        RequestHelp,            // provided by USER ;  3  -  userid, location, extracontent
        RequestEmergency,       // provided by USER ;  3  -  userid, location, extracontent
        CancelHelp,
        HelpResponse            // provided by ORGZ ;  2  -  userId, extracontent
    }



}
