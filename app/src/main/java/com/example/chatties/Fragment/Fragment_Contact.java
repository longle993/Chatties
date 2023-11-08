package com.example.chatties.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatties.Adapter.ContactAdapter;
import com.example.chatties.Contract.IContactContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.Presenter.ContactFragmentPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.FragmentContactBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Fragment_Contact extends Fragment implements IContactContract.View {
    FragmentContactBinding binding;
    private FragmentManager manager;
    ContactFragmentPresenter presenter;
    ContactAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContactBinding.inflate(inflater,container,false);
        manager =getActivity().getSupportFragmentManager();
        presenter = new ContactFragmentPresenter(this);
        LoadUserData();

        binding.btnAddfr.setOnClickListener(v -> {
            Fragment fragmentFriendRQ = manager.findFragmentByTag("Fragment_Friend_Request");
            if (fragmentFriendRQ == null) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, new Fragment_Friend_Request(), "Fragment_Friend_Request");
                transaction.addToBackStack(null); //
                transaction.commit();
            } else {
                manager.beginTransaction()
                        .show(fragmentFriendRQ)
                        .commit();
            }
        });

        return binding.getRoot();

    }

    @Override
    public void LoadItemFriend(boolean isSuccess, Exception e, ArrayList<User> friendsID) {
        if(isSuccess){
            String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            adapter = new ContactAdapter(friendsID,getActivity(),currentID);
            binding.listFriends.setAdapter(adapter);
            binding.listFriends.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        showLoading(false);

    }
    private void LoadUserData(){
        showLoading(true);

        String currentID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        presenter.onLoadListFriends(currentID);
    }
    private void showLoading(boolean loading) {
        if (loading) {
            binding.frameList.setVisibility(View.GONE);
            binding.listFriends.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.listFriends.setVisibility(View.VISIBLE);
            binding.frameList.setVisibility(View.VISIBLE);
        }
    }
}
