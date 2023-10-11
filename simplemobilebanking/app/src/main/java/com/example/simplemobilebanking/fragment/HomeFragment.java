package com.example.simplemobilebanking.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.simplemobilebanking.NavActivity;
import com.example.simplemobilebanking.R;
import com.example.simplemobilebanking.TopUpActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    private DatabaseReference database;
    private TextView tvusername, tvsaldo;
    private Button isiSaldo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        database = FirebaseDatabase.getInstance().getReference();
        isiSaldo = view.findViewById(R.id.btnisisaldo);
        tvusername = view.findViewById(R.id.tvUsername);
        tvsaldo = view.findViewById(R.id.tvSaldoSaya);

        Activity activity = getActivity();
        if (activity instanceof NavActivity) {
            Intent intent = activity.getIntent();
            String username = intent.getStringExtra("username");

            database.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String username = snapshot.child("username").getValue(String.class);
                        Integer saldo = snapshot.child("saldo").getValue(Integer.class);
                        tvusername.setText(username);
                        tvsaldo.setText(String.valueOf(saldo));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
            isiSaldo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), TopUpActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
}