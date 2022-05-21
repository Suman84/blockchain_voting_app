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
import android.widget.Toast;

//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;

import com.blikoon.qrcodescanner.QrCodeActivity;

public class Scanner<context> extends AppCompatActivity {
    private Button button;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG="ScanYourQR";

    private static final int MY_CAMERA_REQUEST_CODE = 100;

        // Checking if permission is not granted


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        String permission ="YES";
        if (ContextCompat.checkSelfPermission(Scanner.this,permission) == PackageManager.PERMISSION_DENIED) {
            int requestCode= 1;
            ActivityCompat
                    .requestPermissions(
                            Scanner.this,
                            new String[] { permission },
                            requestCode);
        }
        else {
            Toast
                    .makeText(Scanner.this,
                            "Permission already granted",
                            Toast.LENGTH_SHORT)
                    .show();
        }

        button = (Button) findViewById(R.id.scanButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Scanner.this, QrCodeActivity.class);
                startActivityForResult( i,REQUEST_CODE_QR_SCAN);

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
                    AlertDialog alertDialog = new AlertDialog.Builder(Scanner.this).create();
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
                AlertDialog alertDialog = new AlertDialog.Builder(Scanner.this).create();
                alertDialog.setTitle("Scan result");
                alertDialog.setMessage(result);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            }
        }
    }