package majorProject.com.evote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ListOfRepresentatives extends AppCompatActivity {

    private static final String TAG = "ListOfRepresentatives";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        initImageBitmaps();
    }

    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://risingnepaldaily.com/banner_image/5ffeaf9fc6412_5ffe98550a4c8_sajha-party.jpg");
        mNames.add("Bibeksheel Sajha Party(विवेकशील साझा पार्टी)");

        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Flag_of_CPN_%28UML%29.svg/300px-Flag_of_CPN_%28UML%29.svg.png");
        mNames.add("Nepal Communist Party (NCP)नेपाल कम्युनिष्ट पार्टी (नेकपा)");

        mImageUrls.add("https://i.redd.it/kfevr1oicyh61.jpg");
        mNames.add("Nepali Congress(नेपाली काँग्रेस)");

        mImageUrls.add("https://i.redd.it/xxrcsc19cyh61.jpg");
        mNames.add("Nepal Majdoor Kisan Party(नेपाल मजदुर किसान पार्टी)");

        mImageUrls.add("https://i.redd.it/egj40drqcyh61.png");
        mNames.add("People's Socialist Party(जनता समाजवादी पार्टी, नेपाल)");

        mImageUrls.add("https://i.redd.it/hfngb8e0dyh61.png");
        mNames.add("Rastriya Janamorcha(राष्ट्रिय जनमोर्चा)");

        mImageUrls.add("https://i.redd.it/wt7vp1j5dyh61.jpg");
        mNames.add("Rastriya Janamukti Party(राष्ट्रिय जनमुक्ति पार्टी)");

        mImageUrls.add("https://i.redd.it/o6sqn13wdyh61.png");
        mNames.add("Rastriya Prajatantra Party(राष्ट्रिय प्रजातन्त्र पार्टी)");

        initRecyclerView();
    }

    private void initRecyclerView(){

        String data = getIntent().getStringExtra("string");

        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames , mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}






















