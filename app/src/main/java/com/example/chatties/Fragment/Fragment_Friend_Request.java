package com.example.chatties.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.chatties.Adapter.PagerAdapter;
import com.example.chatties.databinding.FragmentFriendRequestBinding;

public class Fragment_Friend_Request extends Fragment {
    FragmentFriendRequestBinding binding;
    private FragmentManager manager;
    PagerAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFriendRequestBinding.inflate(inflater, container, false);
        pagerAdapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        binding.viewPagerRequest.setAdapter(pagerAdapter);
        binding.navRequest.setupWithViewPager(binding.viewPagerRequest);
        binding.btnBackFriendrequest.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        return binding.getRoot();
    }
}
