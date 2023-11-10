package com.example.chatties.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatties.Fragment.Fragment_ReceiveRequest;
import com.example.chatties.Fragment.Fragment_SendRequest;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Fragment_ReceiveRequest();
            case 1: return new Fragment_SendRequest();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        // Đặt tiêu đề cho từng tab
        switch (position) {
            case 0:
                return "Đã nhận";
            case 1:
                return "Đã gửi";
            // Thêm các trường hợp mới nếu bạn có thêm tab
            default:
                return null;
        }
    }
}
