package com.example.simplemobilebanking;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

import com.example.simplemobilebanking.user.LoginActivity;
import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class TopUpActivity extends AppCompatActivity {
    private DatabaseReference database;
    private EditText etTopup;
    private Button btnNext,backhm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        database = FirebaseDatabase.getInstance().getReference();
        btnNext = findViewById(R.id.btn_next);
        etTopup = findViewById(R.id.etTopup);
        backhm = findViewById(R.id.btn_backhm);

        backhm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String topupAmountStr = etTopup.getText() != null ? etTopup.getText().toString() : "";
                if (!topupAmountStr.isEmpty()) {
                    try {
                        int topupAmount = Integer.parseInt(topupAmountStr);
                        if (topupAmount > 0) {
                            Intent intent = getIntent();
                            String username = intent.getStringExtra("username");
                            DatabaseReference userRef = database.child("users").child(username);

                            userRef.child("saldo").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        int currentSaldo = snapshot.getValue(Integer.class);
                                        int newSaldo = currentSaldo + topupAmount;
                                        userRef.child("saldo").setValue(newSaldo);
                                        Toast.makeText(getApplicationContext(), "Top up berhasil", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(TopUpActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Gagal membaca saldo: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(),"Jumlah top up harus lebih besar dari 0", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(),"Jumlah top up harus berupa angka", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Jumlah top up tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}