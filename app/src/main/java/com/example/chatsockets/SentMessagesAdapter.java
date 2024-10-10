package com.example.chatsockets;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SentMessagesAdapter extends RecyclerView.Adapter<SentMessagesAdapter.SentMessagesViewHolder> {

    private final Context context;
    private final List<Message> messageList;


    public SentMessagesAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public SentMessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("SentMessage", "onCreateViewHolder: ");
        View view = LayoutInflater.from(context).inflate(R.layout.message_sent, parent, false);
        return new SentMessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SentMessagesAdapter.SentMessagesViewHolder holder, int position) {
        Message message = messageList.get(position);
        Log.i("SentMessage", "message: " + message.getText());
        holder.message.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class SentMessagesViewHolder extends RecyclerView.ViewHolder {

        public TextView message;

        public SentMessagesViewHolder(View view) {
            super(view);
            Log.i("SentMessage", "SentMessagesViewHolder: ");
            message = view.findViewById(R.id.messageSent);

        }
    }
}
