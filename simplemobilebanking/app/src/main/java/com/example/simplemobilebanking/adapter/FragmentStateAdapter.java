package com.example.simplemobilebanking.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentStateAdapter {
    @NonNull
    Fragment createFragment(int position);

    int getItemCount();
}
