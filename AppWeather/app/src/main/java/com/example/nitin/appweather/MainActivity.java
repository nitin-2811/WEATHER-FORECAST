package com.example.nitin.appweather;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView resultTextView;

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {

            String result="";
            URL url;
            HttpURLConnection urlConnection=null;
            try{

                url=new URL(urls[0]);
                urlConnection=(HttpURLConnection) url.openConnection();
                InputStream in =urlConnection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(in);
                int data=inputStreamReader.read();
                Log.i("THHHHHHHHHHHHHHHHHHHHHHHHISSSSSSSSSS ","SDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD1");
                while(data!=-1)
                {
                    result+=(char)data;
                    data=inputStreamReader.read();
                }

                return result;
            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "Sorry!Can't find the", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.i("THHHHHHHHHHHHHHHHHHHHHHHHISSSSSSSSSS ","SDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD2");

                JSONObject jsonObject = new JSONObject(s);
                String weatherInfo=jsonObject.getString("weather");
                Log.i("Weather content",weatherInfo);
                JSONArray arr=new JSONArray(weatherInfo);
                String s1="";
                for(int i=0;i<arr.length();i++){
                    JSONObject jsonPart=arr.getJSONObject(i);

                    s1+=jsonPart.getString("main");
                    s1+="\r\n"+jsonPart.getString("description");
                }

                resultTextView.setText(s1);

            }
            catch (Exception e){
                Toast.makeText(MainActivity.this, "Sorry!Can't find the", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public  void getWeather(View view){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        DownloadTask task=new DownloadTask();
        String place= editText.getText().toString();
        Log.i("THHHHHHHHHHHHHHHHHHHHHHHHISSSSSSSSSS ","SDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        task.execute("http://samples.openweathermap.org/data/2.5/weather?q="+place+"&appid=b6907d289e10d714a6e88b30761fae22");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText=findViewById(R.id.editText);
        resultTextView=findViewById(R.id.resultTextView);
    }
}
