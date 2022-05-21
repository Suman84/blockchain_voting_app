package majorProject.com.evote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;

import java.math.BigInteger;

public class ScannerActivity<context> extends AppCompatActivity {
    private Button buttonScan, buttonPrivateKey;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG="ScanYourQR";
    private static final String TAG = "GalleryActivity";

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
//USING QR CODE
        buttonScan = (Button) findViewById(R.id.scanButton);
        buttonPrivateKey = (Button) findViewById(R.id.submitPK);
        buttonScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText PIN = (EditText) findViewById(R.id.pin);
                String PIN2Vote = PIN.getText().toString().trim();
                if(PIN2Vote.length()>3) {
                    Intent i = new Intent(ScannerActivity.this, QrCodeActivity.class);
                    startActivityForResult(i, REQUEST_CODE_QR_SCAN);
                }else{
                    toastAsync("PIN TOO SHORT!");
                }

            }

        });
// USING PRIVATE KEY
        buttonPrivateKey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                EditText privateKey = (EditText) findViewById(R.id.privateKey);
                String ADMIN_PRIVATE_KEY = privateKey.getText().toString().trim();

                EditText PIN = (EditText) findViewById(R.id.pin);
                String PIN2Vote = PIN.getText().toString().trim();

                String canNum = getIntent().getStringExtra("candidatenumber");
                BigInteger candidateNumber = BigInteger.valueOf(Long.parseLong(canNum));


                if(ADMIN_PRIVATE_KEY.length() == 0 ){
                    toastAsync("ENTER PRIVATE KEY!");
                }else if(ADMIN_PRIVATE_KEY.length() < 64){
                    int length = ADMIN_PRIVATE_KEY.length();
                    toastAsync(""+ length);
                }else if(ADMIN_PRIVATE_KEY.length() == 64){

                    startActivity(new Intent(getApplicationContext(), Results.class).putExtra("PrivateKey",ADMIN_PRIVATE_KEY )
                            .putExtra("candidateNumber",canNum )
                            .putExtra("pin",PIN2Vote ));
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

                EditText PIN = (EditText) findViewById(R.id.pin);
                String PIN2Vote = PIN.getText().toString().trim();

                startActivity(new Intent(getApplicationContext(), Results.class).putExtra("PrivateKey",ADMIN_PRIVATE_KEY )
                        .putExtra("candidateNumber",canNum )
                        .putExtra("pin",PIN2Vote ));

            }
        }

    public void toastAsync(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });
    }


    }