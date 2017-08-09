package com.app.helpinghand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HospitalList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        Hospital_Model[] array_list = new Hospital_Model[6];
        Hospital_Model model;

        model = new Hospital_Model();
        model.setName("A.I.I.M.S");
        model.setAssociated("YES");
        model.setLocation("NEW DELHI");

        array_list[0] = model;

        model = new Hospital_Model();
        model.setName("Government Hospital");
        model.setAssociated("YES");
        model.setLocation("NEW DELHI");

        array_list[1] = model;

        model = new Hospital_Model();
        model.setName("Artemis Hospital");
        model.setAssociated("NO");
        model.setLocation("NEW DELHI");

        array_list[2] = model;

        model = new Hospital_Model();
        model.setName("Medanta Medicity");
        model.setAssociated("YES");
        model.setLocation("GURUGRAM");

        array_list[3] = model;

        model = new Hospital_Model();
        model.setName("Max Hospital");
        model.setAssociated("NO");
        model.setLocation("GURUGRAM");

        array_list[4] = model;

        model = new Hospital_Model();
        model.setName("FORTIS Hospital");
        model.setAssociated("YES");
        model.setLocation("NOIDA");

        array_list[5] = model;





        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_hospital_list,R.id.text1,  array_list);

        ListView view = (ListView) findViewById(R.id.list);

        view.setAdapter(adapter);




    }


}
