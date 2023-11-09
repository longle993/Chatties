package com.example.chatties.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.chatties.Adapter.UserAdapter;
import com.example.chatties.Contract.IChatViewContract;
import com.example.chatties.Entity.Conversation;
import com.example.chatties.Entity.ConversationTable;
import com.example.chatties.Entity.User;
import com.example.chatties.Presenter.ChatsFragmentPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Fragment_Chats extends Fragment implements IChatViewContract.View {
    FragmentChatsBinding binding;
    private FragmentManager manager;
    ChatsFragmentPresenter presenter;
    UserAdapter adapter;
    ArrayList<Conversation> listConver;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =FragmentChatsBinding.inflate(inflater,container,false);
        manager =getActivity().getSupportFragmentManager();
        listConver = new ArrayList<>();
        presenter = new ChatsFragmentPresenter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LoadData();
        // Xử lý khi EditTextSearch được chạm vào
        binding.editTextSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Fragment fragmentSearch = manager.findFragmentByTag("Fragment_Search");
                    if (fragmentSearch == null) {
                        // Nếu chưa có, thì thêm mới
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.fragment_container, new Fragment_Search(), "Fragment_Search");
                        transaction.addToBackStack(null); //
                        transaction.commit();
                    } else {
                        manager.beginTransaction()
                                .show(fragmentSearch)
                                .commit();
                    }
                }
                return true;
            }
        });
        return  binding.getRoot();
    }

    @Override
    public void onFinishLoadChatList(boolean isSuccess, Exception e, Conversation conver,int type) {
        if(isSuccess){
            if(type == ConversationTable.CONVERSATION_MODIFY){
                for(Conversation converItem : listConver){
                    if(converItem.getConversationID().equals(conver.getConversationID())){
                        converItem.setLast_message_time(conver.getLast_message_time());
                        converItem.setLast_message(conver.getLast_message());
                        break;
                    }
                }
            }else {
                this.listConver.add(conver);
            }
            presenter.onLoadListUser(conver.getUser());
            Collections.sort(listConver);
        }
    }

    @Override
    public void onFinishLoadUserList(boolean isSuccess, Exception e, User user) {
        if(!user.getId().equals(FirebaseAuth.getInstance().getUid())){
            this.user = user;
            adapter = new UserAdapter(getActivity(),listConver,user);
            binding.recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        showLoading(false);

    }

    private void LoadData(){
        presenter.onLoadChatList();
    }
    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
