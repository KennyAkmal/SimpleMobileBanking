package com.example.simplemobilebanking.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simplemobilebanking.NavActivity;
import com.example.simplemobilebanking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText loginusername, loginpassword;
    private Button loginbtn, bckrgs;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database = FirebaseDatabase.getInstance().getReference();
        loginusername = findViewById(R.id.emailregister);
        loginpassword = findViewById(R.id.password);
        loginbtn = findViewById(R.id.btnlogin);
        bckrgs = findViewById(R.id.btnrgs);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String username = loginusername.getText().toString();
                String password = loginpassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username atau password anda salah", Toast.LENGTH_SHORT).show();
                }else {
                    database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.child(username).exists()){
                                if (snapshot.child(username).child("password").getValue(String.class) != null &&
                                        snapshot.child(username).child("password").getValue(String.class).equals(password)){
                                    Intent intent = new Intent(LoginActivity.this, NavActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password anda salah", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Data anda belum terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        bckrgs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}