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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                Toast.makeText(GalleryActivity.this,"Connecting . . . ", Toast.LENGTH_SHORT).show();
                final String imageName = getIntent().getStringExtra("image_name");
                Rootnode = FirebaseDatabase.getInstance();
                reference = Rootnode.getReference("representatives").child(imageName);
                //reference.setValue("0");

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userEmail = user.getEmail();
                        int beforeAt = userEmail.indexOf("@");
                        String subEmail = userEmail.substring(0,beforeAt);
                        String isitvoted = dataSnapshot.child(subEmail).getValue(String.class);

                        if(isitvoted.equals("voted"))
                        {
                            Toast.makeText(GalleryActivity.this,"User already voted! ", Toast.LENGTH_SHORT).show();
                        }
                        else{
                                Toast.makeText(GalleryActivity.this,"voting.... ", Toast.LENGTH_SHORT).show();
                                reference = Rootnode.getReference("Users").child(subEmail);
                                reference.setValue("voted");

                            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("representatives");
                            reference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    final String imageName = getIntent().getStringExtra("image_name");
                                    String NoofVotes = dataSnapshot.child(imageName).getValue(String.class);
                                    int noofvotes = Integer.parseInt(NoofVotes);
                                    noofvotes++;
                                    NoofVotes = String.valueOf(noofvotes);
                                    DatabaseReference reference3 = Rootnode.getReference("representatives").child(imageName);
                                    reference3.setValue(NoofVotes);
                                    Toast.makeText(GalleryActivity.this,"Voting Successful ", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");
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


















