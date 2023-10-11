package com.example.simplemobilebanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simplemobilebanking.fragment.ProfilFragment;
import com.example.simplemobilebanking.user.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private DatabaseReference database;
    private EditText etEmail, etNohp, etPassword;
    private Button btnUpdate, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etEmail = findViewById(R.id.etEmail);
        etNohp = findViewById(R.id.etNohp);
        etPassword = findViewById(R.id.etPassword);
        btnUpdate = findViewById(R.id.savebtn);
        btnBack =findViewById(R.id.bcktohome);
        database = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String nohp = etNohp.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty() || nohp.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference userRef = database.child("users").child(username);
                    Map<String, Object> userUpdates = new HashMap<>();
                    userUpdates.put("email", email);
                    userUpdates.put("nohp", nohp);
                    userUpdates.put("password", password);

                    userRef.updateChildren(userUpdates)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Update telah berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EditActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Update gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}