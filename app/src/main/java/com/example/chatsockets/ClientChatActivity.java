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

public class ClientChatActivity extends AppCompatActivity implements MessageListener {


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

        String ip = getIntent().getStringExtra("connectToIpAddress");
        int port = getIntent().getIntExtra("Port", -1);
        userName = getIntent().getStringExtra("userName");
        client = new Client();
        client.connect(this, ip, port);
        client.setMessageListener(this);
        messagesRecyclerView = findViewById(R.id.MessageSentRecyclerView);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageList = new ArrayList<>();

        messagesAdapter = new MessagesAdapter(this, messageList);
        messagesRecyclerView.setAdapter(messagesAdapter);

        EditText etMessage = findViewById(R.id.TIET_message);

        etMessage.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // Desativa temporariamente o campo de entrada para evitar múltiplos envios
                etMessage.setEnabled(false);

                message = userName + ":";
                message = message + etMessage.getText().toString();

                // Verifica se a mensagem não está vazia
                if (!message.trim().isEmpty()) {
                    Message messageobject = new Message(message, true);
                    addMessage(messageobject);
                    client.exchangeMessage(messageobject.getText(), messageobject.getSender());
                    hideKeyboad(v);
                    etMessage.getText().clear();
                } else {
                    Log.i("ChatSocketss", "Mensagem vazia não foi enviada.");
                }

                // Reativa o campo de entrada após 500ms
                new android.os.Handler().postDelayed(() -> etMessage.setEnabled(true), 300);

                return true;
            }

            return false;
        });

    }

    public void hideKeyboad(View view) {
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
        Log.i("ChatSocketss", "addMessage: Recebi uma mensagem");
        addMessage(message);
    }
}

