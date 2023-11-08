package com.example.chatties.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.chatties.databinding.FragmentSearchBinding;

public class Fragment_Search extends Fragment {
    FragmentSearchBinding binding;
    private FragmentManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        manager =getActivity().getSupportFragmentManager();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.editTextSearching, InputMethodManager.SHOW_IMPLICIT);

        manager = getActivity().getSupportFragmentManager();

        binding.btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        return  binding.getRoot();
    }
}
