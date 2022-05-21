package majorProject.com.evote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListOfRepresentatives extends AppCompatActivity {

    private static final String TAG = "ListOfRepresentatives";

    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(200000L);
    private final static BigInteger candidateNumber = BigInteger.valueOf(0);
    private final static String CONTRACT_ADDRESS = "0x7074366943B2AedA5486F89E8F34Bd6188AED415";
    private final static String ADMIN_PRIVATE_KEY = "33fb8bb0abbab9a1c04d18d6dddbacbbacd2a884050c7cd7941fe2fe7187d38d";
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mVotes = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    private Web3j web3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");

        try {
            initImageBitmaps();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initImageBitmaps() throws ExecutionException, InterruptedException {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/1994df6c8bf74f4786c768bb5c3922d0"));
        Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);
        Election election2 = Election.load(CONTRACT_ADDRESS, web3, credentials, GAS_LIMIT, GAS_PRICE);
        BigInteger cannum = election2.getNumCandidate().sendAsync().get();
        int totalNumberOfCandidates = Integer.parseInt(String.valueOf(cannum));
        toastAsync(totalNumberOfCandidates+"");

        int i=0;
        for(i=0; i<totalNumberOfCandidates; i++) {
            try {

                String s = String.valueOf(i);

                Tuple3<String, String, BigInteger> totalCandidates = election2.candidates(BigInteger.valueOf(Long.parseLong(s))).sendAsync().get();
                String theCompleteOutput = String.valueOf(totalCandidates);

                String candidatename = theCompleteOutput.substring(theCompleteOutput.indexOf("value1") + 7, theCompleteOutput.indexOf("value2") - 2);
                String candidatelink = theCompleteOutput.substring(theCompleteOutput.indexOf("value2") + 7, theCompleteOutput.indexOf("value3") - 2);
                String numberofvotes = theCompleteOutput.substring(theCompleteOutput.indexOf("value3") + 7, theCompleteOutput.indexOf("}"));

                mImageUrls.add(candidatelink);
                mNames.add(candidatename);

            } catch (Exception e) {
                Log.d("error", e.getMessage());
                toastAsync(e.getMessage());
            }
        }

//        mImageUrls.add("https://risingnepaldaily.com/banner_image/5ffeaf9fc6412_5ffe98550a4c8_sajha-party.jpg");
//        mNames.add("Bibeksheel Sajha Party(विवेकशील साझा पार्टी)");
//
//
//        mImageUrls.add("https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Flag_of_CPN_%28UML%29.svg/300px-Flag_of_CPN_%28UML%29.svg.png");
//        mNames.add("Nepal Communist Party (NCP)नेपाल कम्युनिष्ट पार्टी (नेकपा)");
//
//
//        mImageUrls.add("https://i.redd.it/kfevr1oicyh61.jpg");
//        mNames.add("Nepali Congress(नेपाली काँग्रेस)");
//
//
//        mImageUrls.add("https://i.redd.it/xxrcsc19cyh61.jpg");
//        mNames.add("Nepal Majdoor Kisan Party(नेपाल मजदुर किसान पार्टी)");
//
//
//        mImageUrls.add("https://i.redd.it/egj40drqcyh61.png");
//        mNames.add("People's Socialist Party(जनता समाजवादी पार्टी, नेपाल)");
//
//
//        mImageUrls.add("https://i.redd.it/hfngb8e0dyh61.png");
//        mNames.add("Rastriya Janamorcha(राष्ट्रिय जनमोर्चा)");
//
//
//        mImageUrls.add("https://i.redd.it/wt7vp1j5dyh61.jpg");
//        mNames.add("Rastriya Janamukti Party(राष्ट्रिय जनमुक्ति पार्टी)");
//
//
//        mImageUrls.add("https://i.redd.it/o6sqn13wdyh61.png");
//        mNames.add("Rastriya Prajatantra Party(राष्ट्रिय प्रजातन्त्र पार्टी)");


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

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}






















