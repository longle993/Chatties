package com.example.chatties.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatties.Adapter.RequestAdapter;
import com.example.chatties.Contract.IRequestContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Presenter.FragmentPresenter.SendRequestFragmentPresenter;
import com.example.chatties.databinding.FragmentSendrequestBinding;

import java.util.ArrayList;

public class Fragment_SendRequest extends Fragment implements IRequestContract.View,RequestAdapter.onRemoveRequest {
    FragmentSendrequestBinding binding;
    RequestAdapter adapter;
    ArrayList<User> listUser;
    SendRequestFragmentPresenter presenter;
    String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSendrequestBinding.inflate(inflater,container,false);
        showLoading(true);
        presenter = new SendRequestFragmentPresenter(this);
        presenter.GetRequest();
        return  binding.getRoot();
    }

    @Override
    public void onFinishGetRequest(boolean isSuccess, Exception e, ArrayList<User> user) {
        if(user.size()>0 && user != null){
            listUser = user;
            adapter = new RequestAdapter(listUser,getActivity(),0);
            binding.recyclerSendRQ.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerSendRQ.setAdapter(adapter);
            adapter.setOnRemoveRequest(this);

        }
        showLoading(false);

    }

    @Override
    public void updateRequest() {
        listUser.remove(id);
        adapter = new RequestAdapter(listUser,getActivity(),0);
        binding.recyclerSendRQ.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerSendRQ.setAdapter(adapter);
    }

    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerSendRQ.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.recyclerSendRQ.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClickItem(String userID) {
        presenter.ReplyRequest(userID,false);
        id = userID;
    }
}
