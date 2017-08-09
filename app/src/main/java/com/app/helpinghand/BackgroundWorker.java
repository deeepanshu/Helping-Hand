package com.app.helpinghand;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Deepanshu on 12/2/2017.
 */

public class BackgroundWorker extends AsyncTask<String, Void, String>{

    Context context;

    AlertDialog alertDialog;



    BackgroundWorker(Context ctx){
        context = ctx;
    }
    private static String filename = "record.txt";

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String name  = params[1];
        String address  = params[5];
        String contact = params[3];
        String content  = params[4];
        String email  = params[2];


        //10.0.2.2:8080
        //192.168.43.105:8084
        String user_url = null;
        try {
            user_url = "http://192.168.43.105:8084/HelpingHand/add_user.jsp?name="+ URLEncoder.encode(name,"UTF-8")+"&address="+URLEncoder.encode(address,"UTF-8")+"&contact="+URLEncoder.encode(contact,"UTF-8")+"&content="+URLEncoder.encode(content,"UTF-8")+"&email="+URLEncoder.encode(email,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(user_url);
        if(type.equals("Register")){
            try {
                URL url = new URL(user_url);
                System.out.println(url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                //httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                                          +URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"
                                          +URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(contact,"UTF-8")+"&"
                                          +URLEncoder.encode("content","UTF-8")+"="+URLEncoder.encode(content,"UTF-8")+"&"
                                          +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                //bw.write(post_data);
                System.out.println(post_data);
                //bw.flush();
                bw.close();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));
                String result=null;
                String line= null;
                while( (line= br.readLine())!=null){
                    result +=line;
                }
                br.close();
                is.close();
                httpURLConnection.disconnect();

                final Pattern pattern = Pattern.compile("<body>(.+?)</body>");
                final Matcher matcher = pattern.matcher(result);
                matcher.find();
                System.out.println(matcher.group(1));
                result = matcher.group(1).toString();
                result = result.trim();
                if(null!=result)
                   return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        //alertDialog.show();
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Status");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
