package com.example.chatties.View;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatties.Contract.IStatusContract;
import com.example.chatties.Entity.User;
import com.example.chatties.Presenter.MainActivityPresenter;

public class BaseActivity extends AppCompatActivity implements IStatusContract.View {
    MainActivityPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainActivityPresenter(this);
        presenter.SetStatus(true);
    }

    @Override
    protected void onPause() {
        presenter.SetStatus(false);
        super.onPause();
    }


    @Override
    protected void onResume() {
        presenter.SetStatus(true);
        super.onResume();
    }

    @Override
    public void LoadUser(User user) {

    }
}
