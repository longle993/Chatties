package com.example.chatties.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatties.Adapter.SearchAdapter;
import com.example.chatties.Contract.ISearchContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Presenter.FragmentPresenter.SearchFragmentPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.FragmentSearchBinding;

import java.util.ArrayList;

public class Fragment_Search extends Fragment implements ISearchContract.View {
    FragmentSearchBinding binding;
    private FragmentManager manager;
    SearchFragmentPresenter presenter;
    ArrayList<User> listFriends;
    SearchAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater,container,false);
        manager =getActivity().getSupportFragmentManager();
        presenter = new SearchFragmentPresenter(this);
        listFriends = new ArrayList<>();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(binding.editTextSearching, InputMethodManager.SHOW_IMPLICIT);
        presenter.onSearching("");
        manager = getActivity().getSupportFragmentManager();
        binding.btnBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
        binding.editTextSearching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String currentText = s.toString();
                presenter.onSearching(currentText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return  binding.getRoot();
    }

    @Override
    public void onGetSearchResult(boolean isSuccess, ArrayList<User> listUser) {
        if(listUser.size()>0){
            adapter = new SearchAdapter(getActivity(),listUser,1);
            binding.recyclerOtherUser.setLayoutManager(new LinearLayoutManager(getActivity()));
            binding.recyclerOtherUser.setAdapter(adapter);

            binding.progressBar.setVisibility(View.VISIBLE);
            binding.layoutOther.setVisibility(View.GONE);
        }
        else {

        }
        showLoading(false);
    }

    @Override
    public void onFinishGetListFriends(boolean isSuccess, ArrayList<User> userID) {
        this.listFriends = userID;
    }
    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar2.setVisibility(View.GONE);
            binding.layoutOther.setVisibility(View.VISIBLE);
        }
    }
}
