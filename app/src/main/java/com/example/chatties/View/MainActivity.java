package com.example.chatties.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.chatties.Contract.IStatusContract;
import com.example.chatties.Fragment.Fragment_Chats;
import com.example.chatties.Fragment.Fragment_Contact;
import com.example.chatties.Fragment.Fragment_Setting;
import com.example.chatties.Fragment.Title_Fragment;
import com.example.chatties.Presenter.MainActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager = getSupportFragmentManager();
        replaceFragment(new Fragment_Chats());

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                CharSequence title = item.getTitle();
                if (title.equals(Title_Fragment.titleChats)) {
                    if(CheckBackStack(manager)){
                        replaceFragment(new Fragment_Chats());
                    }
                    else {
                        replaceFragment(new Fragment_Chats());
                    }
                } else if (title.equals(Title_Fragment.titleContact)) {
                    if(CheckBackStack(manager)){
                        replaceFragment(new Fragment_Contact());

                    }
                    else {
                        replaceFragment(new Fragment_Contact());
                    }
                } else if (title.equals(Title_Fragment.titleSetting)) {
                    if(CheckBackStack(manager)){
                        replaceFragment(new Fragment_Setting());

                    }
                    else {
                        replaceFragment(new Fragment_Setting());
                    }
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        manager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }
    private boolean CheckBackStack(FragmentManager fragmentManager){
        if(fragmentManager.getBackStackEntryCount()>0){
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

}