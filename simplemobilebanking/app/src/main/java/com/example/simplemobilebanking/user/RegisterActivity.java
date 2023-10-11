package com.example.simplemobilebanking.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simplemobilebanking.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseReference database;
    private EditText registerusername, registeremail, registernohp,registerpw;
    private Button btnregister, backhp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance().getReference();

        registeremail = findViewById(R.id.registeremail);
        registernohp = findViewById(R.id.registerphone);
        registerpw = findViewById(R.id.registerpassword);
        registerusername = findViewById(R.id.registerusername);
        btnregister = findViewById(R.id.btnregister);
        backhp = findViewById(R.id.bck);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = registerusername.getText().toString();
                String email = registeremail.getText().toString();
                String nohp = registernohp.getText().toString();
                String password = registerpw.getText().toString();

                if (username.isEmpty() || email.isEmpty() || nohp.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else {
                    DatabaseReference userRef = database.child("users").child(username);
                    userRef.child("username").setValue(username);
                    userRef.child("email").setValue(email);
                    userRef.child("nohp").setValue(nohp);
                    userRef.child("password").setValue(password);
                    userRef.child("saldo").setValue(0);
                    Toast.makeText(getApplicationContext(), "Register telah berhasil", Toast.LENGTH_SHORT).show();
                    Intent register = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(register);
                }
            }
        });
        backhp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}