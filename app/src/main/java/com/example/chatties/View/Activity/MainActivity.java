package com.example.chatties.View.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Application;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.chatties.Contract.IStatusContract;
import com.example.chatties.Entity.User;
import com.example.chatties.View.Fragment.Fragment_Chats;
import com.example.chatties.View.Fragment.Fragment_Contact;
import com.example.chatties.View.Fragment.Fragment_Setting;
import com.example.chatties.View.Fragment.Title_Fragment;
import com.example.chatties.Presenter.ActivityPresenter.MainActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainActivity extends BaseActivity implements IStatusContract.View{
    ActivityMainBinding binding;
    private FragmentManager manager;
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager = getSupportFragmentManager();
        replaceFragment(new Fragment_Chats());
        presenter = new MainActivityPresenter(this);
        presenter.GetUser();

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
    void proceedService(String username){
        Application application = getApplication();
        long appID =487317484 ;
        String appSign ="dd88e5d6924ba06715432c90d40d5b02974a3ecec3bb144b26e1c7fdcd8a1aba";
        String userID = FirebaseAuth.getInstance().getUid();
        String userName =username;

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
    }

    @Override
    public void LoadUser(User user) {
        proceedService(user.getName());
    }
}