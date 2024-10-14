package com.example.chatsockets;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ServerChatActivity extends AppCompatActivity implements MessageListener {

    MessagesAdapter messagesAdapter;
    RecyclerView messagesRecyclerView;
    List<Message> messageList;
    Client client;
    String message;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userName = getIntent().getStringExtra("userName");

        Log.i("ChatSocketss", "Username que recebi da intent: " + userName);

        Server server = new Server();
        server.setUserName(userName);
        server.setMessageListener(this);

        messagesRecyclerView = findViewById(R.id.MessageSentRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();

        messagesAdapter = new MessagesAdapter(this, messageList);
        messagesRecyclerView.setAdapter(messagesAdapter);

        EditText etMessage = findViewById(R.id.TIET_message);

        etMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                etMessage.setEnabled(false);

                message = userName + ":";
                message = message + etMessage.getText().toString();

                if (!message.trim().isEmpty()) {
                    Message messageobject = new Message(message, true);
                    addMessage(messageobject);
                    server.exchangeMessage(messageobject.getText(), messageobject.getSender());
                    hideKeyboard(v);
                    etMessage.getText().clear();
                } else {
                    Log.i("ChatSocketss", "Mensagem vazia não foi enviada.");
                }

                etMessage.postDelayed(() -> etMessage.setEnabled(true), 300);

                return true;
            }

            return false;
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void addMessage(Message message) {
        Log.i("ChatSocketss", "addMessage: Era pra funcionar");
        runOnUiThread(() -> {
            messageList.add(message);
            messagesAdapter.notifyItemInserted(messageList.size() - 1);

        });
    }

    @Override
    public void onMessageReceived(Message message) {
        addMessage(message);
    }
}