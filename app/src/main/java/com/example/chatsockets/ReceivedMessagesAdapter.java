package com.example.chatsockets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReceivedMessagesAdapter extends RecyclerView.Adapter<ReceivedMessagesAdapter.ReceivedMessagesViewHolder> {

    private final Context context;
    private final List<Message> messageList;


    public ReceivedMessagesAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ReceivedMessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_received, parent, false);
        return new ReceivedMessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceivedMessagesAdapter.ReceivedMessagesViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.message.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ReceivedMessagesViewHolder extends RecyclerView.ViewHolder {

        public TextView message;

        public ReceivedMessagesViewHolder(View view) {
            super(view);
            message = view.findViewById(R.id.messageReceived);

        }
    }
}
