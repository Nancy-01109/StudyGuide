package com.example.studyguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedIn extends AppCompatActivity {

    TextView name,mail,id;
    private Button btnSignOut;
    private Button btnMapps;
    private  Button Music;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        btnSignOut=(Button) findViewById(R.id.sign_out);
        name=findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        id=findViewById(R.id.id1);
        btnMapps=(Button)findViewById(R.id.map_butt);
        




        GoogleSignInAccount signInAccount= GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null)
        {
            name.setText(signInAccount.getDisplayName());
            mail.setText(signInAccount.getEmail());
            id.setText(signInAccount.getId());
        }

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        btnMapps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Mapps.class);
                startActivity(intent);
            }
        });






    }
}