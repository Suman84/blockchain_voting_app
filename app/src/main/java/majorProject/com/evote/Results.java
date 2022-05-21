package majorProject.com.evote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class Results extends AppCompatActivity {


    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(2900000000L);
    private final static String CONTRACT_ADDRESS = "0x7074366943B2AedA5486F89E8F34Bd6188AED415";

    private Web3j web3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String ADMIN_PRIVATE_KEY = getIntent().getStringExtra("PrivateKey");
        String CandidateNumber = getIntent().getStringExtra("candidateNumber");
        String PIN2VOTE = getIntent().getStringExtra("pin");

        TextView tv_status_voting = findViewById(R.id.textView);
        TextView tv_your_status = findViewById(R.id.textView2);

        Button button_Proceed = (Button) findViewById(R.id.buttonproceed);

        TextView tv_wrong_vote_attempts = findViewById(R.id.textView3);
//        tv_authorization.setText("Authorization:" );
//        tv_voted.setText("VOTED? :");

     //   tv_wrong_vote_attempts.setText("1");

        web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/1994df6c8bf74f4786c768bb5c3922d0"));

        try {

            Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);
            String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
            String addr = credentials.getAddress();
            BigInteger candidateNumber = BigInteger.valueOf(Long.parseLong(CandidateNumber));
            Election election2 = Election.load(CONTRACT_ADDRESS, web3, credentials, GAS_PRICE, GAS_LIMIT);
            //election2.voters(addr);

            String error = election2.showTheError().sendAsync().get();
            int votelater = 0;

           // election2.vote(candidateNumber, Integer.valueOf(PIN2VOTE)).sendAsync().get();
            if(error.length() == 12)
            {
                error = "Authorized and not voted";
                votelater = 1;

            }else{
                String setthis = "STATUS: FAILED" ;
                tv_status_voting.setText(setthis);
            }
            toastAsync(error);
            String setThis = "Your Status:  " +  error;
            tv_your_status.setText(setThis);
            if(votelater == 1){
                //tv_wrong_vote_attempts.setText("0");

                button_Proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String PIN2Vote = tv_wrong_vote_attempts.getText().toString().trim();
                        if(PIN2Vote.equals("1")) {

                            try {
                                election2.vote(candidateNumber, Integer.valueOf(PIN2VOTE)).sendAsync().get();


                                String error = election2.showTheError().sendAsync().get();

                                if(error.equals("already voted")){
                                    tv_status_voting.setText("VOTING:SUCCESSFUL");
                                    tv_your_status.setText("Voted!");
                                }else {
                                    tv_status_voting.setText("VOTING:UNSUCCESSFUL");
                                    tv_your_status.setText("Wrong PIN!");
                                }
                                tv_wrong_vote_attempts.setText("0");

                            } catch (InterruptedException e) {
                                tv_status_voting.setText("VOTING:UNSUCCESSFUL");
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                tv_status_voting.setText("VOTING:UNSUCCESSFUL");
                                e.printStackTrace();
                            }
                        }

                        }
                });

            }

        } catch (Exception e) {
            Log.d("error", e.getMessage());
            toastAsync(e.getMessage());
        }


    }


    private void getIncomingIntent(){

            String Status = getIntent().getStringExtra("Status");
            String Authorization = getIntent().getStringExtra("Authorization");
            String Voted = getIntent().getStringExtra("Voted");
            String WrongVoteAttempts = getIntent().getStringExtra("WrongVoteAttempts");

    }

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }
}
