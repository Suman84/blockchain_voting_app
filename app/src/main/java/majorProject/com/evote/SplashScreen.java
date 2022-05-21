package majorProject.com.evote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Scanner;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), ListOfRepresentatives.class));

            }
        },2000);
    }
}
