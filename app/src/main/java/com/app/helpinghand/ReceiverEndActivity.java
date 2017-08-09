package com.app.helpinghand;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiverEndActivity extends AppCompatActivity {


    private static final String INBOX_URI = "content://sms/inbox";
    private static ReceiverEndActivity activity;

    private TextView response_box;

    public static ReceiverEndActivity instance(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_end);
        response_box = (TextView) findViewById(R.id.response_box);
        response_box.setText(SMSReceiver.text);
        //UserActivity.sendButton.setEnabled(true);
        //UserActivity.emergencyButton.setEnabled(true);
        SMSReceiver.text="";

    }

    @Override
    public void onStart(){
        super.onStart();
        activity = this;
    }

    public void cancelButtonClicked(View v){
        sendSOS(Code.CancelHelp);
        Intent intent = new Intent(this, ReceiverEndActivity.class);
        finish();
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "Cancelling...", Toast.LENGTH_LONG ).show();
    }

    public void sendSOS(Code x){
        String admin_no = "9711289603";
        String userID ="3"; //getUserId();
        String cypher =null;
        switch(x){
            case CancelHelp:cypher = "["+x+"]"+"["+userID+"]";
                break;
        }
        cypher = cypher.replace("\n","");
        int length =  (cypher).length();
        System.out.println(cypher+"L:"+length);
        Intent intent = new Intent(getApplicationContext(),UserActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(admin_no,null,cypher,pi,null);
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",Toast.LENGTH_LONG).show();
        HomeActivity.getResponse = new SMSReceiver();
    }


    public enum Code{
        RequestHelp,            // provided by USER ;  3  -  userid, location, extracontent
        RequestEmergency,       // provided by USER ;  3  -  userid, location, extracontent
        CancelHelp,
        HelpResponse            // provided by ORGZ ;  2  -  userId, extracontent
    }


}
