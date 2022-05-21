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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText txtUserName, txtEmail ,txtPassword, txtConfirmPassword, txtRollnum;
    Button btn_register;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txtEmail= (EditText) findViewById(R.id.Email);
        txtPassword= (EditText) findViewById(R.id.Password);
        txtConfirmPassword =(EditText) findViewById(R.id.ConPassword);
        btn_register =(Button) findViewById(R.id.Register);

        firebaseAuth =FirebaseAuth.getInstance();
        FirebaseDatabase Rootnode;

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email =txtEmail.getText().toString().trim();
                String password =txtPassword.getText().toString().trim();
                String confirmPassword =txtConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Registration.this,"Please Enter Email !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Registration.this,"Please Enter Valid Password !", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Toast.makeText(Registration.this,"Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.length()<6){
                    Toast.makeText(Registration.this,"Password too Short ! ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password.equals(confirmPassword)){
                    Toast.makeText(Registration.this,"Registering....",Toast.LENGTH_SHORT).show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {


                                        String email =txtEmail.getText().toString().trim();
                                        int beforeAt = email.indexOf("@");
                                        String subEmail = email.substring(0,beforeAt);

                                        DatabaseReference reference;
                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(subEmail);
                                        reference.setValue("notvoted");


                                        Toast.makeText(Registration.this,"Authentication successful",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                    } else {
                                        Toast.makeText(Registration.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(Registration.this,"Password do not match!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
