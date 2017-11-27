package com.example.exit.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

    protected static final String ACTIVITY_NAME = "StartActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME,"In onCreate()");

        final Button button = (Button)findViewById(R.id.button);
        final Button chatButton = (Button)findViewById(R.id.chatButton);
        final Button weatherButton = (Button) findViewById(R.id.weatherButton);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent intent = new Intent(StartActivity.this,ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatIntent = new Intent(StartActivity.this,ChatWindow.class);
                startActivity(chatIntent);
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent weatherIntent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(weatherIntent);
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
            }
        });
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent data){
        if (requestCode == 10){
            Log.i(ACTIVITY_NAME,"Returned to StartActivity.onActivityResult");
        }

        if (responseCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            CharSequence text = "ListItemActivity passed: ";
            Toast.makeText(getApplicationContext(), text+messagePassed, Toast.LENGTH_LONG).show(); //this is the ListActivity
        }
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }

}

