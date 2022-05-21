package majorProject.com.evote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;

import com.blikoon.qrcodescanner.QrCodeActivity;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class ScannerActivity<context> extends AppCompatActivity {
    private Button buttonScan, buttonPrivateKey;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG="ScanYourQR";

    private static final String TAG = "GalleryActivity";
    private final static BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private final static BigInteger GAS_PRICE = BigInteger.valueOf(200000L);
    //private final static BigInteger candidateNumber = BigInteger.valueOf(0);
    private final static String CONTRACT_ADDRESS = "0x65333FfBe6b0F15983a3a680c52Df90cA78aBFF9";
    //private final static String ADMIN_PRIVATE_KEY = "33fb8bb0abbab9a1c04d18d6dddbacbbacd2a884050c7cd7941fe2fe7187d38d";

    private Web3j web3;

    private static final int MY_CAMERA_REQUEST_CODE = 100;

        // Checking if permission is not granted


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        String permission ="YES";
        if (ContextCompat.checkSelfPermission(ScannerActivity.this,permission) == PackageManager.PERMISSION_DENIED) {
            int requestCode= 1;
            ActivityCompat
                    .requestPermissions(
                            ScannerActivity.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(ScannerActivity.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }

        buttonScan = (Button) findViewById(R.id.scanButton);
        buttonPrivateKey = (Button) findViewById(R.id.submitPK);
        buttonScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(ScannerActivity.this, QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);

            }

        });

        buttonPrivateKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                EditText privateKey = (EditText) findViewById(R.id.privateKey);;
                String ADMIN_PRIVATE_KEY = privateKey.getText().toString().trim();
                String canNum = getIntent().getStringExtra("candidatenumber");
                BigInteger candidateNumber = BigInteger.valueOf(Long.parseLong(canNum));


                if(ADMIN_PRIVATE_KEY.length() == 0 ){
                    toastAsync("ENTER PRIVATE KEY!");
                }else if(ADMIN_PRIVATE_KEY.length() < 64){
                    int length = ADMIN_PRIVATE_KEY.length();
                    toastAsync(""+ length);
                }else if(ADMIN_PRIVATE_KEY.length() == 64){
                    // BLOCKCHAIN IMPLEMENTATION
                    toastAsync("Connecting to Ethereum network...");

                    // FIXME: Add your own API key here
                    web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/1994df6c8bf74f4786c768bb5c3922d0"));
                    try {
                        Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
                        Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);

                        Election election2 = Election.load(CONTRACT_ADDRESS, web3, credentials, GAS_LIMIT, GAS_PRICE);
                        election2.vote(candidateNumber).sendAsync().get();

                        if (!clientVersion.hasError()) {
                            toastAsync("Voted!");
                        } else {
                            toastAsync(clientVersion.getError().getMessage());
                        }
                    } catch (Exception e) {
                        Log.d("error", e.getMessage());
                        toastAsync(e.getMessage());
                    }

                }else{
                    toastAsync("PRIVATE KEY TOO LONG!");
                }
            }

        });
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if(resultCode != Activity.RESULT_OK)
            {
                Log.d(LOGTAG,"COULD NOT GET A GOOD RESULT.");
                if(data==null)
                    return;
                //Getting the passed result
                String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
                if( result!=null)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(ScannerActivity.this).create();
                    alertDialog.setTitle("Scan Error");
                    alertDialog.setMessage("QR Code could not be scanned");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                return;

            }
            if(requestCode == REQUEST_CODE_QR_SCAN)
            {
                if(data==null)
                    return;
                //Getting the passed result
                String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
                Log.d(LOGTAG,"Have scan result in your app activity :"+ result);
                String canNum = getIntent().getStringExtra("candidatenumber");
                String ADMIN_PRIVATE_KEY = result;
                        BigInteger candidateNumber = BigInteger.valueOf(Long.parseLong(canNum));

                        //Toast.makeText(ScannerActivity.this, "yoo        "+ canNum, Toast.LENGTH_SHORT).show();

                        // BLOCKCHAIN IMPLEMENTATION

                        toastAsync("Connecting to Ethereum network...");
                        // FIXME: Add your own API key here
                        web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/1994df6c8bf74f4786c768bb5c3922d0"));
                        try {
                            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
                        Credentials credentials = Credentials.create(ADMIN_PRIVATE_KEY);

                        Election election2 = Election.load(CONTRACT_ADDRESS, web3, credentials, GAS_LIMIT, GAS_PRICE);
                        election2.vote(candidateNumber).sendAsync().get();

                        if(!clientVersion.hasError()){
                            toastAsync("Voted!");
                        }
                        else {
                            toastAsync(clientVersion.getError().getMessage());
                        }
                    } catch (Exception e) {
                        Log.d("error",e.getMessage());
                        toastAsync(e.getMessage());
                    }





//                AlertDialog alertDialog = new AlertDialog.Builder(ScannerActivity.this).create();
//                alertDialog.setTitle("Your Key");
//                alertDialog.setMessage(result);
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();

            }
        }

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }


    }