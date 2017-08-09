package com.app.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    final static String filename = "record.txt";
    static SMSReceiver getResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent ;


        if(!hasFile()){
            intent = new Intent(this, RegisterActivity.class);
       }
        else{
           intent = new Intent(this, UserActivity.class);
        }
      // intent = new Intent(this, UserActivity.class);
//        getResponse = new SMSReceiver();
//        //intent = new Intent(this, UserActivity.class);

        startActivity(intent);
        finish();
    }

    public  boolean hasFile(){
        File file=  getBaseContext().getFileStreamPath(filename);
        return file.exists();
    }

}
