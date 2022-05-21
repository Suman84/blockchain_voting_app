package majorProject.com.evote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by User on 1/2/2018.
 */

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";

    private Button button_vote;
    FirebaseDatabase Rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
        button_vote = (Button) findViewById(R.id.buttonvote);

        button_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = getIntent().getStringExtra("email");
                String imageName = getIntent().getStringExtra("image_name");

                Rootnode = FirebaseDatabase.getInstance();
                reference = Rootnode.getReference("representatives").child(imageName);
                reference.setValue("1");

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("representatives");



            }
        });


    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
            String email = getIntent().getStringExtra("string");





            setImage(imageUrl, imageName);
        }


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


















