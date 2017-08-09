package com.app.helpinghand;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class UserActivity extends AppCompatActivity {

    private EditText message;
    public static Button sendButton;
    public static Button emergencyButton;
    public static boolean disabled = false;
    AppLocationService appLocationService;
    SMSReceiver smsReceiver;
    String filename = "record.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(disabled){
            sendButton.setEnabled(false);
            emergencyButton.setEnabled(false);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user);

        System.out.println("USER ACTIVITY");

        message = (EditText) findViewById(R.id.message);
        sendButton = (Button) findViewById(R.id.sendbutton);
        emergencyButton = (Button) findViewById(R.id.emergency_button);
        appLocationService = new AppLocationService(UserActivity.this);
    }

    public void emergencyButtonClicked(View v){
        sendSOS(Code.RequestEmergency);
        //Intent intent = new Intent(this, ReceiverEndActivity.class);
        //startActivity(intent);
    }

    public void sendButtonClicked(View v){
        sendSOS(Code.RequestHelp);
        //Intent intent = new Intent(this, ReceiverEndActivity.class);
        //startActivity(intent);
    }

    public void exploreButtonClicked(View v){
        Intent intent = new Intent(this, HospitalList.class);
        startActivity(intent);

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
        String text = message.getText().toString();
        String userID ="3"; //getUserId();
        String cypher =null;
        switch(x){
            case RequestEmergency: cypher =  "["+x+"]["+userID+"][New Delhi]";
                emergencyButton.setEnabled(false);
                sendButton.setEnabled(false);
                disabled =true;
                break;
            case RequestHelp:cypher = "["+x+"]["+userID+"][New Delhi]["+text+"]";
                emergencyButton.setEnabled(false);
                sendButton.setEnabled(false);
                disabled = true;
                break;
            case CancelHelp:cypher = "["+x+"]"+"["+userID+"]";
                break;
        }
        cypher = cypher.replace("\n","");
        int length =  (cypher).length();
        System.out.println(cypher+"L:"+length);
        Intent intent = new Intent(getApplicationContext(),UserActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        Intent intent2 =  new Intent(getApplicationContext(),UserActivity.class);
        PendingIntent pi2 = PendingIntent.getActivity(getApplicationContext(),0,intent2,0);
        SmsManager sms2 = SmsManager.getDefault();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(admin_no,null,cypher,pi,null);
        sms2.sendTextMessage(getDearOne(),null,"Hi there! I am in trouble, in New Delhi. Please help me!!", pi2, null);
        System.out.println("GOT"+getDearOne());
        //if(getDearOne()!=null)
        //   sms.sendTextMessage(getDearOne(), null, "Hi there! I am in trouble, in New Delhi. Please help me!!", pi, null);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Toast.makeText(getApplicationContext(), "Message Sent successfully!",Toast.LENGTH_LONG).show();
        HomeActivity.getResponse = new SMSReceiver();
    }

    public enum Code{
        RequestHelp,            // provided by USER ;  3  -  userid, location, extracontent
        RequestEmergency,       // provided by USER ;  3  -  userid, location, extracontent
        CancelHelp,
        HelpResponse            // provided by ORGZ ;  2  -  userId, extracontent
    }


    public void getLocationOfDevice(View v){
        Intent intent =  new Intent(getApplicationContext(), MyActivity.class);
        startActivity(intent);
    }

    public String getUserId(){
        try{
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader bf = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line=bf.readLine())!=null){
                sb.append(line).append("\n");
                System.out.println(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
            return "";
        }catch(IOException e){
            e.printStackTrace();
            return "";
        }
    }

    public String getDearOne(){
        try{
            FileInputStream fis = getApplicationContext().openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bf = new BufferedReader(isr);
            String dear_one = new String();
            String line;
            while((line=bf.readLine())!=null){
                dear_one = line;
            }
            return dear_one;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getLocation(){

        Location gpsLocation = appLocationService.getLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            double latitude = gpsLocation.getLatitude();
            double longitude = gpsLocation.getLongitude();
            String result = "Lat:" + latitude + "-Longitude:" + longitude;
            System.out.println(result);
            return result;
        } else {
            showSettingsAlert();
        }
        return "";
    }


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                UserActivity.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        UserActivity.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

}
