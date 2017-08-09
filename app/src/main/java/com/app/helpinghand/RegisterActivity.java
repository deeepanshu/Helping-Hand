package com.app.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText contact_no;
    private EditText address;
    private EditText content;
    private EditText dear_contact;
    private Button registerButton;
    private static String filename = "record.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        contact_no = (EditText) findViewById(R.id.contact);
        content = (EditText) findViewById(R.id.content);
        address = (EditText) findViewById(R.id.address);
        dear_contact = (EditText) findViewById(R.id.dear_contact);
        registerButton = (Button) findViewById(R.id.registerbutton);
    }



    public void registerButtonClicked(View v){
        System.out.println(name.getText().toString()+"\n" +email.getText().toString()+"\n"+contact_no.getText().toString()+"\n");
        String type = "Register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        try {
            String data = backgroundWorker.execute(type,name.getText().toString(), email.getText().toString(), contact_no.getText().toString(), content.getText().toString(), address.getText().toString()).get();
            if(data == null){
                throw new NullPointerException();
            }
            System.out.println("DATA"+data);
            addRecord(data, dear_contact.getText().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }catch(NullPointerException e){
            Intent intent = new Intent(this, HomeActivity.class);
            finish();
            startActivity(intent);
        }

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public  void addRecord(String data, String near_one) {

        if(data==null){
            return;
        }
        FileOutputStream fo = null;
        try {
            String file_string = data+"\n"+near_one;
            fo = openFileOutput(filename, MODE_WORLD_READABLE );

            fo.write(file_string.getBytes());
            fo.close();
            Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
