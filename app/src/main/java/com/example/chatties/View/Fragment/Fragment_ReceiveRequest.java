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
import com.example.chatties.Presenter.FragmentPresenter.ReceiveRequestFragmentPresenter;
import com.example.chatties.databinding.FragmentReceiverequestBinding;

import java.util.ArrayList;

public class Fragment_ReceiveRequest extends Fragment implements IRequestContract.View,RequestAdapter.onAcceptOrDenyRequest {
    FragmentReceiverequestBinding binding;
    RequestAdapter adapter;
    ArrayList<User> user;
    String id;
    ReceiveRequestFragmentPresenter presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReceiverequestBinding.inflate(inflater,container,false);
        presenter = new ReceiveRequestFragmentPresenter(this);
        presenter.GetRequest();
        return binding.getRoot();
    }

    @Override
    public void onFinishGetRequest(boolean isSuccess, Exception e, ArrayList<User> user) {
        if(user.size()>0 && user != null){
            this.user = user;
            adapter = new RequestAdapter(user,getActivity(),1);
            binding.recyclerReceiveRQ.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerReceiveRQ.setAdapter(adapter);
            adapter.setOnAcceptOrDenyRequestListener(this);
        }
        showLoading(false);
    }

    @Override
    public void updateRequest() {
        user.remove(id);
        adapter = new RequestAdapter(user,getActivity(),1);
        binding.recyclerReceiveRQ.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerReceiveRQ.setAdapter(adapter);
    }

    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerReceiveRQ.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.recyclerReceiveRQ.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(String userID, boolean isAccept) {
        presenter.ReplyRequest(userID,isAccept);
        id = userID;
    }
}
