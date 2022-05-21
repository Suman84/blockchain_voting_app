package majorProject.com.evote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginScreen extends AppCompatActivity {

    private Button button;
    EditText txtEmail;
    EditText txtPassword;
    Button btn_login;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        txtEmail = (EditText) findViewById(R.id.EmailLog);
        txtPassword = (EditText) findViewById(R.id.PasswordLog);
        button = (Button) findViewById(R.id.Login);

        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(LoginScreen.this, "Please Enter Email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginScreen.this, "Please Enter Valid Password !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(LoginScreen.this, "Password too Short ! ", Toast.LENGTH_SHORT).show();

                }

                if (email.equals("admin@gmail.com") && password.equals("evoting")) {
                    startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                }

                if (email.equals("admin@gmail.com") && !password.equals("evoting")) {
                    Toast.makeText(LoginScreen.this, "Wrong password! ", Toast.LENGTH_SHORT).show();
                    return;
                }



                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String email = txtEmail.getText().toString().trim();
                                    startActivity(new Intent(getApplicationContext(), ListOfRepresentatives.class).putExtra("string",email));
                                } else {
                                    Toast.makeText(LoginScreen.this, "Login Failed or USER NOT REGISTERED !", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
