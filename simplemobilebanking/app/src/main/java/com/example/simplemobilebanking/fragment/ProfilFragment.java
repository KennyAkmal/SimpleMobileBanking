package com.example.simplemobilebanking.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.simplemobilebanking.EditActivity;
import com.example.simplemobilebanking.NavActivity;
import com.example.simplemobilebanking.R;
import com.example.simplemobilebanking.user.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment {
    private DatabaseReference database;
    private TextView tvUname,tvNoHp;
    private Button editprofilbtn, logoutbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);
        database = FirebaseDatabase.getInstance().getReference();
        tvUname = view.findViewById(R.id.tvUname);
        tvNoHp = view.findViewById(R.id.tvnoHp);
        editprofilbtn = view.findViewById(R.id.editprofil);
        logoutbtn = view.findViewById(R.id.buttonlogout);

        Activity activity = getActivity();
        if (activity instanceof NavActivity){
            Intent intent = activity.getIntent();
            String username = intent.getStringExtra("username");
            database.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String uname = snapshot.child("username").getValue(String.class);
                        String nohp = snapshot.child("nohp").getValue(String.class);
                        tvUname.setText(uname);
                        tvNoHp.setText(nohp);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            editprofilbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EditActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            });
        }
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}