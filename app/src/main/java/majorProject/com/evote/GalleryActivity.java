package majorProject.com.evote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by User on 1/2/2018.
 */

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(2000000000L);
    private final static BigInteger candidateNumber = BigInteger.valueOf(0);
    private final static String CONTRACT_ADDRESS = "0x7074366943B2AedA5486F89E8F34Bd6188AED415";
    private final static String ADMIN_PRIVATE_KEY = "33fb8bb0abbab9a1c04d18d6dddbacbbacd2a884050c7cd7941fe2fe7187d38d";

    private Button button_vote;
    private Web3j web3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
        TextView name = findViewById(R.id.totalVotes);
        String setThis = "Number of votes   ";
        name.setText(setThis);
        showVotesAndStatus();

        button_vote = (Button) findViewById(R.id.buttonvote);
        button_vote.setOnClickListener(view -> {
          //  Toast.makeText(GalleryActivity.this, "Connecting . . . ", Toast.LENGTH_SHORT).show();
            final String imageName = getIntent().getStringExtra("image_name");
            String canNum = getIntent().getStringExtra("candidatenumber");


            //startActivity(new Intent(getApplicationContext(), ScannerActivity.class)).putExtra("candidatenumber",position + "");;

            Intent i = new Intent(GalleryActivity.this, ScannerActivity.class);
            i.putExtra("candidatenumber", canNum);
            startActivity(i);

        });


    }

    private void showVotesAndStatus(){

        // FIXME: Add your own API key here
        web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/1994df6c8bf74f4786c768bb5c3922d0"));
        try {
         //   Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);
            Election election2 = Election.load(CONTRACT_ADDRESS, web3, credentials, GAS_LIMIT, GAS_PRICE);
            String canNum = getIntent().getStringExtra("candidatenumber");

            Tuple3<String,String, BigInteger> totalCandidates = election2.candidates(BigInteger.valueOf(Long.parseLong(canNum))).sendAsync().get();

            String theCompleteOutput = String.valueOf(totalCandidates);

            String candidatename = theCompleteOutput.substring(theCompleteOutput.indexOf("value1")+7, theCompleteOutput.indexOf("value2")-2);
            String candidatelink = theCompleteOutput.substring(theCompleteOutput.indexOf("value2")+7, theCompleteOutput.indexOf("value3")-2);
            String numberofvotes = theCompleteOutput.substring(theCompleteOutput.indexOf("value3")+7, theCompleteOutput.indexOf("}"));
            setImage(candidatelink, candidatename);



            TextView name = findViewById(R.id.totalVotes);
            String setThis = "Number of votes   " +  numberofvotes;
            name.setText(setThis);

        } catch (Exception e) {
            Log.d("error",e.getMessage());
            toastAsync(e.getMessage());
        }

    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String CandidateNumber = getIntent().getStringExtra("candidatenumber");
            //setImage(imageUrl, imageName);
        }


    }
    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }


    private void setImage(String imageUrl, String imageName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }




}


















