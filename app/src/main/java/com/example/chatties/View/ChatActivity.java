package com.example.chatties.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.example.chatties.Adapter.ImageAdapter;
import com.example.chatties.Adapter.MessageAdapter;
import com.example.chatties.Contract.ISendMessContract;
import com.example.chatties.Entity.Chat;
import com.example.chatties.Entity.ChatTable;
import com.example.chatties.Entity.User;
import com.example.chatties.Entity.UserTable;
import com.example.chatties.Presenter.ChatActivityPresenter;
import com.example.chatties.R;
import com.example.chatties.databinding.ActivityChatBinding;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends BaseActivity implements ISendMessContract.View {
    ActivityChatBinding binding;
    ChatActivityPresenter presenter;
    MessageAdapter messAdapter;
    ImageAdapter imgAdapter;
    ArrayList<Chat> listChat;
    FirebaseAuth auth;
    private ArrayList<Uri> selectedImagePaths;
    private static int request_code = 1;
    boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        listChat = new ArrayList<>();
        selectedImagePaths = new ArrayList<>();
        presenter = new ChatActivityPresenter(this);
        messAdapter = new MessageAdapter(this,this.listChat, auth.getUid());
        imgAdapter = new ImageAdapter(this,selectedImagePaths);
        binding.recyclerViewMessage.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewMessage.setAdapter(messAdapter);

        GetIntent();
        binding.btnBackMain.setOnClickListener(v -> {
            finish();
        });
        binding.layoutchat.setOnTouchListener((v, event) -> {
            DisableEDT();
            return false;
        });
        binding.recyclerViewMessage.setOnTouchListener(((v, event) -> {
            DisableEDT();
            return false;
        }));
        binding.edtSendMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.btnSendMessage.setVisibility(View.VISIBLE);
                }
                else {
                    binding.btnSendMessage.setVisibility(View.GONE);
                }
            }
        });
        binding.btnSendMessage.setOnClickListener(v -> {
            String message = binding.edtSendMessage.getText().toString();
            if(ValidateMessage(message)){
                Chat newMess = new Chat();
                String receiverId = getIntent().getExtras().getString(UserTable.USER_ID);
                String senderId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Timestamp message_time = Timestamp.now();
                newMess.setSenderID(senderId);
                newMess.setMessage(message);
                newMess.setMessage_time(message_time);
                presenter.SendMessage(newMess,receiverId);
                DisableEDT();
                binding.edtSendMessage.setText("");
            }
        });
        binding.btnSendPic.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            startActivityForResult(Intent.createChooser(intent,"Chọn ảnh từ thư viện"),request_code);
        });
    }

    private void GetIntent(){
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString(UserTable.USER_NAME);
        String avatar = bundle.getString(UserTable.USER_AVATAR);
        String id = bundle.getString(UserTable.USER_ID);
        String currentID = FirebaseAuth.getInstance().getUid();
        presenter.GetStatus(id);
        presenter.LoadConversation(currentID,id);
        SetUser(id,name,isActive,avatar);
    }
    private void SetUser(String id, String name,boolean status,String avatar){
        binding.tvUsername.setText(name);
        if(status){
            binding.tvStatus.setText("Đang hoạt động");
        }
        else {
            binding.tvStatus.setText("Offline");
        }
        Glide.with(this).load(avatar).into(binding.imvAvatar);
    }
    private boolean ValidateMessage(String message){
        if(TextUtils.isEmpty(message) || message.equals(""))
            return false;
        else
            return true;
    }

    @Override
    public void onFinishLoadConversation(boolean isSuccess , Exception e, Chat message) {
        if(isSuccess){
            this.listChat.add(message);
            Collections.sort(listChat);
        }
        messAdapter.notifyDataSetChanged();
        binding.recyclerViewMessage.scrollToPosition(listChat.size()-1);
        showLoading(false);
    }

    @Override
    public void ShowStatus(boolean status) {
        isActive = status;
        if(status){
            binding.tvStatus.setText("Đang hoạt động");
        }
        else {
            binding.tvStatus.setText("Offline");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==request_code && (resultCode == RESULT_OK || resultCode == RESULT_FIRST_USER)){
            if (requestCode == request_code && resultCode == RESULT_OK && data != null) {
                // Kiểm tra xem có nhiều ảnh được chọn hay không
                if (data.getClipData() != null) {
                    ClipData clipData = data.getClipData();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();
                        selectedImagePaths.add(imageUri);
                    }
                } else if (data.getData() != null) {
                    // Nếu chỉ có một ảnh được chọn
                    Uri imageUri = data.getData();
                    selectedImagePaths.add(imageUri);
                }
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                binding.recyclerListImage.setLayoutManager(layoutManager);
                binding.recyclerListImage.setAdapter(imgAdapter);
                imgAdapter.notifyDataSetChanged();
                if(selectedImagePaths.size()>0){
                    binding.btnSendMessage.setVisibility(View.VISIBLE);
                    binding.layoutListImage.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void showLoading(boolean loading) {
        if (!loading) {
            binding.progressBar.setVisibility(View.GONE);
            binding.recyclerViewMessage.setVisibility(View.VISIBLE);
        }
    }
    private void DisableEDT(){
        binding.edtSendMessage.clearFocus();

        //Ẩn bàn phím
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.edtSendMessage.getWindowToken(), 0);
    }

}